import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.jgrapht.Graph;
import org.jgrapht.graph.SimpleWeightedGraph;

public class KesselmanKogan implements Algorithm {

	@Override
	public Graph<Vertex, Edge> colorGraph(Graph<Vertex, Edge> graph) {
		Set<Edge> edges = graph.edgeSet();
		List<Matching> matchings = new ArrayList<>();

		edges.forEach(currentEdge -> {
			if (matchings.isEmpty()) {
				matchings.add(new Matching(currentEdge));
			} else if (matchings.stream().noneMatch(matching -> matching.edges.contains(currentEdge))) {
				Optional<Matching> availableMatching = matchings
						.stream()
						.filter(matching -> matching.checkIfFits(graph, currentEdge))
						.findFirst();
				availableMatching
						.map(matching -> matching.addEdge(currentEdge))
						.orElseGet(() -> matchings.add(new Matching(currentEdge)));
			}
		});

		System.out.println("Chromatic Index : " + matchings.size());
		return buildNewGraph(graph, matchings);
	}

	private Graph<Vertex, Edge> buildNewGraph(Graph<Vertex, Edge> graph, List<Matching> matchings) {
		Graph<Vertex, Edge> newGraph = new SimpleWeightedGraph<>(Edge.class);
		graph.vertexSet().forEach(newGraph::addVertex);
		List<ColorEnum> usedColors = new ArrayList<>();
		matchings.forEach(matching -> {
			ColorEnum selectedColor = ColorEnum.getRandomColor();
			while (usedColors.contains(selectedColor)) {
				selectedColor = ColorEnum.getRandomColor();
			}
			usedColors.add(selectedColor);
			ColorEnum finalSelectedColor = selectedColor;
			matching.edges.forEach(edge -> {
				edge.color = finalSelectedColor;
				newGraph.addEdge(graph.getEdgeSource(edge), graph.getEdgeTarget(edge), edge);
				Edge edge1 = newGraph.getEdge(graph.getEdgeSource(edge), graph.getEdgeTarget(edge));
				newGraph.setEdgeWeight(edge1, graph.getEdgeWeight(edge1));
			});
		});
		return newGraph;
	}
}

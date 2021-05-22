import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.jgrapht.Graph;
import org.jgrapht.graph.SimpleGraph;

public class KesselmanKogan implements Algorithm {

	@Override
	public Graph<Vertex, Edge> colorGraph(Graph<Vertex, Edge> graph) {
		Set<Edge> edges = graph.edgeSet();
		List<Matching> matchings = new ArrayList<>();

		edges.forEach(currentEdge -> {
			if (matchings.isEmpty()) {
				matchings.add(new Matching(currentEdge));
			} else {
				Optional<Matching> availableMatching = matchings
						.stream()
						.filter(matching -> matching.checkIfFits(graph, currentEdge))
						.findFirst();
				availableMatching
						.map(matching -> matching.addEdge(currentEdge))
						.orElseGet(() -> matchings.add(new Matching(currentEdge)));
			}
		});

		System.out.println("Chromatic Index : " + (matchings.size() - noOfRepeats(matchings)));
		return buildNewGraph(graph, matchings);
	}

	private Graph<Vertex, Edge> buildNewGraph(Graph<Vertex, Edge> graph, List<Matching> matchings) {
		Graph<Vertex, Edge> newGraph = new SimpleGraph<>(Edge.class);
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
			});
		});
		return newGraph;
	}

	private int noOfRepeats(List<Matching> matchings) {
		List<Edge> allEdges = matchings
				.stream()
				.map(matching -> matching.edges)
				.flatMap(Collection::stream)
				.collect(Collectors.toList());
		Set<Edge> edgesInSet = new HashSet<>(allEdges);
		return allEdges.size() - edgesInSet.size();
	}
}

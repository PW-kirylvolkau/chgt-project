import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.jgrapht.Graph;
import org.jgrapht.graph.SimpleGraph;

public class KesselmanKogan implements Algorithm {

	private class Matching {

		List<Edge> edges;

		Matching(Edge newEdge) {
			edges = new ArrayList<>();
			edges.add(newEdge);
		}

		boolean addEdge(Edge edge) {
			return edges.add(edge);
		}

		boolean checkIfFits(Graph<Vertex, Edge> graph, Edge newEdge) {
			return edges.stream()
					.map(edge -> edge.getVerticies(graph))
					.noneMatch(vertices -> vertices.contains(graph.getEdgeSource(newEdge)) || vertices.contains(graph.getEdgeTarget(newEdge)));
		}
	}

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
				availableMatching.map(matching -> matching.addEdge(currentEdge))
						.orElseGet(() -> matchings.add(new Matching(currentEdge)));
			}
		});

		System.out.println("Chromatic Index : " + matchings.size());
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
}

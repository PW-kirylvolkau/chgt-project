import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;
import org.jgrapht.Graph;
import org.jgrapht.graph.SimpleGraph;

public class KesselmanKogan implements Algorithm {

	private class Matching {

		public List<Edge> edges;

		public Matching(Edge newEdge) {
			edges = new ArrayList<>();
			edges.add(newEdge);
		}

		public boolean addEdge(Edge edge) {
			return edges.add(edge);
		}

		public boolean checkIfFits(Graph<Vertex, Edge> graph, Edge newEdge) {
			return edges.stream()
					.map(edge -> edge.getVerticies(graph))
					.noneMatch(vertices -> vertices.contains(graph.getEdgeSource(newEdge)) || vertices.contains(graph.getEdgeTarget(newEdge)));
		}
	}

	@Override
	public int colorGraph(Graph<Vertex, Edge> graph) {
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

		Graph<Vertex, Edge> newGraph = new SimpleGraph<>(Edge.class);
		graph.vertexSet().forEach(newGraph::addVertex);
		matchings.forEach(matching -> {
			ColorEnum colorEnum = ColorEnum.getRandomColor();
			matching.edges.forEach(edge -> {
				edge.color = colorEnum;
				newGraph.addEdge(graph.getEdgeSource(edge), graph.getEdgeTarget(edge), edge);
			});
		});

		return matchings.size();
	}
}

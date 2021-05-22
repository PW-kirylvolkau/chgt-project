import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.jgrapht.Graph;

public class KesselmanKogan implements Algorithm {

	private class Matching {
		public List<Edge> edges;

		public Matching(Edge newEdge) {
			this.edges = new ArrayList<>(List.of(newEdge));
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
		List<Edge> edges = new ArrayList<>(graph.edgeSet());
		List<Matching> matchings = new ArrayList<>();

		for (int i = 0; i < edges.size(); i++) {
			Edge currentEdge = edges.get(i);
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
			}

		return matchings.size();
	}
}

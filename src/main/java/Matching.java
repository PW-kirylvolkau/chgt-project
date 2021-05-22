import org.jgrapht.Graph;

import java.util.ArrayList;
import java.util.List;

public class Matching {

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
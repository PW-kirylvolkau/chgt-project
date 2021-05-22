import org.jgrapht.Graph;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class TreesAlgorithm implements Algorithm {

    @Override
    public int colorGraph(Graph<Vertex, Edge> graph) {
        Set<Vertex> vertices = new HashSet<>(graph.vertexSet());
        Set<Edge> edges = new HashSet<>(graph.edgeSet());

        int startIdx = new Random().nextInt(vertices.size());
        Vertex startVertex = vertices.toArray()


        return 0;
    }

    private Vertex traversePreOrder(Graph<Vertex, Edge> graph) {

    }

    private Vertex getFirstVertex() {

    }
}

import org.jgrapht.Graph;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.DepthFirstIterator;

import java.util.*;

public class TreesAlgorithm implements Algorithm {

    @Override
    public Graph<Vertex, Edge> colorGraph(Graph<Vertex, Edge> graph) {
        Graph<Vertex, Edge> result = new SimpleGraph<>(Edge.class);
        graph.vertexSet().forEach(result::addVertex);
        Set<Vertex> vertices = new HashSet<>(graph.vertexSet());
        List<Matching> matchings = new ArrayList<>();


        var iterator = new DepthFirstIterator<Vertex, Edge>(graph);
        while (iterator.hasNext()){
            var current = iterator.next();
            var edges = graph.edgesOf(current);

        }


        int startIdx = new Random().nextInt(vertices.size());
        Vertex startVertex = (Vertex)vertices.toArray()[startIdx];



        return 0;
    }

    private Vertex traversePreOrder(Graph<Vertex, Edge> graph) {

    }

    private Vertex getFirstVertex() {

    }
}

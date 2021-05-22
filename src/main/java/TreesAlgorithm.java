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


        int startIdx = new Random().nextInt(vertices.size());
        Vertex startVertex = (Vertex)vertices.toArray()[startIdx];
        System.out.println("initial vertex : " + startVertex.name);

        var iterator = new DepthFirstIterator<Vertex, Edge>(graph, startVertex);
        while (iterator.hasNext()){
            var current = iterator.next();
            var edges = graph.edgesOf(current);
            for (Edge currentEdge: edges) {
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
            }
        }

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
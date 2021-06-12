import org.jgrapht.Graph;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.traverse.BreadthFirstIterator;

import java.util.*;

public class TreesAlgorithmBFS implements Algorithm {

    @Override
    public Graph<Vertex, Edge> colorGraph(Graph<Vertex, Edge> graph) {
        Graph<Vertex, Edge> result = new SimpleGraph<>(Edge.class);
        graph.vertexSet().forEach(result::addVertex);
        Set<Vertex> vertices = new HashSet<>(graph.vertexSet());
        List<Matching> matchings = new ArrayList<>();

        int startIdx = new Random().nextInt(vertices.size());
        Vertex startVertex = (Vertex)vertices.toArray()[startIdx];

        var iterator = new BreadthFirstIterator<>(graph, startVertex);
        while (iterator.hasNext()){
            var current = iterator.next();
            var edges = graph.edgesOf(current);
            for (Edge currentEdge: edges) {
                if (matchings.isEmpty()) {
                    matchings.add(new Matching(currentEdge));
                } else if (matchings.stream().noneMatch(matching -> matching.edges.contains(currentEdge))) {
                    Optional<Matching> availableMatching = matchings
                            .stream()
                            .filter(matching -> matching.edges.stream().noneMatch(edges::contains))
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
import org.jgrapht.Graph;

import java.util.*;
import java.util.stream.Collectors;

public class GraphStateReporter {
    public static void reportState(Graph<Vertex, Edge> graph) {
        System.out.println("SUM OF THE MAX COLOR WEIGHTS : " +
                graph.edgeSet().stream()
                    .collect(Collectors.groupingBy(Edge::getColor))
                    .values().stream()
                    .map(edgeList -> graph.edgeSet().stream()
                            .max(Comparator.comparingDouble(graph::getEdgeWeight))
                            .orElseThrow(IllegalStateException::new)
                    )
                    .map(graph::getEdgeWeight)
                    .mapToDouble(Double::doubleValue)
                    .sum()
        );
    }
}

import java.util.Random;
import org.jgrapht.Graph;

public class Weighter {

	static void weightGraph(Graph<Vertex, Edge> graph) {
		graph.edgeSet().forEach(edge -> graph.setEdgeWeight(edge, new Random().nextInt(5) + 1));
	}

}

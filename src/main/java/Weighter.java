import java.text.DecimalFormat;
import java.util.Random;
import org.jgrapht.Graph;

class Weighter {

	private static DecimalFormat df = new DecimalFormat("#.##");

	static void weightGraph(Graph<Vertex, Edge> graph) {
		graph.edgeSet().forEach(edge -> graph.setEdgeWeight(edge, Double.parseDouble(df.format((new Random().nextDouble() + 0.1) * 5))));
	}

}

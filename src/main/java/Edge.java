import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

public class Edge extends DefaultEdge implements Serializable {

	private static final long serialVersionUID = 3252408452177932855L;

	public Color color;

	public Edge() {

	}

	public List<Vertex> getVerticies(Graph<Vertex, Edge> graph) {
		List<Vertex> vertices = new ArrayList<>();
		vertices.add(graph.getEdgeSource(this));
		vertices.add(graph.getEdgeTarget(this));
		return vertices;
	}
}

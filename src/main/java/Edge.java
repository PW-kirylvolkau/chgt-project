import java.awt.Color;
import java.io.Serializable;
import org.jgrapht.graph.DefaultEdge;

public class Edge extends DefaultEdge implements Serializable {

	private static final long serialVersionUID = 3252408452177932855L;

	public Color color;

	public Edge() {

	}
}

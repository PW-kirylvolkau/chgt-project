import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultEdge;

public interface Algorithm<V> {

	int colorGraph(Graph<V, DefaultEdge> graph);

}

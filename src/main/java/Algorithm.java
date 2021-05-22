import org.jgrapht.Graph;

public interface Algorithm {

	Graph<Vertex, Edge> colorGraph(Graph<Vertex, Edge> graph);

}

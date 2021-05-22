import java.util.function.Supplier;
import org.jgrapht.Graph;
import org.jgrapht.generate.CompleteGraphGenerator;
import org.jgrapht.graph.SimpleGraph;

public class CompleteGraphDemo {

	// number of vertices
	private static final int SIZE = 6;

	public static void main(String[] args) {
		Supplier<Vertex> vSupplier = new Supplier<>() {
			private int id = 0;

			@Override
			public Vertex get() {
				return new Vertex("v" + ++id);
			}
		};

		Graph<Vertex, Edge> graph = new SimpleGraph<>(vSupplier, EdgeSupplier.createDefaultEdgeSupplier(), false);
		CompleteGraphGenerator<Vertex, Edge> completeGraphGenerator = new CompleteGraphGenerator<>(SIZE);
		completeGraphGenerator.generateGraph(graph);

		KesselmanKogan kkAlgorithm = new KesselmanKogan();
		kkAlgorithm.colorGraph(graph);

		GraphExporter.export(graph);
	}
}

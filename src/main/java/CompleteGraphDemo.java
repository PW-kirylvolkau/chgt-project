import java.awt.Color;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Supplier;
import org.jgrapht.Graph;
import org.jgrapht.generate.RandomRegularGraphGenerator;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.DepthFirstIterator;

public class CompleteGraphDemo {

	// number of vertices
	private static final int SIZE = 10;

	public static void main(String[] args) {
		Supplier<Vertex> vSupplier = new Supplier<>() {
			private int id = 0;

			@Override
			public Vertex get() {
				return new Vertex("v" + ++id, Color.BLACK);
			}
		};

		Graph<Vertex, Edge> graph = new SimpleGraph<>(vSupplier, EdgeSupplier.createDefaultEdgeSupplier(), false);
		RandomRegularGraphGenerator<Vertex, Edge> regularGraphGenerator = new RandomRegularGraphGenerator<>(SIZE, 2);
		regularGraphGenerator.generateGraph(graph);

		KesselmanKogan kkAlgorithm = new KesselmanKogan();
		System.out.println(kkAlgorithm.colorGraph(graph));

		Set<Vertex> strings = graph.vertexSet();
		Vertex start = strings.stream().findAny().get();
		start.color = Color.RED;

		Iterator<Vertex> iterator = new DepthFirstIterator<>(graph, start);
		while (iterator.hasNext()) {
			Vertex uri = iterator.next();
			System.out.println(uri.name + " " + uri.color);
		}
	}
}

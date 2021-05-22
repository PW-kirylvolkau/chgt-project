import java.util.Iterator;
import org.jgrapht.Graph;
import org.jgrapht.generate.GnpRandomGraphGenerator;
import org.jgrapht.generate.GraphGenerator;
import org.jgrapht.generate.PruferTreeGenerator;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.DepthFirstIterator;

public class CompleteGraphDemo {

	private static final int SIZE = 8;

	public static void main(String[] args) {
//		Graph<Vertex, Edge> graph = new SimpleGraph<>(VertexSupplier.createDefaultVertexSupplier(), EdgeSupplier.createDefaultEdgeSupplier(), false);
//		GnpRandomGraphGenerator<Vertex, Edge> completeGraphGenerator = new GnpRandomGraphGenerator<>(SIZE, 0.4);
//		completeGraphGenerator.generateGraph(graph);
//
//		KesselmanKogan kkAlgorithm = new KesselmanKogan();
//		kkAlgorithm.colorGraph(graph);

		var initialTree = new SimpleGraph<>(VertexSupplier.createDefaultVertexSupplier(), EdgeSupplier.createDefaultEdgeSupplier(), false);
		PruferTreeGenerator<Vertex, Edge> tree = new PruferTreeGenerator<>(SIZE);
		tree.generateGraph(initialTree);

		// TREES ALGORITHM
		var treeAlgorithm = new TreesAlgorithm();
		var coloredTree = treeAlgorithm.colorGraph(initialTree);
		GraphExporter.export(coloredTree);
	}
}

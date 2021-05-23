import java.util.Iterator;
import org.jgrapht.Graph;
import org.jgrapht.generate.*;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.traverse.DepthFirstIterator;

public class CompleteGraphDemo {

	private static final int SIZE = 15;

	public static void main(String[] args) {

		// TreesAlgorithm
		Graph<Vertex, Edge> graph = new SimpleGraph<>(VertexSupplier.createDefaultVertexSupplier(), EdgeSupplier.createDefaultEdgeSupplier(), true);
		PruferTreeGenerator<Vertex,Edge> gnm = new PruferTreeGenerator<Vertex,Edge>(SIZE);
		gnm.generateGraph(graph);
		TreesAlgorithm algo = new TreesAlgorithm();
		graph = algo.colorGraph(graph);
		GraphExporter.export(graph, false);

		// TreesAlgorithmBFS
		graph = new SimpleGraph<>(VertexSupplier.createDefaultVertexSupplier(), EdgeSupplier.createDefaultEdgeSupplier(), true);
		gnm = new PruferTreeGenerator<Vertex,Edge>(SIZE);
		gnm.generateGraph(graph);
		TreesAlgorithmBFS algo1 = new TreesAlgorithmBFS();
		graph = algo1.colorGraph(graph);
		GraphExporter.export(graph, false);

		// MaxDegreeColoring
		graph = new SimpleGraph<>(VertexSupplier.createDefaultVertexSupplier(), EdgeSupplier.createDefaultEdgeSupplier(), true);
		GnpRandomBipartiteGraphGenerator<Vertex, Edge> gnm1 = new GnpRandomBipartiteGraphGenerator<Vertex,Edge>(SIZE, SIZE, 0.4);
		gnm1.generateGraph(graph);
		MaxDegreeColoring algo2 = new MaxDegreeColoring();
		graph = algo2.colorGraph(graph);
		GraphExporter.export(graph, false);

		// KesselmanKogan
		graph = new SimpleGraph<>(VertexSupplier.createDefaultVertexSupplier(), EdgeSupplier.createDefaultEdgeSupplier(), true);
		GnpRandomGraphGenerator<Vertex, Edge> gnm2 = new GnpRandomGraphGenerator<Vertex,Edge>(SIZE, 0.4);
		gnm2.generateGraph(graph);
		KesselmanKogan algo3 = new KesselmanKogan();
		graph = algo3.colorGraph(graph);
		GraphExporter.export(graph, false);

	}
}

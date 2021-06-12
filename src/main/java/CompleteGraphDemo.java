import java.util.Set;
import org.jgrapht.Graph;
import org.jgrapht.generate.GnpRandomBipartiteGraphGenerator;
import org.jgrapht.generate.GnpRandomGraphGenerator;
import org.jgrapht.generate.PruferTreeGenerator;
import org.jgrapht.graph.SimpleGraph;

public class CompleteGraphDemo {

	private static final int SIZE = 16;

	public static void main(String[] args) {

		// TreesAlgorithm
		Graph<Vertex, Edge> graph = new SimpleGraph<>(VertexSupplier.createDefaultVertexSupplier(), EdgeSupplier.createDefaultEdgeSupplier(), true);
		PruferTreeGenerator<Vertex,Edge> gnm = new PruferTreeGenerator<>(SIZE);
		gnm.generateGraph(graph);
		Weighter.weightGraph(graph);
		TreesAlgorithm algo = new TreesAlgorithm();
		graph = algo.colorGraph(graph);
		GraphExporter.export(graph, true);
		GraphStateReporter.reportState(graph);

		// TreesAlgorithmBFS
		graph = new SimpleGraph<>(VertexSupplier.createDefaultVertexSupplier(), EdgeSupplier.createDefaultEdgeSupplier(), true);
		gnm = new PruferTreeGenerator<>(SIZE);
		gnm.generateGraph(graph);
		Weighter.weightGraph(graph);
		TreesAlgorithmBFS algo1 = new TreesAlgorithmBFS();
		graph = algo1.colorGraph(graph);
		GraphExporter.export(graph, true);
		GraphStateReporter.reportState(graph);

		// MaxDegreeColoring
		graph = new SimpleGraph<>(VertexSupplier.createDefaultVertexSupplier(), EdgeSupplier.createDefaultEdgeSupplier(), true);
		GnpRandomBipartiteGraphGenerator<Vertex, Edge> gnm1 = new GnpRandomBipartiteGraphGenerator<Vertex,Edge>(SIZE / 2 , SIZE / 2, 0.4);
		gnm1.generateGraph(graph);
		Weighter.weightGraph(graph);
		MaxDegreeColoring algo2 = new MaxDegreeColoring();
		graph = algo2.colorGraph(graph);
		GraphExporter.export(graph, true);
		GraphStateReporter.reportState(graph);

		// KesselmanKogan
		graph = new SimpleGraph<>(VertexSupplier.createDefaultVertexSupplier(), EdgeSupplier.createDefaultEdgeSupplier(), true);
		GnpRandomGraphGenerator<Vertex, Edge> gnm2 = new GnpRandomGraphGenerator<Vertex,Edge>(SIZE, 0.4);
		gnm2.generateGraph(graph);
		Weighter.weightGraph(graph);
		KesselmanKogan algo3 = new KesselmanKogan();
		graph = algo3.colorGraph(graph);
		GraphExporter.export(graph, true);
		GraphStateReporter.reportState(graph);

	}
}

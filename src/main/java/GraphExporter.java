import java.util.ArrayList;
import org.jgrapht.Graph;
import org.jgrapht.nio.dot.DOTExporter;

import java.awt.*;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

public class GraphExporter {

    private static int numberOfVertices;

    public static void export(Graph<Vertex, Edge> graph) {
        numberOfVertices =  graph.vertexSet().size();

        DOTExporter<Vertex, Edge> exporter = new DOTExporter<>(v -> v.name);
        graph.vertexSet();

        Writer writer = new StringWriter();
        exporter.exportGraph(graph, writer);
        String output = writer.toString();

        String websiteAddress = createUrlFromOutput(output, new ArrayList<>(graph.edgeSet()), graph);
        openInBrowser(websiteAddress);

    }

    private static void openInBrowser(String websiteAddress) {
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            URI url;
            try {
                url = new URI(websiteAddress);
            } catch (URISyntaxException x) {
                throw new IllegalArgumentException(x.getMessage(), x);
            }

            try {
                Desktop.getDesktop().browse(url);
            } catch (java.io.IOException x){
                throw new IllegalArgumentException(x.getMessage(), x);
            }
        }
    }

    private static String deleteVerticesFromOutput(String input) {
        String[] lines = input.split(System.getProperty("line.separator"));
        StringBuilder result = new StringBuilder();
        int i = 0;
        for (String l: lines) {
            if(i > numberOfVertices || i == 0) {
				result.append(l);
			}
            i++;
        }
        return result.toString();
    }

    // input must be of this form:
	// strict graph G {  v3 -- v4;  v4 -- v8;  v2 -- v6;}
	private static String createUrlFromOutput(String input, List<Edge> edges, Graph<Vertex, Edge> graph) {
		String defaultString = "https://dreampuf.github.io/GraphvizOnline/#strict%20graph%20G%7B";

		String output = deleteVerticesFromOutput(input);
		output = output.substring(output.indexOf('{') + 3, output.indexOf('}')).replace(" ", "");

		StringBuilder result = new StringBuilder();
		result.append(defaultString);
		int counterOfEdges = 0;
		for (int i = 0; i < output.length(); i++) {
			char c = output.charAt(i);

			if (c == ';') {
				result.append("%5Bcolor%3D");
                if(edges.get(counterOfEdges).color == null) {
                    result.append("black");
                } else {
                    result.append(edges.get(counterOfEdges).color.toString().toLowerCase());
                }

                result.append("%2Clabel%3D");
                result.append(String.valueOf(graph.getEdgeWeight(edges.get(counterOfEdges))));

				result.append("%5D");
				result.append("%3B");
				counterOfEdges++;
			} else {
				result.append(c);
			}
		}
		result.append("%7D");
		return result.toString();
	}
}

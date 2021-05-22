import org.jgrapht.Graph;
import org.jgrapht.nio.dot.DOTExporter;

import java.awt.*;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URI;
import java.net.URISyntaxException;

public class GraphExporter {
    static int numberOfVertices;
    public static void export(Graph<Vertex, Edge> graph) {

        numberOfVertices =  graph.vertexSet().size();

        DOTExporter<Vertex, Edge> exporter = new DOTExporter<>(v -> v.name);

        Writer writer = new StringWriter();
        exporter.exportGraph(graph, writer);
        String output = writer.toString();

        String websiteAddress = createUrlFromOutput(output);
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

    private static String formatGraphOutput(String input) {
        String output = deleteVerticesFromOutput(input);
        return output;
    }

    private static String deleteVerticesFromOutput(String input) {
        String[] lines = input.split(System.getProperty("line.separator"));
        String output = "";
        int i = 0;
        for (String l: lines) {
            if(i > numberOfVertices || i == 0)
                output += l;

            i++;
        }
        return output;
    }

    // input must be of this form:
    // strict graph G {  v3 -- v4;  v4 -- v8;  v2 -- v6;}
    private static String createUrlFromOutput(String input) {
        String defaultString = "https://dreampuf.github.io/GraphvizOnline/#strict%20graph%20G%7B";

        String output = deleteVerticesFromOutput(input);
        output = output.substring(output.indexOf('{') + 3, output.indexOf('}')).replace(" ", "");


        for (int i = 0; i < output.length(); i++) {
            char c = output.charAt(i);
            System.out.println(c);

            if(c == ';')
                defaultString += "%3B";
            else
                defaultString += c;
        }
        defaultString += "%7D%0D%0A";
        return defaultString;
    }
}

import org.jgrapht.Graph;
import org.jgrapht.nio.Attribute;
import org.jgrapht.nio.DefaultAttribute;
import org.jgrapht.nio.dot.DOTExporter;

import java.awt.*;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedHashMap;
import java.util.Map;

public class GraphExporter {
    public static void export(Graph<Vertex, Edge> graph) {
        DOTExporter<Vertex, Edge> exporter = new DOTExporter<>(v -> v.name.replace('.', '_'));
        exporter.setVertexAttributeProvider(v -> {
            Map<String, Attribute> map = new LinkedHashMap<>();
            map.put("label", DefaultAttribute.createAttribute(v.name));
            return map;
        });
        Writer writer = new StringWriter();
        exporter.exportGraph(graph, writer);
        String output = writer.toString();
        System.out.println(output);
        openInBrowser();

    }

    private static void openInBrowser() {
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            URI url;
            try {
                url = new URI("https://dreampuf.github.io/GraphvizOnline/");
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
}

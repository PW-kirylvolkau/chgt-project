import org.jgrapht.Graph;
import org.jgrapht.graph.SimpleGraph;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MaxDegreeColoring implements Algorithm {

    /* By Vizing's theorem, the number of colors needed to edge color
     a simple graph is either its maximum degree Δ or Δ+1 */
    @Override
    public Graph<Vertex, Edge> colorGraph(Graph<Vertex, Edge> graph) {
        int allowedNumberOfColors = findGraphDegree(graph);
        ArrayList<ColorEnum> colors = new ArrayList<ColorEnum>();
        Graph<Vertex, Edge> newGraph = new SimpleGraph<>(Edge.class);
        graph.vertexSet().forEach(newGraph::addVertex);
        ColorEnum tempColor;

        CustomEdges customEdges = new CustomEdges(graph);

        for (Vertex vertex : graph.vertexSet()) {
            ArrayList<Edge> edges = new ArrayList<>(graph.edgeSet());
            String v0 = vertex.name.toLowerCase();
            // list of all the edges that have vertex as one of its ends
            List<Edge> collect = edges
                    .stream()
                    .filter(edge -> edge.getVerticies(graph).contains(vertex))
                    .collect(Collectors.toList());

            for(Edge edge : collect) {
                String v1 = edge.getVerticies(graph).get(0).name;
                String v2 = edge.getVerticies(graph).get(1).name;
                ColorEnum color = customEdges.getColorOfEdge(v1, v2);
                if(color != null) {
                    edge.color = color;
                    newGraph.addEdge(edge.getVerticies(graph).get(0), edge.getVerticies(graph).get(1), edge);
                    colors.add(color);
                    continue;
                }

                tempColor  = ColorEnum.getRandomColor();
                ArrayList<ColorEnum> c;
                if(v0 == v1)
                    c = customEdges.getEdgeColorsConnectedToVertex(v2);
                else
                    c = customEdges.getEdgeColorsConnectedToVertex(v1);

                while(colors.contains(tempColor) && !c.contains(tempColor)) {
                    tempColor  = ColorEnum.getRandomColor();
                }

                edge.color = tempColor;
                customEdges.colorEdge(v1, v2, tempColor);
                newGraph.addEdge(edge.getVerticies(graph).get(0), edge.getVerticies(graph).get(1), edge);

                colors.add(tempColor);
            }
            colors.clear();
        }

        customEdges.print();
        return newGraph;
    }

    private int findGraphDegree(Graph<Vertex, Edge> graph) {
        ArrayList<Vertex> vertices = new ArrayList<>(graph.vertexSet());
        int max = 0;
        for (Vertex v : vertices) {
            if (max < graph.degreeOf(v))
                max = graph.degreeOf(v);
        }
        return max;
    }

    private class CustomEdges {
        ArrayList<CustomEdge> edges = new ArrayList<>();

        public CustomEdges(Graph<Vertex, Edge> graph) {
            ArrayList<Edge> edges1 = new ArrayList<>(graph.edgeSet());

            for(Edge edge: edges1) {
                this.addEdgeWithoutColor(graph, edge);
            }

        }

        public int countNumberOfColors() {
            // TODO
            return 0;
        }

        private void addEdgeWithoutColor(Graph<Vertex, Edge> graph, Edge edge) {
            String v1 = edge.getVerticies(graph).get(0).name;
            String v2 = edge.getVerticies(graph).get(1).name;

            if(!IfContainsEdge(v1, v2)) {
                this.edges.add(new CustomEdge(v1, v2));
            }
        }

        private boolean IfContainsEdge(String v1, String v2) {
            for (CustomEdge e: this.edges) {
                if(e.equals(v1, v2)) {
                    return true;
                }
            }

            return false;
        }

        private ArrayList<String> getAllTheNames() {
            ArrayList<String> names = new ArrayList<>();

            for(CustomEdge e: edges) {
                if(!names.contains(e.v1)) {
                    names.add(e.v1);
                }

                if(!names.contains(e.v2)) {
                    names.add(e.v2);
                }
            }

            return names;
        }

        public ArrayList<ColorEnum> getEdgeColorsConnectedToVertex(String v) {
            ArrayList<ColorEnum> colorEnums = new ArrayList<ColorEnum>();
            ArrayList<String> names = getAllTheNames();
            names.removeIf(x -> x.equals(v));

            ColorEnum c = null;

            for (String n: names) {
                for (CustomEdge e: edges) {
                    c = e.getColor(n, v);
                    if(c != null) {
                        if(!colorEnums.contains(c)) {
                            colorEnums.add(c);
                        }
                    }
                }
            }

            return colorEnums;
        }

        public ColorEnum getColorOfEdge(String v1, String v2) {
            ColorEnum color = null;
            for (CustomEdge e: this.edges) {
                color = e.getColor(v1, v2);
                if(color != null)
                    return color;
            }

            return color;
        }

        public void colorEdge(String v1, String v2, ColorEnum c) {
            for (CustomEdge e: this.edges) {
                if(e.equals(v1, v2)) {
                    e.setColor(c);
                }
            }
        }

        void printNumberOfColors() {
            System.out.println("printNumberOfColors() to be implemented");
        }

        public void printNumberOfEdges() {
            System.out.println(this.edges.size());
        }

        public void print() {
            for (CustomEdge e: this.edges) {
                e.print();
            }
        }
    }

    private class CustomEdge {
        ColorEnum color;
        String v1, v2;

        public CustomEdge(String v1, String v2) {
            this.v1 = v1;
            this.v2 = v2;
            this.color = null;
        }

        public CustomEdge(String v1, String v2, ColorEnum color) {
            this.v1 = v1;
            this.v2 = v2;
            this.color = color;
        }

        public ColorEnum getColor(String v1, String v2) {
            if(equals(v1, v2)){
                return this.color;
            }

            return null;
        }

        public void colorEdge(ColorEnum color) {
            if(this.color == null)
                this.color = color;
            else
                System.out.println("This edge is already colored");
        }

        public boolean equals(String v1, String v2) {
            if(this.v1.equals(v1) && this.v2.equals(v2))
                return true;
            if(this.v1.equals(v2) && this.v2.equals(v1))
                return true;

            return false;
        }

        public void setColor(ColorEnum c) {
            this.color = c;
        }

        public void print() {
            if(color == null)
                System.out.println(v1 + " " + v2 + " " + "null"  );
            else
                System.out.println(v1 + " " + v2 + " " + color.toString().toLowerCase()  );
        }

        public boolean IsVertexIn(String v) {
            return v.equals(v1) || v.equals(v2);
        }
    }
}

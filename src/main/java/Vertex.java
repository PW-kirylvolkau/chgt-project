import java.awt.Color;

public class Vertex {

	public String name;
	public Color color;

	public Vertex() {

	}

	public Vertex(String name, Color color) {
		this.name = name;
		this.color = color;
	}

	public static Vertex valueOf(String string) {
		Vertex v = new Vertex();
		v.name = string.substring(0, string.indexOf(' '));
		v.color = null;
		return v;
	}

	@Override
	public String toString() {
		return name + " " + color;
	}
}

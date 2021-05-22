public class Vertex {

	public String name;
	public Vertex() {

	}

	public Vertex(String name) {
		this.name = name;
	}

	public static Vertex valueOf(String string) {
		Vertex v = new Vertex();
		v.name = string.substring(0, string.indexOf(' '));
		return v;
	}

	@Override
	public String toString() {
		return name;
	}
}

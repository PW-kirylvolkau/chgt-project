import java.util.function.Supplier;

public class VertexSupplier {

	private static final Supplier<Vertex> VERTEX_SUPPLIER = new Supplier<>() {
		private int id = 0;

		@Override
		public Vertex get() {
			return new Vertex("" + ++id);
		}
	};

	public static Supplier<Vertex> createDefaultVertexSupplier() {
		return VERTEX_SUPPLIER;
	}
}

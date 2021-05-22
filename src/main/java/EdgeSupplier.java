import java.util.function.Supplier;

public class EdgeSupplier {

	public static final Supplier<Edge> EDGE_SUPPLIER = Edge::new;

	public static Supplier<Edge> createDefaultEdgeSupplier() {
		return EDGE_SUPPLIER;
	}
}

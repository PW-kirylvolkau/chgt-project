import java.util.List;
import java.util.Random;

public enum ColorEnum {
	RED, YELLOW, BLUE, GREEN, BLACK, ORANGE, PINK, PURPLE, GREY, BROWN, GOLD, SILVER, LIMEGREEN, MAROON, OLIVE, NAVY, TEAL, AQUA;

	private static final List<ColorEnum> VALUES = List.of(values());
	private static final int SIZE = VALUES.size();
	private static final Random RANDOM = new Random();

	public static ColorEnum getRandomColor() {
		return VALUES.get(RANDOM.nextInt(SIZE));
	}
}

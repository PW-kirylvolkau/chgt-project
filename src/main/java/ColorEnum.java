import java.util.List;
import java.util.Random;

public enum ColorEnum {
	RED, YELLOW, BLUE, GREEN, ORANGE, PINK, PURPLE, GREY, BROWN, GOLD, TURQUOISE, LIMEGREEN, MAROON, MAGENTA, NAVY, MOCCASIN;

	private static final List<ColorEnum> VALUES = List.of(values());
	private static final int SIZE = VALUES.size();
	private static final Random RANDOM = new Random();

	public static ColorEnum getRandomColor() {
		return VALUES.get(RANDOM.nextInt(SIZE));
	}
}

package software.aoc.day10.part2;

import java.util.Arrays;
import java.util.stream.IntStream;
import static java.lang.String.valueOf;

public class Button {
    private final int[] wiring;

    private Button(int[] wiring) {
        this.wiring = wiring;
    }

    public static Button from(String schematic, int length) {
        return new Button(parseNumIn(schematic, length));
    }

    public int[] wiring() {
        return wiring;
    }

    private static int[] parseNumIn(String schematic, int length) {
        return toWiringArray(stripParenthesesIn(schematic), length);
    }

    private static String stripParenthesesIn(String diagram) {
        return diagram.substring(1, diagram.length() - 1);
    }

    private static int[] toWiringArray(String diagram, int length) {
        return parseArrayIn(diagram.split(","), length);
    }

    private static int[] parseArrayIn(String[] wiring, int length) {
        return IntStream.range(0, length)
                .map(i -> contains(wiring, i))
                .toArray();
    }

    private static int contains(String[] wiring, int i) {
        return Arrays.asList(wiring).contains(valueOf(i)) ? 1 : 0;
    }
}
package software.aoc.day10.part1;

import java.util.Arrays;
import static java.lang.Integer.parseInt;

public class Button {
    private final int wiring;

    private Button(int wiring) {
        this.wiring = wiring;
    }

    public static Button from(String schematic, int length) {
        return new Button(parseNumIn(schematic, length));
    }

    public int wiring() {
        return wiring;
    }

    private static int parseNumIn(String schematic, int length) {
        return binaryStringToInt(stripParenthesesIn(schematic), length);
    }

    private static String stripParenthesesIn(String diagram) {
        return diagram.substring(1, diagram.length() - 1);
    }

    private static int binaryStringToInt(String diagram, int length) {
        return parseIntIn(diagram.split(","), length);
    }

    private static int parseIntIn(String[] wirings, int length) {
        return Arrays.stream(wirings)
                .mapToInt(w -> 1 << (length - 1 - parseInt(w)))
                .reduce((a, b) -> a | b).orElseThrow();
    }
}
package software.aoc.day10.part1;

import static java.lang.Integer.parseInt;

public record LightIndicator (int value, int size) {
    public static LightIndicator from(String lightDiagram) {
        return createIndicatorLightWith(lightDiagram);
    }

    private static LightIndicator createIndicatorLightWith(String lightDiagram) {
        return new LightIndicator(binaryStringToInt(stripBracketsIn(lightDiagram)), lightDiagram.length() - 2);
    }

    private static String stripBracketsIn(String diagram) {
        return diagram.substring(1, diagram.length() - 1);
    }

    private static int binaryStringToInt(String diagram) {
        return parseInt(diagram.replace('.', '0')
                .replace('#', '1'), 2);
    }
}
package software.aoc.day7;

import java.util.Arrays;

public enum TachyonCell {
    EMPTY('.'), BEAM('S'), SPLITTER('^');

    private final char cellValue;

    TachyonCell(char cellValue) {
        this.cellValue = cellValue;
    }

    public char cellValue() {
        return cellValue;
    }

    public static TachyonCell from(int val) {
        return Arrays.stream(values())
                .filter(c -> c.cellValue == val)
                .findFirst().orElse(EMPTY);
    }
}
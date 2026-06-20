package software.aoc.day7;

import java.util.Arrays;
import static software.aoc.day7.TachyonCell.EMPTY;

public class TachyonGrid {
    private final TachyonCell[][] cells;

    private TachyonGrid(TachyonCell[][] cells) {
        this.cells = cells;
    }

    public static TachyonGrid with(String grid) {
        return tachyonGridIn(grid.split("\n"));
    }

    public TachyonCell at(int row, int col) {
        if (isOutOfBounds(row, col)) return EMPTY;
        return cells[row][col];
    }

    public int height() {
        return cells.length;
    }

    public int width() {
        return cells[0].length;
    }

    private static TachyonGrid tachyonGridIn(String[] grid) {
        return new TachyonGrid(tachyonCellMapIn(grid));
    }

    private static TachyonCell[][] tachyonCellMapIn(String[] grid) {
        return Arrays.stream(grid)
                .map(TachyonGrid::cellRow)
                .toArray(TachyonCell[][]::new);
    }

    private static TachyonCell[] cellRow(String stringRow) {
        return Arrays.stream(stringRow.split(""))
                .map(s -> TachyonCell.from(s.charAt(0)))
                .toArray(TachyonCell[]::new);
    }

    private boolean isOutOfBounds(int row, int col) {
        return row < 0 || row >= cells.length ||
                col < 0 || col >= cells[0].length;
    }
}
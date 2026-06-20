package software.aoc.day12;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import static software.aoc.day12.Angle.Degrees270;
import static software.aoc.day12.Angle.Degrees90;

public class Present implements Comparable<Present> {
    private final int id;
    private final int[][] grid;

    private Present(int id, int[][] grid) {
        this.id = id;
        this.grid = grid;
    }

    public static Present with(String presentStructure) {
        return new Present(idIn(presentStructure), toIntMatrix(gridOf(presentStructure)));
    }

    public List<Present> allOrientations() {
        return Stream.concat(allRotations(), this.flipped().allRotations())
                .distinct()
                .toList();
    }

    public int boundingRectangleArea() {
        return grid.length * grid[0].length;
    }

    public Stream<Position> occupiedPositions() {
        return IntStream.range(0, height()).boxed()
                .flatMap(this::occupiedPositionsAtRow);
    }

    public int area() {
        return Arrays.stream(grid)
                .mapToInt(a -> Arrays.stream(a).sum())
                .sum();
    }

    public int width() {
        return grid[0].length;
    }

    public int height() {
        return grid.length;
    }

    private Stream<Present> allRotations() {
        return Arrays.stream(Angle.values())
                .map(this::rotate);
    }

    private Present rotate(Angle angle) {
        return new Present(id, rotatedGrid(angle));
    }

    private int[][] rotatedGrid(Angle angle) {
        int[][] newGrid = new int[adaptHeight(angle)][adaptWidth(angle)];
        occupiedPositions().forEach(p -> fillRotatedCell(newGrid, p, angle));
        return newGrid;
    }

    private void fillRotatedCell(int[][] newGrid, Position p, Angle angle) {
        int[] coords = angle.rotatingFunction().apply(p.y(), p.x(), height(), width());
        newGrid[coords[0]][coords[1]] = 1;
    }

    private int adaptHeight(Angle angle) {
        return angle == Degrees90 || angle == Degrees270 ? width() : height();
    }

    private int adaptWidth(Angle angle) {
        return angle == Degrees90 || angle == Degrees270 ? height() : width();
    }

    private Present flipped() {
        return new Present(id, flippedGrid());
    }

    private int[][] flippedGrid() {
        int[][] newGrid = new int[height()][width()];
        occupiedPositions().forEach(p -> newGrid[height() - 1 - p.y()][p.x()] = 1);
        return newGrid;
    }

    private Stream<Position> occupiedPositionsAtRow(int y) {
        return IntStream.range(0, width())
                .filter(x -> grid[y][x] == 1)
                .mapToObj(x -> new Position(x, y));
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Present p)) return false;
        return Arrays.deepEquals(this.grid, p.grid);
    }

    @Override
    public int hashCode() {
        return 31 * id + Arrays.deepHashCode(grid);
    }

    @Override
    public int compareTo(Present present) {
        return this.area() - present.area();
    }

    private static int idIn(String p) {
        return Integer.parseInt(p.split(":\\r?\n")[0]);
    }

    private static String gridOf(String p) {
        return p.split(":\\r?\n")[1];
    }

    private static int[][] toIntMatrix(String grid) {
        return toIntMatrix(grid.split("\\r?\n"));
    }

    private static int[][] toIntMatrix(String[] splitGrid) {
        return Arrays.stream(splitGrid)
                .map(Present::convertToIntRow)
                .toArray(int[][]::new);
    }

    private static int[] convertToIntRow(String row) {
        return convertToIntRow(row.split(""));
    }

    private static int[] convertToIntRow(String[] splitRow) {
        return Arrays.stream(splitRow)
                .mapToInt(Present::convertToInt)
                .toArray();
    }

    private static int convertToInt(String gridPixel) {
        return gridPixel.equals("#") ? 1 : 0;
    }
}
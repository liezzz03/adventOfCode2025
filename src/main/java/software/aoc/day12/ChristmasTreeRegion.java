package software.aoc.day12;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public record ChristmasTreeRegion(int[][] grid) {
    public static ChristmasTreeRegion with(int width, int height) {
        return new ChristmasTreeRegion(new int[height][width]);
    }

    public Stream<Position> validAnchorPoints(Present present) {
        return allPositionsFromZeroTo(this.height() - present.height(), this.width() - present.width());
    }

    public Optional<ChristmasTreeRegion> place(Present present, Position anchor) {
        if (!canPlace(present, anchor)) return Optional.empty();
        return Optional.of(new ChristmasTreeRegion(nextGridWith(present, anchor)));
    }

    private Stream<Position> allPositionsFromZeroTo(int maxRow, int maxCol) {
        return IntStream.rangeClosed(0, maxRow)
                .boxed()
                .flatMap(y -> getPositionsInRow(y, maxCol));
    }

    private static Stream<Position> getPositionsInRow(int y, int maxCol) {
        return IntStream.rangeClosed(0, maxCol)
                .mapToObj(x -> new Position(x, y));
    }

    private int[][] nextGridWith(Present present, Position anchor) {
        int[][] copy = copyGrid();
        present.occupiedPositions()
                .map(pos -> pos.translate(anchor))
                .forEach(p -> copy[p.y()][p.x()] = 1);
        return copy;
    }

    private int[][] copyGrid() {
        return Arrays.stream(this.grid)
                .map(int[]::clone)
                .toArray(int[][]::new);
    }

    public boolean canPlace(Present present, Position anchor) {
        return present.occupiedPositions()
                .map(pos -> pos.translate(anchor))
                .allMatch(this::isEmpty);
    }

    private boolean isEmpty(Position position) {
        return grid[position.y()][position.x()] == 0;
    }

    public int area() {
        return grid.length * grid[0].length;
    }

    public int width() {
        return grid[0].length;
    }

    public int height() {
        return grid.length;
    }
}
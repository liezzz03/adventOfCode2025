package software.aoc.day4.part2;

import java.util.stream.IntStream;
import java.util.stream.Stream;

public record Position(int row, int col) {
    public Stream<Position> neighbors() {
        return IntStream.rangeClosed(-1, 1).boxed()
                .flatMap(this::neighborsInRow)
                .filter(p -> !p.equals(this));
    }

    private Stream<Position> neighborsInRow(Integer drow) {
        return IntStream.rangeClosed(-1, 1)
                .mapToObj(dcol -> new Position(row + drow, col + dcol));
    }
}

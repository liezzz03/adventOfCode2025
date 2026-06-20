package software.aoc.day9.part1;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import static java.util.Comparator.comparingLong;

public class MovieTheater {
    private final List<RedTile> tiles;

    private MovieTheater(List<RedTile> tiles) {
        this.tiles = tiles;
    }

    public static MovieTheater with(String tiles) {
        return new MovieTheater(listOf(tiles));
    }

    private static List<RedTile> listOf(String tiles) {
        return Arrays.stream(tiles.split("\\r?\\n"))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(RedTile::of)
                .toList();
    }

    public long largestArea() {
        return buildAllRectangles()
                .max(comparingLong(Rectangle::area)).orElse(Rectangle.Null)
                .area();
    }

    private Stream<Rectangle> buildAllRectangles() {
        return IntStream.range(0, tiles.size()).boxed()
                .flatMap(this::rectanglesStartingAt);
    }

    private Stream<Rectangle> rectanglesStartingAt(Integer i) {
        return IntStream.range(i + 1, tiles.size())
                .mapToObj(j -> Rectangle.of(tiles.get(i), tiles.get(j)));
    }
}
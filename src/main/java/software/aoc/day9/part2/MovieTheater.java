package software.aoc.day9.part2;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import static java.util.Comparator.comparingLong;

public class MovieTheater {
    private final List<Tile> tiles;
    private final Polygon polygon;

    private MovieTheater(List<Tile> tiles, Polygon polygon) {
        this.tiles = tiles;
        this.polygon = polygon;
    }

    public static MovieTheater with(String redTiles) {
        return movieTheaterWith(redTiles.split("\\r?\n"));
    }

    private static MovieTheater movieTheaterWith(String[] redTiles) {
        return new MovieTheater(tilesIn(redTiles), Polygon.with(tilesIn(redTiles)));
    }

    private static List<Tile> tilesIn(String[] redTiles) {
        return Arrays.stream(redTiles)
                .map(Tile::of)
                .toList();
    }

    public long largestArea() {
        return buildAllRectangles()
                .filter(polygon::contains)
                .findFirst().orElse(Rectangle.Null)
                .area();
    }

    private Stream<Rectangle> buildAllRectangles() {
        return IntStream.range(0, tiles.size()).boxed()
                .flatMap(this::makePairsWithTileAtIndex)
                .sorted(comparingLong(r -> -r.area()));
    }

    private Stream<Rectangle> makePairsWithTileAtIndex(Integer i) {
        return IntStream.range(i + 1, tiles.size())
                .mapToObj(j -> Rectangle.of(tiles.get(i), tiles.get(j)));
    }
}
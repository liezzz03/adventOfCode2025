package software.aoc.day9.part2;

import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;
import static java.lang.Math.max;
import static java.lang.Math.min;

public class Polygon {

    private final List<Tile[]> edges;
    private final long[] cornersYCoordinates;

    private Polygon(List<Tile> cornersYCoordinates) {
        this.edges = edgesFrom(cornersYCoordinates);
        this.cornersYCoordinates = cornersYCoordinates.stream().mapToLong(Tile::y).sorted().toArray();
    }

    public static Polygon with(List<Tile> tiles) {
        return new Polygon(tiles);
    }

    public boolean contains(Rectangle rectangle) {
        return isInPolygonBounds(rectangle) && noPolygonCornersInside(rectangle);
    }

    private boolean isInPolygonBounds(Rectangle rectangle) {
        return isRectangleSpanContainedAt(rectangle.minY(), rectangle) &&
                isRectangleSpanContainedAt(rectangle.maxY(), rectangle);
    }

    private boolean noPolygonCornersInside(Rectangle rectangle) {
        return cornersWithinYBoundsOf(rectangle)
                .allMatch(col -> isRectangleSpanContainedAt(col, rectangle));
    }

    private boolean isRectangleSpanContainedAt(long y, Rectangle rectangle) {
        return polygonIntervalsAt(y)
                .anyMatch(i -> i.contains(rectangle.horizontalSpan()));
    }

    private Stream<Interval> polygonIntervalsAt(long y) {
        return Stream.concat(
                horizontalEdgesAlong(y),
                interiorSpansAt(y)
        );
    }

    private Stream<Interval> horizontalEdgesAlong(long y) {
        return edges.stream().filter(e -> isAlong(y, e))
                .map(Polygon::createValidIntervalAtEdge);
    }

    private static Interval createValidIntervalAtEdge(Tile[] e) {
        return new Interval(min(e[0].x(), e[1].x()), max(e[0].x(), e[1].x()));
    }

    private Stream<Interval> interiorSpansAt(long y) {
        return spanningIntervalsWith(xOfEdgesIntersectingAt(y));
    }

    private static Stream<Interval> spanningIntervalsWith(List<Long> xIntersections) {
        return IntStream.range(0, xIntersections.size() / 2)
                .mapToObj(i -> new Interval(xIntersections.get(i * 2), xIntersections.get(i * 2 + 1)));
    }

    private List<Long> xOfEdgesIntersectingAt(long y) {
        return edges.stream()
                .filter(edge -> isIntersecting(y, edge))
                .map(edge -> edge[0].x())
                .sorted()
                .toList();
    }

    private LongStream cornersWithinYBoundsOf(Rectangle rectangle) {
        return Arrays.stream(cornersYCoordinates, startIndexFor(rectangle.minY()), cornersYCoordinates.length)
                .takeWhile(y -> y < rectangle.maxY());
    }

    private int startIndexFor(long minY) {
        int result = Arrays.binarySearch(cornersYCoordinates, minY);
        return result < 0 ? ~result : result + 1;
    }

    private static boolean isIntersecting(long y, Tile[] edge) {
        return (edge[0].y() <= y && edge[1].y() > y) || (edge[1].y() <= y && edge[0].y() > y);
    }

    private static boolean isAlong(long y, Tile[] e) {
        return e[0].y() == y && e[1].y() == y;
    }

    private List<Tile[]> edgesFrom(List<Tile> corners) {
        return IntStream.range(0, corners.size())
                .mapToObj(i -> new Tile[]{corners.get(i), corners.get((i+1) % corners.size())})
                .toList();
    }
}
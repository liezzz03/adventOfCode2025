package software.aoc.day9.part2;

import static java.lang.Math.*;

public record Rectangle(Tile tile1, Tile tile2) {
    public static final Rectangle Null = Rectangle.of(Tile.Null, Tile.Null);

    public static Rectangle of(Tile t1, Tile t2) {
        return new Rectangle(
                new Tile(min(t1.x(), t2.x()), min(t1.y(), t2.y())),
                new Tile(max(t1.x(), t2.x()), max(t1.y(), t2.y()))
        );
    }

    public long area() {
        return (abs(tile2.x() - tile1.x()) + 1) * (abs(tile2.y() - tile1.y()) + 1);
    }

    public Interval horizontalSpan() {
        return new Interval(tile1.x(), tile2.x());
    }

    public long minY() {
        return tile1.y();
    }

    public long maxY() {
        return tile2.y();
    }
}
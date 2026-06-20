package software.aoc.day9.part1;

import static java.lang.Math.abs;

public class Rectangle {
    public static final Rectangle Null = Rectangle.of(RedTile.Null, RedTile.Null);

    private final RedTile tile1;
    private final RedTile tile2;

    private Rectangle(RedTile tile1, RedTile tile2) {
        this.tile1 = tile1;
        this.tile2 = tile2;
    }

    public static Rectangle of(RedTile tile1, RedTile tile2) {
        return new Rectangle(tile1, tile2);
    }

    public long area() {
        return (abs(tile2.x() - tile1.x()) + 1) * (abs(tile2.y() - tile1.y()) + 1);
    }
}
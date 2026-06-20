package software.aoc.day9.part2;

import static java.lang.Long.parseLong;

public record Tile(long x, long y) {
    public static final Tile Null = new Tile(0, 0);

    public static Tile of(String tileCoordinates) {
        return tileIn(tileCoordinates.split(","));
    }

    private static Tile tileIn(String[] tileCoordinates) {
        return new Tile(parseLong(tileCoordinates[0]), parseLong(tileCoordinates[1]));
    }
}
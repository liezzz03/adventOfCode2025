package software.aoc.day9.part1;

import static java.lang.Long.parseLong;

public record RedTile(long x, long y) {
    public static final RedTile Null = new RedTile(0, 0);

    public static RedTile of(String tile) {
        return tileIn(tile.split(","));
    }

    private static RedTile tileIn(String[] tile) {
        return new RedTile(parseLong(tile[0].trim()), parseLong(tile[1].trim()));
    }
}
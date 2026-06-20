package software.aoc.day12;

public record Position(int x, int y) {
    public Position translate(Position anchor) {
        return new Position(x + anchor.x(), y + anchor.y());
    }
}

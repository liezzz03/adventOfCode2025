package software.aoc.day1.part2;


public record Rotation(Direction direction, int magnitude) {
    public static Rotation from(String order) {
        return rotationIn(order);
    }

    public int crossingsFrom(int startPosition) {
        return direction.calculateCrossings(startPosition, magnitude);
    }

    public int step() {
        return direction().sign() * magnitude;
    }

    private static Rotation rotationIn(String order) {
        return new Rotation(directionOf(order), valueOf(order));
    }

    private static Direction directionOf(String order) {
        return Direction.from(order.charAt(0));
    }

    private static int valueOf(String order) {
        return Integer.parseInt(order.substring(1));
    }
}
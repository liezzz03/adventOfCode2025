package software.aoc.day1.part2;

public enum Direction {
    LEFT(-1, Direction::leftCrossing),
    RIGHT(1, (start, magnitude) -> (start + magnitude) / 100);

    private final int sign;
    private final RotationStrategy rotationStrategy;

    Direction(int sign, RotationStrategy rotationStrategy) {
        this.sign = sign;
        this.rotationStrategy = rotationStrategy;
    }

    public static Direction from(char c) {
        return c == 'L' ? LEFT : RIGHT;
    }

    public int sign() {
        return this.sign;
    }

    public int calculateCrossings(int startPosition, int magnitude) {
        return this.rotationStrategy.calculateCrossings(startPosition, magnitude);
    }

    private static int leftCrossing(int start, int magnitude) {
        return start == 0
                ? magnitude / 100
                : (magnitude - start) / 100 + (magnitude < start ? 0 : 1);
    }

    private interface RotationStrategy {
        int calculateCrossings(int start, int magnitude);
    }
}

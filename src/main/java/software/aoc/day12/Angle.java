package software.aoc.day12;

public enum Angle {
    Degrees0((y, x, b, b2) -> new int[]{y, x}),
    Degrees90((y, x, h, b) -> new int[]{x, h - 1 - y}),
    Degrees180((y, x, h, w) -> new int[]{h - 1 - y, w - 1 - x}),
    Degrees270((y, x, b, w) -> new int[]{w - 1 - x, y});

    private final RotatingFunction rotatingFunction;

    Angle(RotatingFunction rotatingFunction) {
        this.rotatingFunction = rotatingFunction;
    }

    public RotatingFunction rotatingFunction() {
        return rotatingFunction;
    }

    public interface RotatingFunction {
        int[] apply(int y, int x, int h, int w);
    }
}

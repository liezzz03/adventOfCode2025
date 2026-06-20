package software.aoc.day1.part1;

public record Rotation(int step) {
    public static Rotation from(String order) {
        return orderIn(order);
    }

    private static Rotation orderIn(String order) {
        return new Rotation(signOf(order) * valueOf(order));
    }

    private static int valueOf(String order) {
        return Integer.parseInt(order.substring(1));
    }

    private static int signOf(String order) {
        return order.charAt(0) == 'L' ? -1 : 1;
    }

}

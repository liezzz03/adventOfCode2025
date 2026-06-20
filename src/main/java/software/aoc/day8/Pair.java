package software.aoc.day8;

public class Pair {
    private final JunctionBox box1;
    private final JunctionBox box2;
    private final double distance;

    private Pair(JunctionBox box1, JunctionBox box2, double distance) {
        this.box1 = box1;
        this.box2 = box2;
        this.distance = distance;
    }

    public static Pair of(JunctionBox box1, JunctionBox box2) {
        return new Pair(box1, box2, distance(box1, box2));
    }

    public JunctionBox box1() {
        return box1;
    }

    public JunctionBox box2() {
        return box2;
    }

    public double distance() {
        return distance;
    }

    private static double distance(JunctionBox box1, JunctionBox box2) {
        return Math.sqrt(Math.pow(box1.x() - box2.x(), 2) +
                Math.pow(box1.y() - box2.y(), 2) +
                Math.pow(box1.z() - box2.z(), 2));
    }
}
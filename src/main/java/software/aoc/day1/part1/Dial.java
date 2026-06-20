package software.aoc.day1.part1;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Dial {
    private static final int INITIAL_POSITION = 50;
    private static final int DIAL_SIZE = 100;

    private final List<Rotation> rotations;

    private Dial(List<Rotation> rotations) {
        this.rotations = rotations;
    }

    public static Dial create() {
        return new Dial(List.of());
    }

    public Dial execute(String orders) {
        // Expresión regular que cubre tanto \n (Linux/Mac) como \r\n (Windows)
        return add(orders.split("\\r?\\n"));
    }

    public Dial add(String... orders) {
        return new Dial(Arrays.stream(orders)
                .map(String::trim)           // Quita cualquier \r o espacio sobrante
                .filter(s -> !s.isEmpty())   // Ignora las líneas en blanco (común al final del input)
                .map(Rotation::from)
                .toList());
    }

    public int count() {
        return (int) iterate()
                .map(this::sumPartial)
                .filter(s -> s == 0)
                .count();
    }

    public int position() {
        return normalize(sumAll());
    }

    private int sumAll() {
        return sum(rotations.stream());
    }

    private IntStream iterate() {
        return IntStream.rangeClosed(1, rotations.size());
    }

    private int sumPartial(int size) {
        return normalize(sum(rotations.stream().limit(size)));
    }

    private static int sum(Stream<Rotation> orders) {
        return orders.mapToInt(Rotation::step).sum() + INITIAL_POSITION;
    }

    private int normalize(int value) {
        return ((value < 0 ? DIAL_SIZE : 0) + value % DIAL_SIZE) % DIAL_SIZE;
    }
}


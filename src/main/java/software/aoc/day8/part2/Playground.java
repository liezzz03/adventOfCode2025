package software.aoc.day8.part2;

import software.aoc.day8.JunctionBox;
import software.aoc.day8.Pair;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import static java.util.Comparator.comparingDouble;

public class Playground {
    private final List<JunctionBox> boxes;

    private Playground(List<JunctionBox> boxes) {
        this.boxes = boxes;
    }

    public static Playground with(String boxes) {
        return new Playground(listWith(boxes));
    }

    private static List<JunctionBox> listWith(String boxes) {
        return Arrays.stream(boxes.split("\n")).map(JunctionBox::from).toList();
    }

    // Cambiarlo a long forzosamente para que no hayan desbordamientos
    public long lastConnectedBoxes() {
        return multipliedXCoordinatesIn(unifiedCircuitSet());
    }

    private long multipliedXCoordinatesIn(CircuitSet circuitSet) {
        return (long) circuitSet.lastConnectedBoxAt(0).x() * circuitSet.lastConnectedBoxAt(1).x();
    }

    private CircuitSet unifiedCircuitSet() {
        return allBoxWirings()
                .reduce(CircuitSet.from(boxes.stream()),
                        (c, p) -> c.connect(p.box1(), p.box2()),
                        (a, b) -> a);
    }

    private Stream<Pair> allBoxWirings() {
        return IntStream.range(0, boxes.size()).boxed()
                .flatMap(this::makePairsWithBoxAtIndex)
                .sorted(comparingDouble(Pair::distance));
    }

    private Stream<Pair> makePairsWithBoxAtIndex(Integer i) {
        return IntStream.range(i + 1, boxes.size())
                .mapToObj(j -> Pair.of(boxes.get(i), boxes.get(j)));
    }
}
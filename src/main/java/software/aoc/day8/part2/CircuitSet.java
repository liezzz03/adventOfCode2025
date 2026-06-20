package software.aoc.day8.part2;

import software.aoc.day8.JunctionBox;
import java.util.Set;
import java.util.stream.Stream;
import static java.util.stream.Collectors.toSet;

public class CircuitSet {
    private final Set<Set<JunctionBox>> circuits;
    private final JunctionBox[] lastConnected;

    private CircuitSet(Set<Set<JunctionBox>> circuits, JunctionBox[] lastConnected) {
        this.circuits = circuits;
        this.lastConnected = lastConnected;
    }

    public static CircuitSet from(Stream<JunctionBox> boxes) {
        return new CircuitSet(boxes.map(Set::of).collect(toSet()), new JunctionBox[] {});
    }

    public JunctionBox lastConnectedBoxAt(int index) {
        return lastConnected[index];
    }

    public CircuitSet connect(JunctionBox box1, JunctionBox box2) {
        if (circuitOf(box1).equals(circuitOf(box2))) return this;
        return new CircuitSet(merge(circuitOf(box1), circuitOf(box2)), new JunctionBox[] {box1, box2});
    }

    private Set<JunctionBox> circuitOf(JunctionBox box) {
        return circuits.stream().filter(c -> c.contains(box)).findFirst().orElseThrow();
    }

    private Set<Set<JunctionBox>> merge(Set<JunctionBox> circuit1, Set<JunctionBox> circuit2) {
        return Stream.concat(unaffectedCircuits(circuit1, circuit2),
                mergeCircuits(circuit1, circuit2)).collect(toSet());
    }

    private Stream<Set<JunctionBox>> mergeCircuits(Set<JunctionBox> circuit1, Set<JunctionBox> circuit2) {
        return Stream.of(Stream.concat(circuit1.stream(), circuit2.stream()).collect(toSet()));
    }

    private Stream<Set<JunctionBox>> unaffectedCircuits(Set<JunctionBox> circuit1, Set<JunctionBox> circuit2) {
        return circuits.stream().filter(c -> !c.equals(circuit1) && !c.equals(circuit2));
    }
}
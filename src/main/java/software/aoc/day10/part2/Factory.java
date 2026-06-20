package software.aoc.day10.part2;

import java.util.Arrays;

public class Factory {
    private final Machine[] machines;

    private Factory(Machine[] machines) { this.machines = machines; }

    public static Factory with(String machines) {
        return new Factory(Arrays.stream(machines.split("\\r?\n"))
                .filter(s -> !s.isBlank())
                .map(Machine::from)
                .toArray(Machine[]::new));
    }

    public int configureAllMachines() {
        return Arrays.stream(machines)
                .mapToInt(Machine::configure)
                .sum();
    }
}
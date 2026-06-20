package software.aoc.day10.part1;

import java.util.Arrays;

public class Factory {
    private final Machine[] machines;

    private Factory(Machine[] machines) {
        this.machines = machines;
    }

    public static Factory with(String machines) {
        return new Factory(machinesIn(machines.split("\\r?\n")));
    }

    public int configureAllMachines() {
        return Arrays.stream(machines)
                .mapToInt(Machine::configure)
                .sum();
    }

    private static Machine[] machinesIn(String[] machines) {
        return Arrays.stream(machines)
                .map(Machine::from)
                .toArray(Machine[]::new);
    }
}
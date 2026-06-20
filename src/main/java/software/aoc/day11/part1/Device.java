package software.aoc.day11.part1;

import java.util.List;
import java.util.stream.Stream;

public record Device(String name, List<String> successors) {
    public static Device with(String name) {
        return new Device(name, List.of());
    }

    public Device addSuccessor(String successor) {
        return new Device(this.name,
                Stream.concat(this.successors.stream(), Stream.of(successor)).toList());
    }

    public boolean isOutput() {
        return name.equals("out");
    }
}
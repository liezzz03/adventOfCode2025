package software.aoc.day11.part1;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;
import static java.util.stream.Collectors.toMap;
import static java.util.stream.Collectors.toSet;

public class Reactor {
    private final Map<String, Device> devices;

    private Reactor(Map<String, Device> devices) {
        this.devices = devices;
    }

    public static Reactor from(String devices) {
        return new Reactor(devicesIn(devices.split("\\r?\n")));
    }

    public int nPathsToOut() {
        return nPathsToOut(devices.get("you"), Set.of());
    }

    private int nPathsToOut(Device from, Set<String> visited) {
        if (from.isOutput()) return 1;
        if (visited.contains(from.name())) return 0;
        return from.successors().stream()
                .map(devices::get)
                .mapToInt(d -> nPathsToOut(d, combine(visited, from)))
                .sum();
    }

    private Set<String> combine(Set<String> visited, Device from) {
        return Stream.concat(visited.stream(), Stream.of(from.name())).collect(toSet());
    }

    private static Map<String, Device> devicesIn(String[] devices) {
        return Stream.concat(Arrays.stream(devices).map(Reactor::parseDevice),
                        Stream.of(Device.with("out")))
                .collect(toMap(Device::name, d -> d));
    }

    private static Device parseDevice(String device) {
        return parseDevice(device.split(" "));
    }

    private static Device parseDevice(String[] device) {
        return addSuccessors(Device.with(nameIn(device[0])), device);
    }

    private static String nameIn(String name) {
        return name.substring(0, name.length() - 1);
    }

    private static Device addSuccessors(Device device, String[] deviceInfo) {
        return Arrays.stream(deviceInfo)
                .skip(1)
                .reduce(device,
                        Device::addSuccessor,
                        (device1, b) -> device1);
    }
}
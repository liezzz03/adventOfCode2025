package software.aoc.day11.part2;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import static java.util.stream.Collectors.toMap;

public class Reactor {
    private final Map<String, Device> devices = new HashMap<>();
    private final Map<State, Long> savedStates = new HashMap<>();

    private Reactor(List<Device> devices) {
        devices.forEach(d -> this.devices.put(d.name(), d));
    }

    public static Reactor with(String devices) {
        return new Reactor(parseDevicesIn(devices.split("\\r?\n")));
    }

    public long nPathsToOut() {
        return nPathsToOut(State.start(device("svr")));
    }

    private long nPathsToOut(State currentState) {
        if (currentState.device().isOutput()) return evaluateIfPathValid(currentState);
        return computeIfAbsentOnSavedStates(currentState);
    }

    private long computeIfAbsentOnSavedStates(State currentState) {
        if (savedStates.containsKey(currentState)) return savedStates.get(currentState);
        savedStates.put(currentState, totalPathsToOut(currentState));
        return savedStates.get(currentState);
    }

    private long totalPathsToOut(State currentState) {
        return currentState.device().successors().stream()
                .map(this::device).map(currentState::next)
                .mapToLong(this::nPathsToOut)
                .sum();
    }

    private int evaluateIfPathValid(State state) {
        return state.dacVisited() && state.fftVisited() ? 1 : 0;
    }

    private Device device(String name) {
        return devices.get(name);
    }

    private static List<Device> parseDevicesIn(String[] devices) {
        return parseDevicesIn(Arrays.stream(devices)
                .map(Reactor::parseDevice)
                .collect(toMap(Device::name, d -> d)));
    }

    private static List<Device> parseDevicesIn(Map<String, Device> allDevices) {
        return Stream.concat(allDevices.values().stream(), Stream.of(Device.with("out")))
                .toList();
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
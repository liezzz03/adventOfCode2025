package software.aoc.day11.part2;

public record State(Device device, boolean dacVisited, boolean fftVisited) {

    public static State start(Device device) {
        return new State(device, false, false);
    }

    public State next(Device nextDevice) {
        return new State(
                nextDevice,
                this.dacVisited || nextDevice.name().equals("dac"),
                this.fftVisited || nextDevice.name().equals("fft")
        );
    }
}
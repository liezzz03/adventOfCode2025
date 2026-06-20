package software.aoc.day3.part2;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.lang.Long.parseLong;

public record BatteryBank(int[] batteries) {

    public static BatteryBank from(String bank) {
        return bankWith(bank.split(""));
    }

    public int size() {
        return batteries.length;
    }

    public int valueAt(int idx) {
        return batteries[idx];
    }

    public long maxJoltage() {
        return parseLong(bestJoltageSequence());
    }

    private String bestJoltageSequence() {
        return Stream.iterate(Selector.start(this), Selector::hasNext, Selector::next)
                .map(Selector::bestValue)
                .collect(Collectors.joining());
    }

    private int bestIndexBetween(int startInclusive, int endInclusive) {
        return IntStream.rangeClosed(startInclusive, endInclusive)
                .reduce(this::pickBest)
                .orElse(startInclusive);
    }

    private int pickBest(int i, int j) {
        return batteries[i] >= batteries[j] ? i : j;
    }

    private static BatteryBank bankWith(String[] batteries) {
        return bankWith(Arrays.stream(batteries).mapToInt(Integer::parseInt));
    }

    private static BatteryBank bankWith(IntStream intStream) {
        return new BatteryBank(intStream.toArray());
    }

    private static class Selector {

        private static final int REQUIRED_DIGITS = 12;

        private final BatteryBank batteryBank;
        private final int currentDigitIndex;
        private final int searchStartIndex;

        private Selector(BatteryBank batteryBank, int currentDigitIndex, int searchStartIndex) {
            this.batteryBank = batteryBank;
            this.currentDigitIndex = currentDigitIndex;
            this.searchStartIndex = searchStartIndex;
        }

        public static Selector start(BatteryBank batteryBank) {
            return new Selector(batteryBank, 0, 0);
        }

        public boolean hasNext() {
            return currentDigitIndex < REQUIRED_DIGITS;
        }

        public Selector next() {
            return new Selector(batteryBank, currentDigitIndex + 1, bestIndex() + 1);
        }

        public String bestValue() {
            return String.valueOf(batteryBank.valueAt(bestIndex()));
        }

        public int bestIndex() {
            return batteryBank.bestIndexBetween(searchStartIndex,
                    batteryBank.size() - REQUIRED_DIGITS + currentDigitIndex);
        }
    }

}

package software.aoc.day3.part1;

import java.util.Arrays;
import java.util.stream.IntStream;

public record BatteryBank(int[] batteries) {
    public static BatteryBank from(String bank) {
        return bankWith(bank.split(""));
    }

    public int maxJoltage() {
        return maxJoltage(bestFirstBatteryIndex());
    }

    private int maxJoltage(int firstSelected) {
        return batteries[firstSelected] * 10 + bestSecondBatteryAfter(firstSelected);
    }

    private int bestFirstBatteryIndex() {
        return IntStream.range(0, batteries.length - 1)
                .reduce(this::pickBest)
                .orElse(0);
    }

    private int pickBest(int i, int j) {
        return batteries[i] >= batteries[j] ? i : j;
    }

    private int bestSecondBatteryAfter(int firstSelected) {
        return Arrays.stream(batteries, firstSelected + 1, batteries.length)
                .max()
                .orElse(batteries[batteries.length - 1]);
    }

    private static BatteryBank bankWith(String[] batteries) {
        return bankWith(Arrays.stream(batteries).mapToInt(Integer::parseInt));
    }

    private static BatteryBank bankWith(IntStream intStream) {
        return new BatteryBank(intStream.toArray());
    }
}


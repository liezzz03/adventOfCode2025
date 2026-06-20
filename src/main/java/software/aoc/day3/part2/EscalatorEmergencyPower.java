package software.aoc.day3.part2;

import java.util.Arrays;
import java.util.List;

public class EscalatorEmergencyPower {

    private final List<BatteryBank> banks;

    private EscalatorEmergencyPower(List<BatteryBank> banks) {
        this.banks = banks;
    }

    public static EscalatorEmergencyPower from(String banks) {
        return new EscalatorEmergencyPower(listOf(banks.split("\\r?\\n")));
    }

    public long joltage() {
        return banks.stream()
                .mapToLong(BatteryBank::maxJoltage)
                .sum();
    }

    private static List<BatteryBank> listOf(String[] banks) {
        return Arrays.stream(banks)
                .map(String::trim)           // Mata al \r invisible y espacios
                .filter(s -> !s.isEmpty())   // Ignora las líneas en blanco
                .map(BatteryBank::from)
                .toList();
    }
}
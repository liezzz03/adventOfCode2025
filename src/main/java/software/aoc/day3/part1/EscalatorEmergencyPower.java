package software.aoc.day3.part1;

import java.util.Arrays;
import java.util.List;

public class EscalatorEmergencyPower {
    private final List<BatteryBank> banks;

    private EscalatorEmergencyPower(List<BatteryBank> banks) {
        this.banks = banks;
    }

    public static EscalatorEmergencyPower from(String banks) {
        // Usamos \\r?\\n por si acaso, aunque el trim de abajo ya lo limpiaría
        return new EscalatorEmergencyPower(listOf(banks.split("\\r?\\n")));
    }

    public int joltage() {
        return banks.stream()
                .mapToInt(BatteryBank::maxJoltage)
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
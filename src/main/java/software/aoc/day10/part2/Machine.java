package software.aoc.day10.part2;

import java.util.*;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

public class Machine {
    private static final Pattern BUTTON_PATTERN = Pattern.compile("\\((.*?)\\)");

    private final JoltageState targetJoltage;
    private final List<JoltageEffect> availableEffects;
    private final Map<JoltageState, OptionalInt> knownJoltageStates = new HashMap<>();

    private Machine(JoltageState targetJoltage, List<JoltageEffect> joltageEffects) {
        this.targetJoltage = targetJoltage;
        this.availableEffects = joltageEffects;
    }

    public static Machine from(String machine) {
        JoltageState joltage = targetJoltageFrom(machine.split(" "));
        return new Machine(joltage,
                JoltageEffect.allFrom(buttonsIn(machine, joltage.size()), joltage.size()));
    }

    public int configure() {
        return configureJoltage(targetJoltage).orElse(0);
    }

    private OptionalInt configureJoltage(JoltageState joltageState) {
        if (joltageState.isSolved()) return OptionalInt.of(0);
        return computeIfAbsentOnKnownStates(joltageState);
    }

    private OptionalInt computeIfAbsentOnKnownStates(JoltageState joltageState) {
        if (knownJoltageStates.containsKey(joltageState)) return knownJoltageStates.get(joltageState);
        OptionalInt result = calculateMinPresses(joltageState);
        knownJoltageStates.put(joltageState, result);
        return result;
    }

    private OptionalInt calculateMinPresses(JoltageState joltageState) {
        return availableEffects.stream()
                .filter(joltageState::canApply)
                .map(effect -> nextStateWith(joltageState, effect))
                .flatMapToInt(OptionalInt::stream)
                .min();
    }

    private OptionalInt nextStateWith(JoltageState joltageState, JoltageEffect effect) {
        return configureJoltage(joltageState.nextState(effect)).stream()
                .map(subResult -> effect.buttonPressCount() + 2 * subResult)
                .findFirst();
    }

    private static List<Button> buttonsIn(String machine, int machineSize) {
        return BUTTON_PATTERN.matcher(machine).results()
                .map(MatchResult::group)
                .map(grp -> Button.from(grp, machineSize))
                .toList();
    }

    private static JoltageState targetJoltageFrom(String[] machine) {
        return JoltageState.from(machine[machine.length - 1]);
    }
}
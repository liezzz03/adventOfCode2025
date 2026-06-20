package software.aoc.day10.part1;

import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import static java.lang.Math.pow;

public class Machine {
    private static final Pattern BUTTON_PATTERN = Pattern.compile("\\((.*?)\\)");

    private final LightIndicator light;
    private final List<Button> buttons;

    private Machine(LightIndicator light, List<Button> buttons) {
        this.light = light;
        this.buttons = buttons;
    }

    public static Machine from(String machine) {
        LightIndicator targetLight = LightIndicator.from(machine.split(" ")[0]);
        return new Machine(targetLight, buttonsIn(machine, targetLight.size()));
    }

    public int configure() {
        return allButtonConfigurations()
                .filter(this::matchesLightPattern)
                .map(Integer::bitCount)
                .min().orElse(0);
    }

    private IntStream allButtonConfigurations() {
        return IntStream.rangeClosed(1, (int) pow(2, buttons.size()));
    }

    private boolean matchesLightPattern(int buttonMask) {
        return pressAllButtonsIn(buttonMask) == light.value();
    }

    private int pressAllButtonsIn(int buttonMask) {
        return IntStream.range(0, buttons.size())
                .filter(i -> isButtonPressed(buttonMask, i))
                .map(i -> buttons.get(i).wiring())
                .reduce(0, (a, b) -> a ^ b);
    }

    private boolean isButtonPressed(int buttonMask, int idx) {
        return (buttonMask & (1 << idx)) != 0;
    }

    private static List<Button> buttonsIn(String machine, int machineSize) {
        return BUTTON_PATTERN.matcher(machine).results()
                .map(MatchResult::group)
                .map(grp -> Button.from(grp, machineSize))
                .toList();
    }
}
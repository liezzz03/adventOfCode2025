package software.aoc.day5.part1;

import java.util.Arrays;
import java.util.List;

public class FreshIngredientIdentifier {

    private final List<Range> ranges;

    private FreshIngredientIdentifier(List<Range> ranges) {
        this.ranges = ranges;
    }

    public static FreshIngredientIdentifier create(String ranges) {
        return new FreshIngredientIdentifier(parsedListOf(ranges.split("\\r?\\n")));
    }

    private static List<Range> parsedListOf(String[] ranges) {
        return Arrays.stream(ranges)
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(Range::from)
                .toList();
    }

    public int countFreshIngredientsFrom(String ingredients) {
        return (int) Arrays.stream(ingredients.split("\\r?\\n"))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .mapToLong(Long::parseLong)
                .filter(this::isFresh)
                .count();
    }

    private boolean isFresh(long i) {
        return ranges.stream().anyMatch(r -> r.includes(i));
    }
}
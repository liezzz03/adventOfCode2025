package software.aoc.day12;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;
import static java.lang.Integer.parseInt;
import static java.util.stream.Collectors.toMap;

public class ChristmasTreeFarm {
    private static final ChristmasTreeFarm Null = new ChristmasTreeFarm(List.of());
    private final List<ChristmasTree> christmasTrees;

    private ChristmasTreeFarm(List<ChristmasTree> christmasTrees) {
        this.christmasTrees = christmasTrees;
    }

    public static ChristmasTreeFarm with(String farm) {
        return christmasTreeFarmIn(farm);
    }

    public int placePresents() {
        return christmasTrees.stream()
                .mapToInt(ChristmasTree::solve)
                .sum();
    }

    private static ChristmasTreeFarm christmasTreeFarmIn(String christmasTreeFarm) {
        return christmasTreeFarmIn(christmasTreeFarm,
                Pattern.compile("(?m)^[0-9]+x[0-9]+:.*$").matcher(christmasTreeFarm));
    }

    private static ChristmasTreeFarm christmasTreeFarmIn(String christmasTreeFarm, Matcher matcher) {
        return matcher.find() ? christmasTreeFarmIn(christmasTreeFarm, matcher.start()) : ChristmasTreeFarm.Null;
    }

    private static ChristmasTreeFarm christmasTreeFarmIn(String christmasTreeFarm, int dividingIdx) {
        return new ChristmasTreeFarm(christmasTreesIn(christmasTreeFarm.substring(dividingIdx).trim(),
                presentsIn(christmasTreeFarm.substring(0, dividingIdx).trim())));
    }

    private static List<Present> presentsIn(String presents) {
        return Arrays.stream(presents.split("\\r?\n\\r?\n"))
                .map(Present::with)
                .toList();
    }

    private static List<ChristmasTree> christmasTreesIn(String christmasTrees, List<Present> presentList) {
        return Arrays.stream(christmasTrees.trim().split("\\r?\n"))
                .map(c -> christmasTreeIn(c, presentList))
                .toList();
    }

    private static ChristmasTree christmasTreeIn(String christmasTree, List<Present> presentList) {
        return christmasTreeIn(regionSizeOf(christmasTree), presentsToPlaceIn(christmasTree, presentList));
    }

    private static ChristmasTree christmasTreeIn(int[] size, Map<Present, Integer> presentsToPlace) {
        return ChristmasTree.with(size[0], size[1], presentsToPlace);
    }

    private static int[] regionSizeOf(String christmasTree) {
        return parseRegionSizeOf(christmasTree.trim().split(":")[0]);
    }

    private static int[] parseRegionSizeOf(String christmasTreeSize) {
        return parseRegionSizeOf(christmasTreeSize.trim().split("x"));
    }

    private static int[] parseRegionSizeOf(String[] christmasTreeSize) {
        return new int[] {parseInt(christmasTreeSize[0]), parseInt(christmasTreeSize[1])};
    }

    private static Map<Present, Integer> presentsToPlaceIn(String christmasTree, List<Present> presentList) {
        return presentCountsIn(christmasTree.split(":")[1], presentList);
    }

    private static Map<Present, Integer> presentCountsIn(String presentCounts, List<Present> presentList) {
        return presentCountsIn(presentCounts.trim().split(" "), presentList);
    }

    private static Map<Present, Integer> presentCountsIn(String[] presentCounts, List<Present> presentList) {
        return IntStream.range(0, presentCounts.length).boxed()
                .collect(toMap(presentList::get, i -> parseInt(presentCounts[i])));
    }
}
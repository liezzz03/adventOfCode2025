package software.aoc.day12;

import java.util.*;
import java.util.stream.Stream;
import static java.util.Collections.reverseOrder;

public class ChristmasTree {
    private final ChristmasTreeRegion presentRegion;
    private final Map<Present, Integer> presentsToPlace;
    private final List<List<Present>> allPresentRotations;
    private final Set<Integer> failedStates = new HashSet<>();

    private ChristmasTree(ChristmasTreeRegion presentRegion, Map<Present, Integer> presentsToPlace) {
        this.presentRegion = presentRegion;
        this.presentsToPlace = presentsToPlace;
        this.allPresentRotations = allRotationsOf(expandedPresents());
    }

    public static ChristmasTree with(int width, int height, Map<Present, Integer> presentsToPlace) {
        return new ChristmasTree(ChristmasTreeRegion.with(width, height), presentsToPlace);
    }

    public int solve() {
        if (minPresentArea() > presentRegion.area()) return 0;
        if (maxPresentArea() < presentRegion.area()) return 1;
        return fitPresentsInTree();
    }

    private int maxPresentArea() {
        return presentsToPlace.entrySet().stream()
                .mapToInt(e -> e.getValue() * e.getKey().boundingRectangleArea())
                .sum();
    }

    private int minPresentArea() {
        return presentsToPlace.entrySet().stream()
                .mapToInt(e -> e.getValue() * e.getKey().area())
                .sum();
    }

    private int fitPresentsInTree() {
        return canFitPresent(0, this.presentRegion) ? 1 : 0;
    }

    private List<List<Present>> allRotationsOf(Stream<Present> presents) {
        return presents
                .map(Present::allOrientations)
                .toList();
    }

    private boolean canFitPresent(int index, ChristmasTreeRegion region) {
        if (index == allPresentRotations.size()) return true;
        if (isKnownFailure(index, region)) return false;
        return findFitting(index, region) || markAsFailed(index, region);
    }

    private boolean findFitting(int index, ChristmasTreeRegion region) {
        return allRotationsOfPresentAt(index)
                .flatMap(present -> findFitting(present, region))
                .anyMatch(nextRegion -> canFitPresent(index + 1, nextRegion));
    }

    private Stream<Present> allRotationsOfPresentAt(int index) {
        return allPresentRotations.get(index).stream();
    }

    private Stream<ChristmasTreeRegion> findFitting(Present present, ChristmasTreeRegion region) {
        return validAnchorPoints(present, region)
                .map(position -> region.place(present, position))
                .flatMap(Optional::stream);
    }

    private boolean isKnownFailure(int index, ChristmasTreeRegion region) {
        return failedStates.contains(stateKey(index, region));
    }

    private boolean markAsFailed(int index, ChristmasTreeRegion region) {
        failedStates.add(stateKey(index, region));
        return false;
    }

    private int stateKey(int index, ChristmasTreeRegion region) {
        return Objects.hash(index, Arrays.deepHashCode(region.grid()));
    }

    private Stream<Position> validAnchorPoints(Present present, ChristmasTreeRegion region) {
        return region.validAnchorPoints(present);
    }

    private Stream<Present> expandedPresents() {
        return presentsToPlace.entrySet().stream()
                .flatMap(this::allPresents)
                .sorted(reverseOrder());
    }

    private Stream<Present> allPresents(Map.Entry<Present, Integer> entry) {
        return Stream.generate(entry::getKey).limit(entry.getValue());
    }
}
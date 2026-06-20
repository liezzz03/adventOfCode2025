package software.aoc.day5.part2;

import java.util.List;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;

public class RangeMerger {
    private final List<Range> merged;
    private final Range current;

    private RangeMerger(List<Range> merged, Range current) {
        this.merged = merged;
        this.current = current;
    }

    public static RangeMerger create() {
        return new RangeMerger(emptyList(), Range.Null);
    }

    public List<Range> merge(List<Range> ranges) {
        return ranges.stream().sorted()
                .reduce(RangeMerger.create(), RangeMerger::mergeNext, (a, b) -> a)
                .result();
    }

    private List<Range> result() {
        if (current.equals(Range.Null)) return merged;
        return addCurrentToMerged();
    }

    private RangeMerger mergeNext(Range nextRange) {
        if (current.equals(Range.Null)) return new RangeMerger(merged, nextRange);
        if (current.mergeableWith(nextRange)) return mergeCurrentWith(nextRange);
        return startNewRangeWith(nextRange);
    }

    private RangeMerger mergeCurrentWith(Range nextRange) {
        return new RangeMerger(merged, current.merge(nextRange));
    }

    private RangeMerger startNewRangeWith(Range nextRange) {
        return new RangeMerger(addCurrentToMerged(), nextRange);
    }

    private List<Range> addCurrentToMerged() {
        return Stream.concat(merged.stream(), Stream.of(current))
                .toList();
    }
}
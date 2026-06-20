package software.aoc.day5.part2;

public record Range(long start, long end) implements Comparable<Range> {
    public static final Range Null = new Range(-1, -1);

    public static Range from(String range) {
        return rangeOf(range.split("-"));
    }

    public Range merge(Range range) {
        return new Range(smallestStartGiven(range), longestEndGiven(range));
    }

    public boolean mergeableWith(Range range) {
        return this.end >= range.start() - 1;
    }

    public long rangeExtension() {
        return this.end - this.start + 1;
    }

    private long smallestStartGiven(Range range) {
        return Math.min(this.start, range.start());
    }

    private long longestEndGiven(Range range) {
        return Math.max(this.end, range.end());
    }

    @Override
    public int compareTo(Range other) {
        return Long.compare(this.start, other.start());
    }

    private static Range rangeOf(String[] range) {
        return new Range(Long.parseLong(range[0]), Long.parseLong(range[1]));
    }

}

package software.aoc.day9.part2;

public record Interval(long start, long end) {
    public boolean contains(long x) {
        return start <= x && x <= end;
    }
    public boolean contains(Interval interval) {
        return start <= interval.start() && interval.end() <= end;
    }
}

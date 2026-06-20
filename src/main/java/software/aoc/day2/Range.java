package software.aoc.day2;

import java.util.stream.LongStream;
import static java.lang.Long.parseLong;

public record Range(long start, long end) {

    public static Range from(String range) {
        return rangeIn(range.split("-"));
    }

    public LongStream stream() {
        return LongStream.rangeClosed(start, end);
    }

    private static Range rangeIn(String[] range) {
        return new Range(parseLong(range[0]), parseLong(range[1]));
    }
}

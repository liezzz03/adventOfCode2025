package software.aoc.day2;

import software.aoc.day2.Range;
import java.util.Arrays;
import java.util.List;

public class GiftShopDatabase {
    private final List<Range> ranges;

    private GiftShopDatabase(List<Range> ranges) {
        this.ranges = ranges;
    }

    public static GiftShopDatabase from(String ranges) {
        return new GiftShopDatabase(parseRanges(ranges));
    }

    private static List<Range> parseRanges(String ranges) {
        return parseRanges(ranges.split(","));
    }

    public Long sumInvalidIds() {
        return ranges.stream()
                .flatMapToLong(Range::stream)
                .mapToObj(Product::new)
                .filter(Product::isInvalid)
                .mapToLong(Product::id)
                .sum();
    }

    private static List<Range> parseRanges(String[] ranges) {
        return Arrays.stream(ranges)
                .map(Range::from)
                .toList();
    }

}
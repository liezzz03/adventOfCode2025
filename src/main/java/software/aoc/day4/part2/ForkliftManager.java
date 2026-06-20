package software.aoc.day4.part2;

import java.util.Objects;
import java.util.stream.Stream;

public class ForkliftManager {

    private final Warehouse warehouse;

    private ForkliftManager(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public static ForkliftManager with(String grid) {
        return new ForkliftManager(Warehouse.from(grid));
    }

    public long allAccessibleRolls() {
        return Stream.iterate(warehouse, Objects::nonNull, Warehouse::next)
                .mapToLong(Warehouse::accessibleRollCount)
                .sum();
    }
}

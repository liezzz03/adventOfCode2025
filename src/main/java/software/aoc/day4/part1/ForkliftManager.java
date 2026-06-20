package software.aoc.day4.part1;

public class ForkliftManager {

    private final Warehouse warehouse;

    private ForkliftManager(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    public static ForkliftManager with(String grid) {
        return new ForkliftManager(Warehouse.from(grid));
    }

    public long accesibleRollCount() {
        return warehouse.rollPositions()
                .filter(warehouse::isAccessible)
                .count();
    }
}
package software.aoc.day8;

public record JunctionBox(int x, int y, int z) {
    public static JunctionBox from(String line) {
        return from(line.split(","));
    }

    private static JunctionBox from(String[] coordinates) {
        return new JunctionBox(
                Integer.parseInt(coordinates[0].trim()),
                Integer.parseInt(coordinates[1].trim()),
                Integer.parseInt(coordinates[2].trim())
        );
    }
}
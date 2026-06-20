package software.aoc.day2.part1;

public record Product(long id) {
    public boolean isInvalid() {
        return String.valueOf(id).matches("^([0-9]+)\\1$");
    }
}

package software.aoc.day6;

import java.util.Arrays;

public record Operation(Operator operator, long[] operands) {
    public long result() {
        return operator.apply(Arrays.stream(operands));
    }
}

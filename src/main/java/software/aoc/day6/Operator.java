package software.aoc.day6;

import java.util.function.Function;
import java.util.stream.LongStream;

public enum Operator {
    SUM(LongStream::sum),
    MULTIPLY(s -> s.reduce((a, b) -> a * b).orElse(0L));

    private final Function<LongStream, Long> operation;

    Operator(Function<LongStream, Long> operation) {
        this.operation = operation;
    }

    public long apply(LongStream operands) {
        return operation.apply(operands);
    }

    public static Operator from(String operator) {
        return operator.equals("+") ? SUM : MULTIPLY;
    }
}

package software.aoc.day6;

import java.util.Objects;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class WorksheetSolver {
    public static long solve(WorksheetReader reader) {
        return operationsIn(reader)
                .mapToLong(Operation::result)
                .sum();
    }

    private static Stream<Operation> operationsIn(WorksheetReader reader) {
        return StreamSupport.stream(reader.spliterator(), false)
                .takeWhile(Objects::nonNull);
    }
}

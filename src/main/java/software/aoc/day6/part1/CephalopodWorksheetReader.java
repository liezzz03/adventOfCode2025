package software.aoc.day6.part1;

import software.aoc.day6.Operation;
import software.aoc.day6.Operator;
import software.aoc.day6.WorksheetReader;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public class CephalopodWorksheetReader implements WorksheetReader {
    private final List<String> lines;

    private CephalopodWorksheetReader(List<String> lines) {
        this.lines = lines;
    }

    public static CephalopodWorksheetReader from(String worksheet) {
        return new CephalopodWorksheetReader(Arrays.stream(worksheet.split("\n")).toList());
    }

    @Override
    public Iterator<Operation> iterator() {
        return new RowIterator(lines);
    }

    private static class RowIterator implements Iterator<Operation> {

        private final Iterator<String> operators;
        private final List<Iterator<String>> operands;

        public RowIterator(List<String> lines) {
            this.operators = operatorsIn(lines.getLast());
            this.operands = operandsIn(lines);
        }

        @Override
        public boolean hasNext() {
            return operators.hasNext() && allHaveNextFrom(operands);
        }

        @Override
        public Operation next() {
            return hasNext() ? readNextOperation() : null;
        }

        private Operation readNextOperation() {
            return new Operation(Operator.from(operators.next()), allNextOperands());
        }

        private static boolean allHaveNextFrom(List<Iterator<String>> operandIter) {
            return operandIter.stream().allMatch(Iterator::hasNext);
        }

        private long[] allNextOperands() {
            return operands.stream()
                    .map(Iterator::next)
                    .mapToLong(Long::parseLong)
                    .toArray();
        }

        private static Iterator<String> operatorsIn(String operators) {
            return Arrays.stream(operators.trim().split(" +")).iterator();
        }

        private static List<Iterator<String>> operandsIn(List<String> lines) {
            return lines.stream()
                    .limit(lines.size() - 1)
                    .map(s -> Stream.of(s.trim().split(" +")).iterator())
                    .toList();
        }
    }
}
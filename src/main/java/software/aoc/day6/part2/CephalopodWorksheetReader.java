package software.aoc.day6.part2;

import software.aoc.day6.Operation;
import software.aoc.day6.Operator;
import software.aoc.day6.WorksheetReader;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

public class CephalopodWorksheetReader implements WorksheetReader {
    private final List<String> columns;

    private CephalopodWorksheetReader(List<String> columns) {
        this.columns = trimLeadingBlankColumnsIn(columns);
    }

    private List<String> trimLeadingBlankColumnsIn(List<String> columns) {
        return columns.stream()
                .dropWhile(String::isBlank)
                .toList();
    }

    public static CephalopodWorksheetReader from(String worksheet) {
        return new CephalopodWorksheetReader(transposeColumnsIn(worksheet.split("\n")));
    }

    private static List<String> transposeColumnsIn(String[] worksheet) {
        return transposeColumnsIn(worksheet, worksheet[0].length());
    }

    private static List<String> transposeColumnsIn(String[] worksheet, int worksheetWidth) {
        return IntStream.iterate(worksheetWidth - 1, i -> i >= 0, i -> i - 1)
                .mapToObj(col -> columnIn(worksheet, col))
                .toList();
    }

    private static String columnIn(String[] worksheet, int columnIdx) {
        return Arrays.stream(worksheet)
                .map(line -> charAt(line, columnIdx))
                .collect(joining());
    }

    private static String charAt(String line, int columnIdx) {
        return columnIdx < line.length() ? line.charAt(columnIdx) + "" : " ";
    }

    @Override
    public Iterator<Operation> iterator() {
        return new ColumnIterator(columns);
    }

    private static class ColumnIterator implements Iterator<Operation> {
        private final Iterator<String> columns;

        public ColumnIterator(List<String> columns) {
            this.columns = columns.iterator();
        }

        @Override
        public boolean hasNext() {
            return columns.hasNext();
        }

        @Override
        public Operation next() {
            return read(nextOperation());
        }

        private Operation read(List<String> operation) {
            return operation.isEmpty() ? null : parseOperation(operation);
        }

        private List<String> nextOperation() {
            return Stream.generate(this::nextColumn)
                    .takeWhile(col -> !col.isBlank())
                    .toList();
        }

        private String nextColumn() {
            return columns.hasNext() ? columns.next() : "";
        }

        private Operation parseOperation(List<String> operation) {
            return new Operation(operatorIn(operation), operandsIn(operation));
        }

        private Operator operatorIn(List<String> operation) {
            return operation.stream()
                    .map(col -> col.substring(col.length() - 1))
                    .filter("+*"::contains)
                    .map(Operator::from)
                    .findFirst().orElseThrow();
        }

        private long[] operandsIn(List<String> operation) {
            return operation.stream()
                    .map(this::keepDigits)
                    .filter(s -> !s.isEmpty())
                    .mapToLong(Long::parseLong)
                    .toArray();
        }

        private String keepDigits(String operand) {
            return operand.replaceAll("[^0-9]", "");
        }

    }
}
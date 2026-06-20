package software.aoc.day6;

import java.util.Iterator;

public interface WorksheetReader extends Iterable<Operation> {
    Iterator<Operation> iterator();
}

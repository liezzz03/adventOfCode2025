package software.aoc.day7.part2;

import software.aoc.day7.TachyonCell;
import software.aoc.day7.TachyonGrid;
import java.util.List;
import java.util.stream.IntStream;
import static software.aoc.day7.TachyonCell.*;

public class TachyonManifold {
    private final TachyonGrid grid;

    private TachyonManifold(TachyonGrid grid) {
        this.grid = grid;
    }

    public static TachyonManifold with(String grid) {
        return new TachyonManifold(TachyonGrid.with(grid));
    }

    public long totalPaths() {
        return propagateBeam(1, new Step(firstLayerPaths())).totalPaths();
    }

    private List<Long> firstLayerPaths() {
        return firstRow().stream()
                .map(this::convertToPath)
                .toList();
    }

    private List<TachyonCell> firstRow() {
        return IntStream.range(0, grid.width())
                .mapToObj(c -> grid.at(0, c))
                .toList();
    }

    private long convertToPath(TachyonCell cell) {
        return cell == BEAM ? 1 : 0;
    }

    private Step propagateBeam(int row, Step previousStep) {
        if (row == grid.height()) return previousStep;
        return propagateBeam(row + 1, nextStep(row, previousStep));
    }

    private Step nextStep(int row, Step step) {
        return new Step(nextLayer(row, step));
    }

    private List<Long> nextLayer(int row, Step previousStep) {
        return IntStream.range(0, previousStep.pathsToLayer().size())
                .mapToObj(col -> countNewPaths(row, col, previousStep))
                .toList();
    }

    private long countNewPaths(int row, int col, Step previousStep) {
        return pathsFromAbove(row, col, previousStep) +
                pathsFromSplittingLeft(row, col, previousStep) +
                pathsFromSplittingRight(row, col, previousStep);
    }

    private long pathsFromAbove(int row, int col, Step previousStep) {
        return grid.at(row - 1, col) != SPLITTER ? previousStep.at(col) : 0;
    }

    private long pathsFromSplittingLeft(int row, int col, Step previousStep) {
        return grid.at(row - 1, col - 1) == SPLITTER ? previousStep.at(col - 1) : 0;
    }

    private long pathsFromSplittingRight(int row, int col, Step previousStep) {
        return (grid.at(row - 1, col + 1) == SPLITTER) ? previousStep.at(col + 1) : 0;
    }

    public record Step(List<Long> pathsToLayer) {
        public long at(int col) {
            return (col < 0 || col >= pathsToLayer.size()) ? 0 : pathsToLayer.get(col);
        }

        public long totalPaths() {
            return pathsToLayer.stream().mapToLong(i -> i).sum();
        }
    }
}
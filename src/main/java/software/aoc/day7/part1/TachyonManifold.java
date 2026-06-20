package software.aoc.day7.part1;

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

    public List<TachyonCell> propagateBeam() {
        return propagateBeam(0, initialStep()).layer();
    }

    public int countSplitBeams() {
        return propagateBeam(0, initialStep()).nSplits();
    }

    private Step initialStep() {
        return new Step(firstRow(), 0);
    }

    private List<TachyonCell> firstRow() {
        return IntStream.range(0, grid.width())
                .mapToObj(c -> grid.at(0, c))
                .toList();
    }

    private Step propagateBeam(int row, Step currentStep) {
        if (row == grid.height() - 1) return currentStep;
        return propagateBeam(row + 1, nextStepIn(row, currentStep));
    }

    private Step nextStepIn(int row, Step currentStep) {
        return new Step(nextLayer(row, currentStep.layer()),
                currentStep.nSplits() + countSplits(row, currentStep.layer()));
    }

    private List<TachyonCell> nextLayer(int row, List<TachyonCell> previousLayer) {
        return IntStream.range(0, grid.width())
                .mapToObj(col -> nextCell(row, col, previousLayer))
                .toList();
    }

    private TachyonCell nextCell(int row, int col, List<TachyonCell> previousLayer) {
        return (beamPropagating(row, col, previousLayer) || beamSplit(row, col, previousLayer)) ? BEAM : EMPTY;
    }

    private boolean beamPropagating(int row, int col, List<TachyonCell> previousLayer) {
        return cellAt(col, previousLayer) == BEAM && grid.at(row, col) != SPLITTER;
    }

    private boolean beamSplit(int row, int col, List<TachyonCell> previousLayer) {
        return beamSplitFrom(row, col - 1, previousLayer) || beamSplitFrom(row, col + 1, previousLayer);
    }

    private boolean beamSplitFrom(int row, int col, List<TachyonCell> previousLayer) {
        return cellAt(col, previousLayer) == BEAM && grid.at(row, col) == SPLITTER;
    }

    private TachyonCell cellAt(int col, List<TachyonCell> previousLayer) {
        if (col < 0 || col >= previousLayer.size()) return EMPTY;
        return previousLayer.get(col);
    }

    private int countSplits(int row, List<TachyonCell> currentLayer) {
        return (int) IntStream.range(0, currentLayer.size())
                .filter(col -> currentLayer.get(col) == BEAM && grid.at(row, col) == SPLITTER)
                .count();
    }

    private record Step(List<TachyonCell> layer, int nSplits) {}
}
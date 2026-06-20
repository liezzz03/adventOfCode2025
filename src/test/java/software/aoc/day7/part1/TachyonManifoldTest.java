package software.aoc.day7.part1;

import org.junit.Test;
import software.aoc.day7.TachyonCell;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class TachyonManifoldTest {

    String tachyon_map = """
            .......S.......
            ...............
            .......^.......
            ...............
            ......^.^......
            ...............
            .....^.^.^.....
            ...............
            ....^.^...^....
            ...............
            ...^.^...^.^...
            ...............
            ..^...^.....^..
            ...............
            .^.^.^.^.^...^.
            ...............
            """;

    @Test
    public void given_tachyon_map_should_account_beam_propagation() {
        assertThat(intArrayIn(TachyonManifold.with("""
                                             ...S...
                                             ...^...
                                             ..^...^
                                             .^..^..
                                             .......
                                             """).propagateBeam()))
                .isEqualTo(new int[] {83, 46, 83, 83, 46, 83, 46});
        assertThat(intArrayIn(TachyonManifold.with(tachyon_map).propagateBeam()))
                .isEqualTo(new int[] {83, 46, 83, 46, 83, 46, 83, 46, 83, 46, 83, 83, 83, 46, 83});
    }

    @Test
    public void given_tachyon_map_should_account_number_of_split_beams() {
        assertThat(TachyonManifold.with("""
                                             ...S...
                                             ...^...
                                             ..^...^
                                             .^..^..
                                             .......
                                             """).countSplitBeams()).isEqualTo(4);
        assertThat(TachyonManifold.with(tachyon_map).countSplitBeams()).isEqualTo(21);
    }

    @Test
    public void reward() throws IOException {
        try (InputStream inputStream = TachyonManifoldTest.class.getResourceAsStream("/day7/input7.txt")) {
            assertThat(TachyonManifold.with(new String(inputStream.readAllBytes())).countSplitBeams()).isEqualTo(1566);
        }
    }

    private int[] intArrayIn(List<TachyonCell> tachyonCells) {
        return tachyonCells.stream()
                .mapToInt(TachyonCell::cellValue)
                .toArray();
    }
}
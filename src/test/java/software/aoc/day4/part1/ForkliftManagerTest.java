package software.aoc.day4.part1;

import org.junit.Test;
import java.io.IOException;
import java.io.InputStream;
import static org.assertj.core.api.Assertions.assertThat;

public class ForkliftManagerTest {

    public final static String diagram = """
                                        ..@@.@@@@.
                                        @@@.@.@.@@
                                        @@@@@.@.@@
                                        @.@@@@..@.
                                        @@.@@@@.@@
                                        .@@@@@@@.@
                                        .@.@.@.@@@
                                        @.@@@.@@@@
                                        .@@@@@@@@.
                                        @.@.@@@.@.
                                        """;

    @Test
    public void given_diagram_should_account_number_of_rolls_accessible() {
        assertThat(ForkliftManager.with("""
                                             ··@@·@··
                                             ·@·@@···
                                             ··@@··@·
                                             """).accesibleRollCount()).isEqualTo(7);
        assertThat(ForkliftManager.with(diagram).accesibleRollCount()).isEqualTo(13);
    }

    @Test
    public void reward() throws IOException {
        try (InputStream inputStream = ForkliftManagerTest.class.getResourceAsStream("/day4/input4.txt")) {
            assertThat(ForkliftManager.with(new String(inputStream.readAllBytes())).accesibleRollCount()).isEqualTo(1587L);
        }
    }
}
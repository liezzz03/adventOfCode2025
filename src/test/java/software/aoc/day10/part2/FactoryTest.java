package software.aoc.day10.part2;

import org.junit.Test;
import java.io.IOException;
import java.io.InputStream;
import static org.assertj.core.api.Assertions.assertThat;

public class FactoryTest {
    private static final String machines = """
                                                      [.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}
                                                      [...#.] (0,2,3,4) (2,3) (0,4) (0,1,2) (1,2,3,4) {7,5,12,7,2}
                                                      [.###.#] (0,1,2,3,4) (0,3,4) (0,1,2,4,5) (1,2) {10,11,11,5,10,5}
                                                      """;

    @Test
    public void given_joltage_requirements_should_count_minimum_button_presses() {
        assertThat(Factory.with(machines)
                .configureAllMachines()).isEqualTo(33);
    }

    @Test
    public void reward() throws IOException {
        try (InputStream inputStream = FactoryTest.class.getResourceAsStream("/day10/input10.txt")) {
            assertThat(Factory.with(new String(inputStream.readAllBytes())).configureAllMachines())
                    .isEqualTo(16765);
        }
    }
}
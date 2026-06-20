package software.aoc.day8.part2;

import org.junit.Test;
import java.io.IOException;
import java.io.InputStream;
import static org.assertj.core.api.Assertions.assertThat;

public class PlaygroundTest {
    String inputBoxes = """    
                        162,817,812
                        57,618,57
                        906,360,560
                        592,479,940
                        352,342,300
                        466,668,158
                        542,29,236
                        431,825,988
                        739,650,466
                        52,470,668
                        216,146,977
                        819,987,18
                        117,168,530
                        805,96,715
                        346,949,466
                        970,615,88
                        941,993,340
                        862,61,35
                        984,92,344
                        425,690,689
                        """;

    @Test
    public void given_boxes_should_account_largest_circuits() {
        assertThat(Playground.with("""
                                          1,2,3
                                          9,8,7
                                          10,11,12
                                          13,1,15
                                          2,3,4
                                          2,2,2
                                          10,10,10
                                          23,23,23
                                          """).lastConnectedBoxes()).isEqualTo(230);
        assertThat(Playground.with(inputBoxes).lastConnectedBoxes()).isEqualTo(25272);
    }

    @Test
    public void reward() throws IOException {
        try (InputStream inputStream = PlaygroundTest.class.getResourceAsStream("/day8/input8.txt")) {
            assertThat(Playground.with(new String(inputStream.readAllBytes())).lastConnectedBoxes())
                    .isEqualTo(7499461416L);
        }
    }
}
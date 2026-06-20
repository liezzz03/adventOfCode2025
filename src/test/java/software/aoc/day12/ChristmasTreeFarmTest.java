package software.aoc.day12;

import org.junit.Test;
import java.io.IOException;
import java.io.InputStream;
import static org.assertj.core.api.Assertions.assertThat;

public class ChristmasTreeFarmTest {
    private static final String christmasTreeFarm = """
                                                    0:
                                                    ###
                                                    ##.
                                                    ##.
                                                    
                                                    1:
                                                    ###
                                                    ##.
                                                    .##
                                                    
                                                    2:
                                                    .##
                                                    ###
                                                    ##.
                                                    
                                                    3:
                                                    ##.
                                                    ###
                                                    ##.
                                                    
                                                    4:
                                                    ###
                                                    #..
                                                    ###
                                                    
                                                    5:
                                                    ###
                                                    .#.
                                                    ###
                                                    
                                                    4x4: 0 0 0 0 2 0
                                                    12x5: 1 0 1 0 2 2
                                                    12x5: 1 0 1 0 3 2
                                                    """;

    @Test
    public void given_presents_and_trees_should_count_fitting_trees() {
        assertThat(ChristmasTreeFarm.with(christmasTreeFarm).placePresents()).isEqualTo(2);
    }

    @Test
    public void reward() throws IOException {
        try (InputStream inputStream = ChristmasTreeFarmTest.class.getResourceAsStream("/day12/input12.txt")) {
            assertThat(ChristmasTreeFarm.with(new String(inputStream.readAllBytes())).placePresents()).isEqualTo(472);
        }
    }
}
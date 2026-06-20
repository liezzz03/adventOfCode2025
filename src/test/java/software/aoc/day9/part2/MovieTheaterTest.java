package software.aoc.day9.part2;

import org.junit.Test;
import java.io.IOException;
import java.io.InputStream;
import static org.assertj.core.api.Assertions.assertThat;

public class MovieTheaterTest {

    String inputTiles = """    
                        7,1
                        11,1
                        11,7
                        9,7
                        9,5
                        2,5
                        2,3
                        7,3
                        """;

    @Test
    public void given_tiles_should_account_largest_area() {
        assertThat(MovieTheater.with(inputTiles).largestArea()).isEqualTo(24);
    }

    @Test
    public void reward() throws IOException {
        try (InputStream inputStream = MovieTheaterTest.class.getResourceAsStream("/day9/input9.txt")) {
            assertThat(MovieTheater.with(new String(inputStream.readAllBytes())).largestArea())
                    .isEqualTo(1571016172L);
        }
    }
}
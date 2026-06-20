package software.aoc.day11.part1;

import org.junit.Test;
import java.io.IOException;
import java.io.InputStream;
import static org.assertj.core.api.Assertions.assertThat;

public class ReactorTest {
    static final String devices = """
                              aaa: you hhh
                              you: bbb ccc
                              bbb: ddd eee
                              ccc: ddd eee fff
                              ddd: ggg
                              eee: out
                              fff: out
                              ggg: out
                              hhh: ccc fff iii
                              iii: out
                              """;

    @Test
    public void given_devices_should_count_number_of_paths_to_out() {
        assertThat(Reactor.from(devices).nPathsToOut()).isEqualTo(5);
    }

    @Test
    public void reward() throws IOException {
        try (InputStream inputStream = ReactorTest.class.getResourceAsStream("/day11/input11.txt")) {
            assertThat(Reactor.from(new String(inputStream.readAllBytes())).nPathsToOut())
                    .isEqualTo(688);
        }
    }
}
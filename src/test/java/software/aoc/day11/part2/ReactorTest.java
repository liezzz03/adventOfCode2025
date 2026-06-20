package software.aoc.day11.part2;

import org.junit.Test;
import java.io.IOException;
import java.io.InputStream;
import static org.assertj.core.api.Assertions.assertThat;

public class ReactorTest {
    static final String devices = """
                                  svr: aaa bbb
                                  aaa: fft
                                  fft: ccc
                                  bbb: tty
                                  tty: ccc
                                  ccc: ddd eee
                                  ddd: hub
                                  hub: fff
                                  eee: dac
                                  dac: fff
                                  fff: ggg hhh
                                  ggg: out
                                  hhh: out
                                  """;

    @Test
    public void given_devices_should_count_number_of_valid_paths_to_out() {
        assertThat(Reactor.with(devices).nPathsToOut()).isEqualTo(2);
    }

    @Test
    public void reward() throws IOException {
        try (InputStream inputStream = ReactorTest.class.getResourceAsStream("/day11/input11.txt")) {
            assertThat(Reactor.with(new String(inputStream.readAllBytes())).nPathsToOut())
                    .isEqualTo(293263494406608L);
        }
    }

}
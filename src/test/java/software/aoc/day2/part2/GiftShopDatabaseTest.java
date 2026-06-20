package software.aoc.day2.part2;

import org.junit.Test;
import java.io.IOException;
import java.io.InputStream;
import static org.assertj.core.api.Assertions.assertThat;

public class GiftShopDatabaseTest {
    public final static String ranges = """
                                        11-22
                                        95-115
                                        998-1012
                                        1188511880-1188511890
                                        222220-222224
                                        1698522-1698528
                                        446443-446449
                                        38593856-38593862
                                        565653-565659
                                        824824821-824824827
                                        2121212118-2121212124
                                        """;

    @Test
    public void given_id_ranges_should_sum_invalid_ids() {
        assertThat(GiftShopDatabase.from("10-20").sumInvalidIds()).isEqualTo(11);
        assertThat(GiftShopDatabase.from("11-22").sumInvalidIds()).isEqualTo(33);
        assertThat(GiftShopDatabase.from(ranges.replace("\n", ",")).sumInvalidIds())
                .isEqualTo(4174379265L);
    }

    @Test
    public void reward() throws IOException {
        try (InputStream inputStream = GiftShopDatabaseTest.class.getResourceAsStream("/day2/input2.txt")) {
            assertThat(GiftShopDatabase.from(new String(inputStream.readAllBytes())).sumInvalidIds())
                    .isEqualTo(31755323497L);
        }
    }
}

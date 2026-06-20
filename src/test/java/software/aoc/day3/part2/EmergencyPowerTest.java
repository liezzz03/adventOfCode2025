package software.aoc.day3.part2;

import org.junit.Test;
import java.io.IOException;
import java.io.InputStream;
import static org.assertj.core.api.Assertions.assertThat;

public class EmergencyPowerTest {
    public final static String batteryBanks = """
                                              987654321111111
                                              811111111111119
                                              234234234234278
                                              818181911112111
                                              """;

    @Test
    public void given_battery_bank_should_account_joltage() {
        assertThat(EscalatorEmergencyPower.from("989216248294345").joltage()).isEqualTo(996248294345L);
        assertThat(EscalatorEmergencyPower.from("108337364723224").joltage()).isEqualTo(837364723224L);
        assertThat(EscalatorEmergencyPower.from("123016263670993").joltage()).isEqualTo(316263670993L);
    }

    @Test
    public void given_multiple_battery_banks_should_account_joltage() {
        assertThat(EscalatorEmergencyPower.from("989216248294\n123016263670").joltage()).isEqualTo(1112232511964L);
        assertThat(EscalatorEmergencyPower.from("123512314532\n111113332321").joltage()).isEqualTo(234625646853L);
        assertThat(EscalatorEmergencyPower.from(batteryBanks).joltage()).isEqualTo(3121910778619L);
    }

    @Test
    public void reward() throws IOException {
        try (InputStream inputStream = EmergencyPowerTest.class.getResourceAsStream("/day3/input3.txt")) {
            assertThat(EscalatorEmergencyPower.from(new String(inputStream.readAllBytes())).joltage())
                    .isEqualTo(167549941654721L);
        }
    }
}

package software.aoc.day3.part1;

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
        assertThat(EscalatorEmergencyPower.from("989216248294").joltage()).isEqualTo(99);
        assertThat(EscalatorEmergencyPower.from("108337364723").joltage()).isEqualTo(87);
        assertThat(EscalatorEmergencyPower.from("123016263670").joltage()).isEqualTo(70);
    }

    @Test
    public void given_multiple_battery_banks_should_account_joltage() {
        assertThat(EscalatorEmergencyPower.from("989216248294\n123016263670").joltage()).isEqualTo(169);
        assertThat(EscalatorEmergencyPower.from("123512314532\n111113332321").joltage()).isEqualTo(88);
        assertThat(EscalatorEmergencyPower.from(batteryBanks).joltage()).isEqualTo(357);
    }

    @Test
    public void reward() throws IOException {
        try (InputStream inputStream = EmergencyPowerTest.class.getResourceAsStream("/day3/input3.txt")) {
            assertThat(EscalatorEmergencyPower.from(new String(inputStream.readAllBytes())).joltage())
                    .isEqualTo(16858);
        }
    }
}

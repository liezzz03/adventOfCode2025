package software.aoc.day5.part2;

import org.junit.Test;
import java.io.IOException;
import java.io.InputStream;
import static org.assertj.core.api.Assertions.assertThat;

public class FreshIngredientIdentifierTest {
    public final static String ranges = """
                                        3-5
                                        10-14
                                        16-20
                                        12-18
                                        """;

    @Test
    public void given_ranges_should_count_fresh_ingredients() {
        assertThat(FreshIngredientIdentifier.create("10-20")
                .countFreshIngredients()).isEqualTo(11);
        assertThat(FreshIngredientIdentifier.create(ranges)
                .countFreshIngredients()).isEqualTo(14);
    }

    @Test
    public void reward() throws IOException {
        try (InputStream inputStream = FreshIngredientIdentifier.class.getResourceAsStream("/day5/input5.txt")) {
            assertThat(createRangesAndCountFreshIngredientsFrom(inputStream))
                    .isEqualTo(347468726696961L);
        }
    }

    private long createRangesAndCountFreshIngredientsFrom(InputStream inputStream) throws IOException {
        return createRangesAndCountFreshIngredientsFrom(new String(inputStream.readAllBytes()));
    }

    private long createRangesAndCountFreshIngredientsFrom(String str) {
        return createRangesAndCountFreshIngredientsFrom(str.split("\\r?\\n\\r?\\n"));
    }

    private long createRangesAndCountFreshIngredientsFrom(String[] split) {
        return FreshIngredientIdentifier.create(split[0])
                .countFreshIngredients();
    }
}

package software.aoc.day5.part1;

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

    public final static String ingredients = """
                                             1
                                             5
                                             8
                                             11
                                             17
                                             32
                                             """;

    @Test
    public void given_ranges_should_count_fresh_ingredients() {
        assertThat(FreshIngredientIdentifier.create("10-20")
                .countFreshIngredientsFrom("1\n5\n10\n15\n20")).isEqualTo(3);
        assertThat(FreshIngredientIdentifier.create(ranges)
                .countFreshIngredientsFrom(ingredients)).isEqualTo(3);
    }

    @Test
    public void reward() throws IOException {
        try (InputStream inputStream = FreshIngredientIdentifier.class.getResourceAsStream("/day5/input5.txt")) {
            assertThat(createRangesAndCountFreshIngredientsFrom(inputStream)).isEqualTo(744);
        }
    }

    private int createRangesAndCountFreshIngredientsFrom(InputStream inputStream) throws IOException {
        return createRangesAndCountFreshIngredientsFrom(new String(inputStream.readAllBytes()));
    }

    private int createRangesAndCountFreshIngredientsFrom(String string) {
        return createRangesAndCountFreshIngredientsFrom(string.split("\\r?\\n\\r?\\n"));
    }

    private int createRangesAndCountFreshIngredientsFrom(String[] split) {
        return FreshIngredientIdentifier.create(split[0])
                .countFreshIngredientsFrom(split[1]);
    }

}

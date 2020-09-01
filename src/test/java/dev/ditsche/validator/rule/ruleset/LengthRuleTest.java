package dev.ditsche.validator.rule.ruleset;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class LengthRuleTest {

    private final LengthRule lengthRule = new LengthRule(6);

    @Test
    public void shouldFailIfNoStringIsProvided() {
        Stream.of(null, 1, new LinkedList<>(), 2.00f).forEach(value -> {
            assertThat(lengthRule.test(value).isPassed()).isFalse();
        });
    }

    @Test
    public void shouldFailIfValueIsTooLong() {
        Stream.of("212.35.234.256", "Testing things", 7, List.of(1,2,3,4,5,6,7)).forEach(value -> {
            assertThat(lengthRule.test(value).isPassed()).isFalse();
        });
    }

    @Test
    public void shouldFailIfValueIsTooShort() {
        Stream.of("", "Test", 5, List.of(1,2,3,4)).forEach(value -> {
            assertThat(lengthRule.test(value).isPassed()).isFalse();
        });
    }

    @Test
    public void shouldPassWithValidLengths() {
        Stream.of("Tobias", "123456", 6, List.of(1,2,3,4,5,6)).forEach(value -> {
            assertThat(lengthRule.test(value).isPassed()).isTrue();
        });
    }

    @Test
    public void shouldReturnValidErrorMessage() {
        assertThat(lengthRule.message("test")).isEqualTo("The field \"test\" needs to have a length of 6");
    }

}
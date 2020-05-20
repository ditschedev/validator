package dev.ditsche.validator.rule.ruleset;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class SizeRuleTest {

    private final SizeRule sizeRule = new SizeRule(5, 10);

    @Test
    public void shouldFailWithInvalidTypes() {
        Stream.of(LocalDateTime.now(), null).forEach(value -> {
            assertThat(sizeRule.passes(value).isPassed()).isFalse();
        });
    }

    @Test
    public void shouldFailWithInvalidSizes() {
        Stream.of("abc", "jdugrdghjkjgd", "", -2, 4, 11, 20, 100L,
                11L, List.of(1,2,3)).forEach(value -> {
            assertThat(sizeRule.passes(value).isPassed()).isFalse();
        });
    }

    @Test
    public void shouldPassWithValidSizes() {
        Stream.of("abcdefg", List.of(1,2,3,4,5,6,7), 5, 10, 8).forEach(value -> {
            assertThat(sizeRule.passes(value).isPassed()).isTrue();
        });
    }

    @Test
    public void shouldReturnValidErrorMessage() {
        assertThat(sizeRule.message("test")).isEqualTo("The field \"test\" needs to be between 5 and 10");
    }

}
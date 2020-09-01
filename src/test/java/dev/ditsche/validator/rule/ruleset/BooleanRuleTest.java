package dev.ditsche.validator.rule.ruleset;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class BooleanRuleTest {

    private final boolean value = true;
    private final BooleanRule booleanRule = new BooleanRule(value);

    @Test
    public void shouldFailIfNoBooleanIsProvided() {
        Stream.of(null, 1, new LinkedList<>(), 2.00f, "false").forEach(value -> {
            assertThat(booleanRule.test(value).isPassed()).isFalse();
        });
    }

    @Test
    public void shouldFailWithOppositeValue() {
        assertThat(booleanRule.test(!value).isPassed()).isFalse();
    }

    @Test
    public void shouldPassWithValidInput() {
        assertThat(booleanRule.test(value).isPassed()).isTrue();
    }

    @Test
    public void shouldReturnValidErrorMessage() {
        assertThat(booleanRule.message("test")).isEqualTo("The field \"test\" needs to be truthy");
        final BooleanRule br = new BooleanRule(false);
        assertThat(br.message("test")).isEqualTo("The field \"test\" needs to be falsy");
    }

}
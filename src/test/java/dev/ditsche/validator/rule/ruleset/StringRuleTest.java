package dev.ditsche.validator.rule.ruleset;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class StringRuleTest {

    private final StringRule stringRule = new StringRule();

    @Test
    public void shouldFailIfNoStringIsProvided() {
        Stream.of(null, 1, new LinkedList<>(), 2.00f, false).forEach(value -> assertThat(stringRule.test(value).isPassed()).isFalse());
    }

    @Test
    public void shouldPassWithValidInput() {
        assertThat(stringRule.test("test").isPassed()).isTrue();
    }

    @Test
    public void shouldReturnValidErrorMessage() {
        assertThat(stringRule.message("test")).isEqualTo("The field \"test\" needs to be a string");
    }

}
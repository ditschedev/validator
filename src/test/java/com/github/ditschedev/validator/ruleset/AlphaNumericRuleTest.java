package com.github.ditschedev.validator.ruleset;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class AlphaNumericRuleTest {

    private final AlphaNumericRule alphaNumericRule = new AlphaNumericRule();

    @Test
    public void shouldFailIfNoStringIsProvided() {
        Stream.of(null, 1, new LinkedList<>(), 2.00f).forEach(value -> {
            assertThat(alphaNumericRule.passes(value)).isFalse();
        });
    }

    @Test
    public void shouldFailIfStringIsInvalid() {
        Stream.of("hello_user123", "Hello*World", "|Pipes|", "!Username", "==Test==").forEach(value -> {
            assertThat(alphaNumericRule.passes(value)).isFalse();
        });
    }

    @Test
    public void shouldPassIfEmailIsValid() {
        Stream.of("username293", "test1234", "1220halloe02").forEach(value -> {
            assertThat(alphaNumericRule.passes(value)).isTrue();
        });
    }

    @Test
    public void shouldReturnValidErrorMessage() {
        assertThat(alphaNumericRule.message("test")).isEqualTo("The field \"test\" must be alpha numeric");
    }

}
package com.github.ditschedev.validator.ruleset;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MaxRuleTest {

    private final MaxRule maxRule = new MaxRule(12);

    @Test
    public void shouldFailWithBiggerInteger() {
        assertThat(maxRule.passes(13)).isFalse();
    }

    @Test
    public void shouldFailWithBiggerLong() {
        assertThat(maxRule.passes(13L)).isFalse();
    }

    @Test
    public void shouldFailWithLongString() {
        assertThat(maxRule.passes("1234567890123456789")).isFalse();
    }

    @Test
    public void shouldPassWithSameInteger() {
        assertThat(maxRule.passes(12)).isTrue();
    }

    @Test
    public void shouldPassWithSameLong() {
        assertThat(maxRule.passes(12L)).isTrue();
    }

    @Test
    public void shouldPassWithSameString() {
        assertThat(maxRule.passes("abcdefghijkl")).isTrue();
    }

    @Test
    public void shouldPassWithLowerInteger() {
        assertThat(maxRule.passes(2)).isTrue();
    }

    @Test
    public void shouldPassWithLowerLong() {
        assertThat(maxRule.passes(5L)).isTrue();
    }

    @Test
    public void shouldPassWithLowerString() {
        assertThat(maxRule.passes("abc")).isTrue();
    }

    @Test
    public void shouldReturnValidErrorMessage() {
        assertThat(maxRule.message("test")).isEqualTo("The field \"test\" must be shorter than 12");
    }

}
package dev.ditsche.validator.ruleset;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MinRuleTest {

    private final MinRule minRule = new MinRule(12);

    @Test
    public void shouldFailWithSmallerInteger() {
        assertThat(minRule.passes(11)).isFalse();
    }

    @Test
    public void shouldFailWithBiggerLong() {
        assertThat(minRule.passes(11L)).isFalse();
    }

    @Test
    public void shouldFailWithShortString() {
        assertThat(minRule.passes("abc")).isFalse();
    }

    @Test
    public void shouldPassWithSameInteger() {
        assertThat(minRule.passes(12)).isTrue();
    }

    @Test
    public void shouldPassWithSameLong() {
        assertThat(minRule.passes(12L)).isTrue();
    }

    @Test
    public void shouldPassWithSameString() {
        assertThat(minRule.passes("abcdefghijkl")).isTrue();
    }

    @Test
    public void shouldPassWithBiggerInteger() {
        assertThat(minRule.passes(20)).isTrue();
    }

    @Test
    public void shouldPassWithBiggerLong() {
        assertThat(minRule.passes(13L)).isTrue();
    }

    @Test
    public void shouldPassWithBiggerString() {
        assertThat(minRule.passes("abcdefghijklmnopqrstuvwxyz")).isTrue();
    }

    @Test
    public void shouldReturnValidErrorMessage() {
        assertThat(minRule.message("test")).isEqualTo("The field \"test\" must be greater than 12");
    }

}
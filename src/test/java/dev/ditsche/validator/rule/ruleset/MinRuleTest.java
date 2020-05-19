package dev.ditsche.validator.rule.ruleset;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MinRuleTest {

    private final MinRule minRule = new MinRule(12);

    @Test
    public void shouldFailWithSmallerInteger() {
        assertThat(minRule.passes(11).isPassed()).isFalse();
    }

    @Test
    public void shouldFailWithBiggerLong() {
        assertThat(minRule.passes(11L).isPassed()).isFalse();
    }

    @Test
    public void shouldFailWithShortString() {
        assertThat(minRule.passes("abc").isPassed()).isFalse();
    }

    @Test
    public void shouldPassWithSameInteger() {
        assertThat(minRule.passes(12).isPassed()).isTrue();
    }

    @Test
    public void shouldPassWithSameLong() {
        assertThat(minRule.passes(12L).isPassed()).isTrue();
    }

    @Test
    public void shouldPassWithSameString() {
        assertThat(minRule.passes("abcdefghijkl").isPassed()).isTrue();
    }

    @Test
    public void shouldPassWithBiggerInteger() {
        assertThat(minRule.passes(20).isPassed()).isTrue();
    }

    @Test
    public void shouldPassWithBiggerLong() {
        assertThat(minRule.passes(13L).isPassed()).isTrue();
    }

    @Test
    public void shouldPassWithBiggerString() {
        assertThat(minRule.passes("abcdefghijklmnopqrstuvwxyz").isPassed()).isTrue();
    }

    @Test
    public void shouldReturnValidErrorMessage() {
        assertThat(minRule.message("test")).isEqualTo("The field \"test\" must be greater than 12");
    }

}
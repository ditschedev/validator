package dev.ditsche.validator.rule.ruleset;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MaxRuleTest {

    private final MaxRule maxRule = new MaxRule(12);

    @Test
    public void shouldFailWithBiggerInteger() {
        assertThat(maxRule.test(13).isPassed()).isFalse();
    }

    @Test
    public void shouldFailWithBiggerLong() {
        assertThat(maxRule.test(13L).isPassed()).isFalse();
    }

    @Test
    public void shouldFailWithLongString() {
        assertThat(maxRule.test("1234567890123456789").isPassed()).isFalse();
    }

    @Test
    public void shouldPassWithSameInteger() {
        assertThat(maxRule.test(12).isPassed()).isTrue();
    }

    @Test
    public void shouldPassWithSameLong() {
        assertThat(maxRule.test(12L).isPassed()).isTrue();
    }

    @Test
    public void shouldPassWithSameString() {
        assertThat(maxRule.test("abcdefghijkl").isPassed()).isTrue();
    }

    @Test
    public void shouldPassWithLowerInteger() {
        assertThat(maxRule.test(2).isPassed()).isTrue();
    }

    @Test
    public void shouldPassWithLowerLong() {
        assertThat(maxRule.test(5L).isPassed()).isTrue();
    }

    @Test
    public void shouldPassWithLowerString() {
        assertThat(maxRule.test("abc").isPassed()).isTrue();
    }

    @Test
    public void shouldReturnValidErrorMessage() {
        assertThat(maxRule.message("test")).isEqualTo("The field \"test\" must be shorter than 12");
    }

}
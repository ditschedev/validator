package dev.ditsche.validator.rule.ruleset;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MinRuleTest {

    private final MinRule minRule = new MinRule(12);

    @Test
    public void shouldFailWithSmallerInteger() {
        assertThat(minRule.test(11).isPassed()).isFalse();
    }

    @Test
    public void shouldFailWithBiggerLong() {
        assertThat(minRule.test(11L).isPassed()).isFalse();
    }

    @Test
    public void shouldFailWithShortString() {
        assertThat(minRule.test("abc").isPassed()).isFalse();
    }

    @Test
    public void shouldPassWithSameInteger() {
        assertThat(minRule.test(12).isPassed()).isTrue();
    }

    @Test
    public void shouldPassWithSameLong() {
        assertThat(minRule.test(12L).isPassed()).isTrue();
    }

    @Test
    public void shouldPassWithSameString() {
        assertThat(minRule.test("abcdefghijkl").isPassed()).isTrue();
    }

    @Test
    public void shouldPassWithBiggerInteger() {
        assertThat(minRule.test(20).isPassed()).isTrue();
    }

    @Test
    public void shouldPassWithBiggerLong() {
        assertThat(minRule.test(13L).isPassed()).isTrue();
    }

    @Test
    public void shouldPassWithBiggerString() {
        assertThat(minRule.test("abcdefghijklmnopqrstuvwxyz").isPassed()).isTrue();
    }

    @Test
    public void shouldReturnValidErrorMessage() {
        assertThat(minRule.message("test")).isEqualTo("The field \"test\" must be greater than 12");
    }

}
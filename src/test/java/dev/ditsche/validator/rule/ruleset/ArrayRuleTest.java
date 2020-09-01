package dev.ditsche.validator.rule.ruleset;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class ArrayRuleTest {

    private final ArrayRule arrayRule = new ArrayRule();

    @Test
    public void shouldFailIfNoStringIsProvided() {
        Stream.of(null, 1, "", 2.00f, false).forEach(value -> assertThat(arrayRule.test(value).isPassed()).isFalse());
    }

    @Test
    public void shouldPassWithArrayAsInput() {
        assertThat(arrayRule.test(new int[2]).isPassed()).isTrue();
        assertThat(arrayRule.test(new Object[4]).isPassed()).isTrue();
    }

    @Test
    public void shouldPassWithListAsInput() {
        assertThat(arrayRule.test(new LinkedList<>()).isPassed()).isTrue();
        assertThat(arrayRule.test(new ArrayList<>()).isPassed()).isTrue();
    }

    @Test
    public void shouldReturnValidErrorMessage() {
        assertThat(arrayRule.message("test")).isEqualTo("The field \"test\" needs to be an array");
    }

}
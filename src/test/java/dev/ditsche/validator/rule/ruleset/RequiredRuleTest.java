package dev.ditsche.validator.rule.ruleset;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class RequiredRuleTest {

    private final RequiredRule requiredRule = new RequiredRule();

    @Test
    public void shouldFailOnEmptyString() {
        Stream.of("", "\t", " ").forEach(value ->
                assertThat(requiredRule.test(value).isPassed()).isFalse()
        );
    }

    @Test
    public void shouldFailOnEmptyCollection() {
        Stream.of(new ArrayList<>(), new LinkedList<>(), new HashSet<>(), new HashMap<>())
                .forEach(col -> {
                    assertThat(requiredRule.test(col).isPassed()).isFalse();
                });
    }

    @Test
    public void shouldPassOnValidString() {
        assertThat(requiredRule.test("a").isPassed()).isTrue();
    }

    @Test
    public void shouldPassOnNonEmptyList() {
        assertThat(requiredRule.test(List.of(1)).isPassed()).isTrue();
    }

    @Test
    public void shouldReturnValidErrorMessage() {
        assertThat(requiredRule.message("test")).isEqualTo("The field \"test\" is required");
    }

}
package com.github.ditschedev.validator.ruleset;

import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class RequiredRuleTest {

    private final RequiredRule requiredRule = new RequiredRule();

    @Test
    public void shouldFailOnEmptyString() {
        Stream.of("", "\t", " ").forEach(value ->
                assertThat(requiredRule.passes(value)).isFalse()
        );
    }

    @Test
    public void shouldFailOnEmptyCollection() {
        Stream.of(new ArrayList<>(), new LinkedList<>(), new HashSet<>(), new HashMap<>())
                .forEach(col -> {
                    assertThat(requiredRule.passes(col)).isFalse();
                });
    }

    @Test
    public void shouldPassOnValidString() {
        assertThat(requiredRule.passes("a")).isTrue();
    }

    @Test
    public void shouldPassOnNonEmptyList() {
        assertThat(requiredRule.passes(List.of(1))).isTrue();
    }

    @Test
    public void shouldReturnValidErrorMessage() {
        assertThat(requiredRule.message("test")).isEqualTo("The field \"test\" is required");
    }

}
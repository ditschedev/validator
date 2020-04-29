package com.github.ditschedev.validator.rule;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class RuleInfoTest {

    @Test
    public void shouldNotCreateIfClassIsNotRule() {
        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> new RuleInfo(null));
    }

}
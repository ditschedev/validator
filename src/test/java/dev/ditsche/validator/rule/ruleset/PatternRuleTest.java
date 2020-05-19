package dev.ditsche.validator.rule.ruleset;

import dev.ditsche.validator.dto.TestEntity;
import dev.ditsche.validator.Validator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class PatternRuleTest {

    private final PatternRule patternRule = new PatternRule("\\d*");

    private Validator<TestEntity> validator;

    @BeforeEach
    public void setUp() {
        validator = new Validator<>();
        validator.addField("title", new PatternRule("[a-z ,.'-]*"));
    }

    @Test
    public void shouldFailIfNoStringIsProvided() {
        Stream.of(null, 1, new LinkedList<>(), 2.00f).forEach(value -> {
            assertThat(patternRule.passes(value)).isFalse();
        });
    }

    @Test
    public void shouldFailIfNotMatching() {
        Stream.of("a", "777ab", ".b", "ccc545").forEach(value -> {
            assertThat(patternRule.passes(value)).isFalse();
        });
    }

    @Test
    public void shouldPassIfMatching() {
        Stream.of("3657577", "464736", "3536346").forEach(value -> {
            assertThat(patternRule.passes(value)).isTrue();
        });
    }

    @Test
    public void shouldReturnValidErrorMessage() {
        assertThat(patternRule.message("test")).isEqualTo("The field \"test\" has an invalid format");
    }

    @Test
    public void shouldValidate() {
        assertDoesNotThrow(() -> {
            validator.tryValidate(new TestEntity("test"));
        });
    }

}
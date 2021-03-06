package dev.ditsche.validator.rule.ruleset;

import dev.ditsche.validator.Validator;
import dev.ditsche.validator.dto.TestEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.stream.Stream;

import static dev.ditsche.validator.rule.builder.Rules.string;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class PatternRuleTest {

    private final PatternRule patternRule = new PatternRule("\\d*");

    private Validator validator;

    @BeforeEach
    public void setUp() {
        validator = Validator.fromRules(string("title").pattern("[a-z ,.'-]*"));
    }

    @Test
    public void shouldFailIfNoStringIsProvided() {
        Stream.of(null, 1, new LinkedList<>(), 2.00f).forEach(value -> {
            assertThat(patternRule.test(value).isPassed()).isFalse();
        });
    }

    @Test
    public void shouldFailIfNotMatching() {
        Stream.of("a", "777ab", ".b", "ccc545").forEach(value -> {
            assertThat(patternRule.test(value).isPassed()).isFalse();
        });
    }

    @Test
    public void shouldPassIfMatching() {
        Stream.of("3657577", "464736", "3536346").forEach(value -> {
            assertThat(patternRule.test(value).isPassed()).isTrue();
        });
    }

    @Test
    public void shouldReturnValidErrorMessage() {
        assertThat(patternRule.message("test")).isEqualTo("The field \"test\" has an invalid format");
    }

    @Test
    public void shouldValidate() {
        assertDoesNotThrow(() -> {
            validator.validate(new TestEntity("test", "", "", 3, null, true, null, null));
        });
    }

}
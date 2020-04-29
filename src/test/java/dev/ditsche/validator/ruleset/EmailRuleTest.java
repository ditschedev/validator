package dev.ditsche.validator.ruleset;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class EmailRuleTest {

    private final EmailRule emailRule = new EmailRule();

    @Test
    public void shouldFailIfNoStringIsProvided() {
        Stream.of(null, 1, new LinkedList<>(), 2.00f).forEach(value -> {
            assertThat(emailRule.passes(value)).isFalse();
        });
    }

    @Test
    public void shouldFailIfEmailIsInvalid() {
        Stream.of("test", "test@test.", "test@.", "test@_jsjfs_", "@dot.com").forEach(value -> {
            assertThat(emailRule.passes(value)).isFalse();
        });
    }

    @Test
    public void shouldPassIfEmailIsValid() {
        Stream.of("test@test.de", "test@localhost", "maxmuster+token@dot.com").forEach(value -> {
            assertThat(emailRule.passes(value)).isTrue();
        });
    }

    @Test
    public void shouldReturnValidErrorMessage() {
        assertThat(emailRule.message("test")).isEqualTo("The field \"test\" must be a valid email address");
    }

}
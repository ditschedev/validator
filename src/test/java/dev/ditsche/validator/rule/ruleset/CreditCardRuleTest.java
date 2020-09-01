package dev.ditsche.validator.rule.ruleset;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class CreditCardRuleTest {

    private final CreditCardRule creditCardRule = new CreditCardRule();

    @Test
    public void shouldFailIfNoStringIsProvided() {
        Stream.of(null, 1, new LinkedList<>(), 2.00f).forEach(value -> {
            assertThat(creditCardRule.test(value).isPassed()).isFalse();
        });
    }

    @Test
    public void shouldFailIfStringIsInvalid() {
        Stream.of("", "2222-2222-2222-222a", "4242-2424-4242-04 3").forEach(value -> {
            assertThat(creditCardRule.test(value).isPassed()).isFalse();
        });
    }

    @Test
    public void shouldPassWithValidCreditCardNumber() {
        Stream.of("2222-2222-2222-2222", "4242-2424-4242-4242").forEach(value -> {
            assertThat(creditCardRule.test(value).isPassed()).isTrue();
        });
    }

    @Test
    public void shouldReturnValidErrorMessage() {
        assertThat(creditCardRule.message("test")).isEqualTo("The field \"test\" needs to be a valid credit card number");
    }

}
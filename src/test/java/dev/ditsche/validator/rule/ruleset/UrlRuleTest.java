package dev.ditsche.validator.rule.ruleset;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class UrlRuleTest {

    private final UrlRule urlRule = new UrlRule();

    @Test
    public void shouldFailIfNoStringIsProvided() {
        Stream.of(null, 1, new LinkedList<>(), 2.00f).forEach(value -> {
            assertThat(urlRule.test(value).isPassed()).isFalse();
        });
    }

    @Test
    public void shouldFailIfStringIsInvalid() {
        Stream.of("", "https:/google.com", "google.com").forEach(value -> {
            assertThat(urlRule.test(value).isPassed()).isFalse();
        });
    }

    @Test
    public void shouldPassWithValidUrl() {
        Stream.of("https://ditsche.dev", "http://google.com?s=searchterm", "https://example.co.uk", "ftp://ftp.google.com").forEach(value -> {
            assertThat(urlRule.test(value).isPassed()).isTrue();
        });
    }

    @Test
    public void shouldReturnValidErrorMessage() {
        assertThat(urlRule.message("test")).isEqualTo("The field \"test\" needs to be a valid url");
    }

}
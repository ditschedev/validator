package dev.ditsche.validator.rule.ruleset;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class IpAddressRuleTest {

    private final IpAddressRule ipAddressRule = new IpAddressRule();

    @Test
    public void shouldFailIfNoStringIsProvided() {
        Stream.of(null, 1, new LinkedList<>(), 2.00f).forEach(value -> {
            assertThat(ipAddressRule.passes(value).isPassed()).isFalse();
        });
    }

    @Test
    public void shouldFailIfStringIsInvalid() {
        Stream.of("", "212.35.234.256", "127.0.0").forEach(value -> {
            assertThat(ipAddressRule.passes(value).isPassed()).isFalse();
        });
    }

    @Test
    public void shouldPassWithValidIpAddress() {
        Stream.of("0.0.0.0", "127.0.0.1", "46.234.18.191").forEach(value -> {
            assertThat(ipAddressRule.passes(value).isPassed()).isTrue();
        });
    }

    @Test
    public void shouldReturnValidErrorMessage() {
        assertThat(ipAddressRule.message("test")).isEqualTo("The field \"test\" needs to be a valid ip address");
    }

}
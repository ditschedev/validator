package dev.ditsche.validator.rule.ruleset;

import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.LinkedList;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class BeforeRuleTest {

    private final BeforeRule beforeRule = new BeforeRule();

    @Test
    public void shouldFailIfNoDateIsProvided() {
        Stream.of(null, 1, new LinkedList<>(), 2.00f, false).forEach(value -> assertThat(beforeRule.test(value).isPassed()).isFalse());
    }

    @Test
    public void shouldPassWithValidInput() {
        Stream.of(LocalDate.now(), LocalDateTime.now(), LocalTime.now(), new Date(), new Timestamp(new Date().getTime())).forEach(value -> assertThat(beforeRule.test(value).isPassed()).isFalse());
    }

    @Test
    public void shouldReturnValidErrorMessage() {
        assertThat(beforeRule.message("test")).startsWith("The field \"test\" needs to be before");
    }

    @Test
    public void shouldFailIfGivenDateIsAfter() {
        assertThat(beforeRule.test(LocalDateTime.now().plusDays(1)).isPassed()).isFalse();
    }

    @Test
    public void shouldPassIfGivenDateIsBefore() {
        assertThat(beforeRule.test(LocalDateTime.now().plusDays(-1)).isPassed()).isTrue();
    }

}
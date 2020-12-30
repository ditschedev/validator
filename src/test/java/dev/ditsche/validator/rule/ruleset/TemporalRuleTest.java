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

public class TemporalRuleTest {

    private final TemporalRule temporalRule = new TemporalRule();

    @Test
    public void shouldFailIfNoDateIsProvided() {
        Stream.of(null, 1, new LinkedList<>(), 2.00f, false).forEach(value -> assertThat(temporalRule.test(value).isPassed()).isFalse());
    }

    @Test
    public void shouldPassWithValidInput() {
        Stream.of(LocalDate.now(), LocalDateTime.now(), LocalTime.now(), new Date(), new Timestamp(new Date().getTime())).forEach(value -> assertThat(temporalRule.test(value).isPassed()).isTrue());
    }

    @Test
    public void shouldReturnValidErrorMessage() {
        assertThat(temporalRule.message("test")).isEqualTo("The field \"test\" needs to be a date or timestamp");
    }

}
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

public class AfterRuleTest {

    private final AfterRule afterRule = new AfterRule();

    @Test
    public void shouldFailIfNoDateIsProvided() {
        Stream.of(null, 1, new LinkedList<>(), 2.00f, false).forEach(value -> assertThat(afterRule.test(value).isPassed()).isFalse());
    }

    @Test
    public void shouldPassWithValidInput() {
        Stream.of(LocalDate.now(), LocalDateTime.now(), LocalTime.now(), new Date(), new Timestamp(new Date().getTime())).forEach(value -> assertThat(new AfterRule().test(value).isPassed()).isFalse());
    }

    @Test
    public void shouldReturnValidErrorMessage() {
        assertThat(afterRule.message("test")).startsWith("The field \"test\" needs to be after");
    }

    @Test
    public void shouldFailIfGivenDateIsBefore() {
        assertThat(afterRule.test(LocalDateTime.now().plusDays(-1)).isPassed()).isFalse();
    }

    @Test
    public void shouldPassIfGivenDateIsAfter() {
        assertThat(afterRule.test(LocalDateTime.now().plusDays(1)).isPassed()).isTrue();
    }

}
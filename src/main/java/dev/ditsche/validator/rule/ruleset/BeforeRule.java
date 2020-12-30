package dev.ditsche.validator.rule.ruleset;

import dev.ditsche.validator.rule.Rule;
import dev.ditsche.validator.rule.RuleResult;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class BeforeRule implements Rule {

    private final LocalDateTime relative;

    public BeforeRule() {
        this(LocalDateTime.now());
    }

    public BeforeRule(Date date) {
        this(new Timestamp(date.getTime()));
    }

    public BeforeRule(Timestamp date) {
        this(date.toLocalDateTime());
    }

    public BeforeRule(LocalDate now) {
        this(LocalDateTime.of(now, LocalTime.MIN));
    }

    public BeforeRule(LocalTime now) {
        this(LocalDateTime.of(LocalDate.MIN, now));
    }

    public BeforeRule(LocalDateTime now) {
        relative = now;
    }

    @Override
    public RuleResult test(Object value) {

        if(value instanceof Date) {
            return RuleResult.passes(((Date) value).before(Timestamp.valueOf(relative)));
        }

        if(value instanceof LocalDateTime) {
            LocalDateTime dt = (LocalDateTime) value;
            return RuleResult.passes(dt.isBefore(relative));
        }
        if(value instanceof LocalDate) {
            LocalDate dt = (LocalDate) value;
            return RuleResult.passes(dt.isBefore(relative.toLocalDate()));
        }
        if(value instanceof LocalTime) {
            LocalTime dt = (LocalTime) value;
            return RuleResult.passes(dt.isBefore(relative.toLocalTime()));
        }

        return RuleResult.reject();
    }

    @Override
    public String message(String field) {
        return String.format("The field \"%s\" needs to be before %s", field, relative.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }

    @Override
    public String getType() {
        return RULE_TYPE_PREFIX + "temporal.past";
    }
}

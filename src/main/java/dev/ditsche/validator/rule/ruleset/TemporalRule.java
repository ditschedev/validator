package dev.ditsche.validator.rule.ruleset;

import dev.ditsche.validator.rule.Rule;
import dev.ditsche.validator.rule.RuleResult;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

public class TemporalRule implements Rule {

    @Override
    public RuleResult test(Object value) {

        if(value instanceof LocalDateTime)
            return RuleResult.resolve();

        if(value instanceof LocalDate)
            return RuleResult.resolve();

        if(value instanceof LocalTime)
            return RuleResult.resolve();

        if(value instanceof Date)
            return RuleResult.resolve();

        return RuleResult.reject();
    }

    @Override
    public String message(String field) {
        return String.format("The field \"%s\" needs to be a date or timestamp", field);
    }

    @Override
    public String getType() {
        return RULE_TYPE_PREFIX + "type.temporal";
    }
}

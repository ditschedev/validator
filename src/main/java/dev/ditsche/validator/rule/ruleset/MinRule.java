package dev.ditsche.validator.rule.ruleset;

import dev.ditsche.validator.rule.Rule;
import dev.ditsche.validator.rule.RuleResult;
import lombok.AllArgsConstructor;

/**
 * Defines a validation rule.
 * Checks if the given fields value has a minimum
 * defined length.
 */
@AllArgsConstructor
public class MinRule implements Rule {

    private long min;

    @Override
    public RuleResult passes(Object value) {
        if(value == null)
            return RuleResult.reject();
        if(value instanceof String)
            return RuleResult.passes(((String) value).length() >= min);

        return RuleResult.passes(Long.parseLong(value.toString()) >= min);
    }

    @Override
    public String message(String field) {
        return String.format("The field \"%s\" must be greater than %d", field, min);
    }

}

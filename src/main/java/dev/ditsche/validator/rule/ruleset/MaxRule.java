package dev.ditsche.validator.rule.ruleset;

import dev.ditsche.validator.rule.Rule;
import dev.ditsche.validator.rule.RuleResult;
import lombok.AllArgsConstructor;

/**
 * Defines a validation rule.
 * Checks if the given fields value against a maximum
 * defined length.
 */
@AllArgsConstructor
public class MaxRule implements Rule {

    private long max;

    @Override
    public RuleResult passes(Object value) {
        if(value == null)
            return RuleResult.resolve();
        if(value instanceof String)
            return RuleResult.passes(((String) value).length() <= max);

        return RuleResult.passes(Long.parseLong(value.toString()) <= max);
    }

    @Override
    public String message(String field) {
        return String.format("The field \"%s\" must be shorter than %d", field, max);
    }
}

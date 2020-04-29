package dev.ditsche.validator.ruleset;

import dev.ditsche.validator.rule.Rule;
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
    public boolean passes(Object value) {
        if(value instanceof String)
            return ((String) value).length() <= max;

        return Long.parseLong(value.toString()) <= max;
    }

    @Override
    public String message(String field) {
        return String.format("The field \"%s\" must be shorter than %d", field, max);
    }
}

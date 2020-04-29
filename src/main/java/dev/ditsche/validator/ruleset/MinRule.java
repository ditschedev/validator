package dev.ditsche.validator.ruleset;

import dev.ditsche.validator.rule.Rule;
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
    public boolean passes(Object value) {
        if(value instanceof String)
            return ((String) value).length() >= min;

        return Long.parseLong(value.toString()) >= min;
    }

    @Override
    public String message(String field) {
        return String.format("The field \"%s\" must be greater than %d", field, min);
    }

}

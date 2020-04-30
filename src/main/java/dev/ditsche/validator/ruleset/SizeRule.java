package dev.ditsche.validator.ruleset;

import dev.ditsche.validator.rule.Rule;
import lombok.AllArgsConstructor;

import java.util.Collection;
import java.util.Map;

/*
 * Defines a validation rule.
 * Checks if the given fields values size is
 * in a defined range.
 * Does not support floating-point numbers.
 */
@AllArgsConstructor
public class SizeRule implements Rule {

    /**
     * The minimum value of the field.
     */
    private long min;

    /**
     * The maximum number of the field.
     */
    private long max;

    @Override
    public boolean passes(Object value) {
        if(value == null)
            return false;
        if(value instanceof String)
            return ((String) value).length() >= min && ((String) value).length() <= max;

        if(value instanceof Collection)
            return ((Collection) value).size() >= min && ((Collection) value).size() <= max;

        if(value instanceof Map)
            return ((Map) value).size() >= min && ((Map) value).size() <= max;

        if(!(value instanceof Number))
            return false;

        return ((Number) value).longValue() >= min && ((Number) value).longValue() <= max;
    }

    @Override
    public String message(String field) {
        return String.format("The field \"%s\" needs to be between %s and %s", field, min, max);
    }
}

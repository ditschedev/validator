package com.github.ditschedev.validator.ruleset;

import com.github.ditschedev.validator.rule.Rule;

import java.util.Collection;
import java.util.Map;

/**
 * A rule that checks if the given field is
 * defined in a non-empty way.
 */
public class RequiredRule implements Rule {

    @Override
    public boolean passes(Object value) {
        if(value == null) return false;

        if(value instanceof String)
            return !((String) value).trim().isEmpty();

        if(value instanceof Collection)
            return !((Collection) value).isEmpty();

        if(value instanceof Map)
            return !((Map) value).isEmpty();

        return true;
    }

    @Override
    public String message(String field) {
        return String.format("The field \"%s\" is required", field);
    }
}

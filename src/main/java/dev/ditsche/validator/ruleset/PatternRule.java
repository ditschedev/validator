package dev.ditsche.validator.ruleset;

import dev.ditsche.validator.rule.Rule;
import lombok.AllArgsConstructor;

import java.util.regex.Pattern;

/**
 * Defines a validation rule.
 * Checks if the given fields value against a maximum
 * defined length.
 */
@AllArgsConstructor
public class PatternRule implements Rule {

    private String pattern;

    @Override
    public boolean passes(Object value) {

        if(!(value instanceof String))
            return false;

        return Pattern.matches(pattern, String.valueOf(value));
    }

    @Override
    public String message(String field) {
        return String.format("The field \"%s\" has an invalid format", field);
    }
}

package dev.ditsche.validator.ruleset;

import dev.ditsche.validator.rule.Rule;

import java.util.regex.Pattern;

/**
 * Defines a validation rule.
 * Checks if the given fields value is an alphanumeric
 * string.
 */
public class AlphaNumericRule implements Rule {

    private final String pattern = "[a-zA-Z0-9]+";

    @Override
    public boolean passes(Object value) {
        if(!(value instanceof String))
            return false;
        return Pattern.matches(pattern, String.valueOf(value));
    }

    @Override
    public String message(String field) {
        return String.format("The field \"%s\" must be alpha numeric", field);
    }
}

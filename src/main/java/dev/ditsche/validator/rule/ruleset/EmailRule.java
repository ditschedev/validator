package dev.ditsche.validator.rule.ruleset;

import dev.ditsche.validator.rule.Rule;
import dev.ditsche.validator.rule.RuleResult;

import java.util.regex.Pattern;

/**
 * Defines a validation rule.
 * Checks if the given fields value is a valid email address
 * defined by the pattern of ESAPI.
 *
 * @author Tobias Dittmann
 */
public class EmailRule implements Rule {

    private final String PATTERN = "^[A-Za-z0-9._%'+-]+@[A-Za-z0-9.-]+.[a-zA-Z]{2,4}$";

    @Override
    public RuleResult passes(Object value) {
        if(value == null)
            return RuleResult.reject();

        if(!(value instanceof String))
            return RuleResult.reject();

        return RuleResult.passes(Pattern.matches(PATTERN, String.valueOf(value)));
    }

    @Override
    public String message(String field) {
        return String.format("The field \"%s\" must be a valid email address", field);
    }
}

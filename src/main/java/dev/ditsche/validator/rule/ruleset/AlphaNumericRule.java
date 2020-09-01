package dev.ditsche.validator.rule.ruleset;

import dev.ditsche.validator.rule.Rule;
import dev.ditsche.validator.rule.RuleResult;

import java.util.regex.Pattern;

/**
 * Defines a validation rule.
 * Checks if the given fields value is an alphanumeric
 * string.
 */
public class AlphaNumericRule implements Rule {

    private final String PATTERN = "[a-zA-Z0-9]+";

    @Override
    public RuleResult test(Object value) {
        if(value == null)
            return RuleResult.reject();
        if(!(value instanceof String))
            return RuleResult.reject();
        return RuleResult.passes(Pattern.matches(PATTERN, String.valueOf(value)));
    }

    @Override
    public String message(String field) {
        return String.format("The field \"%s\" must be alpha numeric", field);
    }

    @Override
    public String getType() {
        return RULE_TYPE_PREFIX + "format.alphanum";
    }
}

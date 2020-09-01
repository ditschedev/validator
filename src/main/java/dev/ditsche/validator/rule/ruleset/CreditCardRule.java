package dev.ditsche.validator.rule.ruleset;

import dev.ditsche.validator.rule.Rule;
import dev.ditsche.validator.rule.RuleResult;

import java.util.regex.Pattern;

/**
 * Checks if the provided value is a valid credit card number.
 *
 * @author Tobias Dittmann
 */
public class CreditCardRule implements Rule {

    private final String PATTERN = "^(\\d{4}[- ]?){3}\\d{4}$";

    @Override
    public RuleResult test(Object value) {

        if(!(value instanceof String))
            return RuleResult.reject();

        return RuleResult.passes(Pattern.matches(PATTERN, String.valueOf(value)));
    }

    @Override
    public String message(String field) {
        return String.format("The field \"%s\" needs to be a valid credit card number", field);
    }

    @Override
    public String getType() {
        return RULE_TYPE_PREFIX + "format.creditcard";
    }
}

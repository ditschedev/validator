package dev.ditsche.validator.rule.ruleset;

import dev.ditsche.validator.rule.Rule;
import dev.ditsche.validator.rule.RuleResult;

import java.util.regex.Pattern;

/**
 * Checks for a valid ip address.
 *
 * @author Tobias Dittmann
 */
public class IpAddressRule implements Rule {

    private final String PATTERN = "^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";

    @Override
    public RuleResult passes(Object value) {

        if(!(value instanceof String))
            return RuleResult.reject();

        return RuleResult.passes(Pattern.matches(PATTERN, String.valueOf(value)));
    }

    @Override
    public String message(String field) {
        return String.format("The field \"%s\" needs to be a valid ip address", field);
    }
}

package dev.ditsche.validator.rule.ruleset;

import dev.ditsche.validator.rule.Rule;
import dev.ditsche.validator.rule.RuleResult;

import java.util.regex.Pattern;

/**
 * Checks whether a given String is a valid url based on the validation pattern provided by ESAPI.
 *
 * More information: https://raw.githubusercontent.com/ESAPI/esapi-java-legacy/develop/configuration/esapi/validation.properties
 *
 * @author Tobias Dittmann
 */
public class UrlRule implements Rule {

    private final String PATTERN = "^(ht|f)tp(s?)://[0-9a-zA-Z]([-.\\w]*[0-9a-zA-Z])*(:(0-9)*)*(/?)([a-zA-Z0-9\\-.?,:'/\\\\+=&;%$#_]*)?$";

    @Override
    public RuleResult passes(Object value) {

        if(!(value instanceof String))
            return RuleResult.reject();

        return RuleResult.passes(Pattern.matches(PATTERN, String.valueOf(value)));
    }

    @Override
    public String message(String field) {
        return String.format("The field \"%s\" needs to be a valid url", field);
    }
}

package dev.ditsche.validator.rule.ruleset;

import dev.ditsche.validator.rule.Rule;
import dev.ditsche.validator.rule.RuleResult;

/**
 * @author Tobias Dittmann
 */
public class TrimRule implements Rule {

    @Override
    public RuleResult passes(Object value) {

        if(!(value instanceof String))
            return RuleResult.reject();

        return RuleResult.resolve(((String) value).trim());
    }

    @Override
    public String message(String field) {
        return String.format("The field \"%s\" needs to be a string to be able to trim it", field);
    }

}

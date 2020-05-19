package dev.ditsche.validator.rule.ruleset;

import dev.ditsche.validator.rule.Rule;
import dev.ditsche.validator.rule.RuleResult;

/**
 * @author Tobias Dittmann
 */
public class StringRule implements Rule {

    @Override
    public RuleResult passes(Object value) {
        return RuleResult.passes((value instanceof String));
    }

    @Override
    public String message(String field) {
        return String.format("The field \"%s\" needs to be a string", field);
    }
}

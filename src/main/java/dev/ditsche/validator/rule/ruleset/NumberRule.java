package dev.ditsche.validator.rule.ruleset;

import dev.ditsche.validator.rule.Rule;
import dev.ditsche.validator.rule.RuleResult;

/**
 * @author Tobias Dittmann
 */
public class NumberRule implements Rule {

    @Override
    public RuleResult test(Object value) {
        return RuleResult.passes(value instanceof Number);
    }

    @Override
    public String message(String field) {
        return String.format("The field \"%s\" needs to be a number", field);
    }

    @Override
    public String getType() {
        return RULE_TYPE_PREFIX + "type.number";
    }
}

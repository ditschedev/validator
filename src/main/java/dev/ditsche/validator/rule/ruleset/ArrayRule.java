package dev.ditsche.validator.rule.ruleset;

import dev.ditsche.validator.rule.Rule;
import dev.ditsche.validator.rule.RuleResult;

/**
 * @author Tobias Dittmann
 */
public class ArrayRule implements Rule {

    @Override
    public RuleResult test(Object value) {
        if(value != null && value.getClass().isArray())
            return RuleResult.resolve();
        if(value instanceof Iterable)
            return RuleResult.resolve();
        return RuleResult.reject();
    }

    @Override
    public String message(String field) {
        return String.format("The field \"%s\" needs to be an array", field);
    }

    @Override
    public String getType() {
        return RULE_TYPE_PREFIX + "type.array";
    }
}

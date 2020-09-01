package dev.ditsche.validator.rule.ruleset;

import dev.ditsche.validator.rule.Rule;
import dev.ditsche.validator.rule.RuleResult;
import lombok.AllArgsConstructor;

/**
 * @author Tobias Dittmann
 */
@AllArgsConstructor
public class BooleanRule implements Rule {

    private final boolean val;

    @Override
    public RuleResult test(Object value) {
        if(!(value instanceof Boolean))
            return RuleResult.reject();
        return RuleResult.passes((boolean) value == val);
    }

    @Override
    public String message(String field) {
        return String.format("The field \"%s\" needs to be %s", field, val ? "truthy" : "falsy");
    }

    @Override
    public String getType() {
        return RULE_TYPE_PREFIX + "type.boolean";
    }
}

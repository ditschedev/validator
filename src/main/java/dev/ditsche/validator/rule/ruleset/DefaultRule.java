package dev.ditsche.validator.rule.ruleset;

import dev.ditsche.validator.rule.Rule;
import dev.ditsche.validator.rule.RuleResult;
import lombok.AllArgsConstructor;

/**
 * @author Tobias Dittmann
 */
@AllArgsConstructor
public class DefaultRule implements Rule {

    private final Object defaultValue;

    @Override
    public RuleResult test(Object value) {
        if(value == null)
            return RuleResult.resolve(defaultValue);

        if(!(defaultValue.getClass().isAssignableFrom(value.getClass())))
            return RuleResult.reject();

        if(value instanceof String && ((String) value).trim().isEmpty())
            return RuleResult.resolve(defaultValue);

        if(value instanceof Number && ((Number) value).longValue() == 0)
            return RuleResult.resolve(defaultValue);

        return RuleResult.resolve();
    }

    @Override
    public String message(String field) {
        return String.format("The field \"%s\" cannot obtain a value of class \"%s\"", field, defaultValue.getClass().getName());
    }

    @Override
    public String getType() {
        return RULE_TYPE_PREFIX + "type.unassignable";
    }
}

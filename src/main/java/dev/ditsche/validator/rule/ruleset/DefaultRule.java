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
    public RuleResult passes(Object value) {
        if(!(defaultValue.getClass().isAssignableFrom(value.getClass())))
            return RuleResult.reject();
        return RuleResult.resolve(defaultValue);
    }

    @Override
    public String message(String field) {
        return String.format("The field \"%s\" cannot obtain a value of class \"%s\"", field, defaultValue.getClass().getName());
    }
}

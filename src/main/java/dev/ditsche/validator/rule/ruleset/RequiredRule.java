package dev.ditsche.validator.rule.ruleset;

import dev.ditsche.validator.rule.Rule;
import dev.ditsche.validator.rule.RuleResult;

import java.util.Collection;
import java.util.Map;

/**
 * A rule that checks if the given field is
 * defined in a non-empty way.
 */
public class RequiredRule implements Rule {

    @Override
    public RuleResult passes(Object value) {
        if(value == null) return RuleResult.reject();

        if(value instanceof String)
            return RuleResult.passes(!((String) value).trim().isEmpty());

        if(value instanceof Collection)
            return RuleResult.passes(!((Collection<?>) value).isEmpty());

        if(value instanceof Map)
            return RuleResult.passes(!((Map<?,?>) value).isEmpty());

        return RuleResult.resolve();
    }

    @Override
    public String message(String field) {
        return String.format("The field \"%s\" is required", field);
    }
}

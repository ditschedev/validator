package dev.ditsche.validator.rule.ruleset;

import dev.ditsche.validator.rule.Rule;
import dev.ditsche.validator.rule.RuleResult;

import java.util.Collection;
import java.util.Map;

/**
 * @author Tobias Dittmann
 */
public class LengthRule implements Rule {

    private long length;

    public LengthRule(long length) {
        this.length = length;
    }

    @Override
    public RuleResult passes(Object value) {

        if(value instanceof String)
            return RuleResult.passes(((String) value).length() == length);

        if(value instanceof Number)
            return RuleResult.passes(((Number) value).longValue() == length);

        if(value instanceof Collection)
            return RuleResult.passes(((Collection<?>) value).size() == length);

        if(value instanceof Map)
            return RuleResult.passes(((Map<?,?>) value).size() == length);

        return RuleResult.reject();
    }

    @Override
    public String message(String field) {
        return String.format("The field \"%s\" needs to have a length of %s", field, length);
    }
}

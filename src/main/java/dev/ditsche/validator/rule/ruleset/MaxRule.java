package dev.ditsche.validator.rule.ruleset;

import dev.ditsche.validator.rule.Rule;
import dev.ditsche.validator.rule.RuleResult;
import lombok.AllArgsConstructor;

import java.util.Collection;
import java.util.Map;

/**
 * Defines a validation rule.
 * Checks if the given fields value against a maximum
 * defined length.
 */
@AllArgsConstructor
public class MaxRule implements Rule {

    private long max;

    @Override
    public RuleResult test(Object value) {
        if(value == null)
            return RuleResult.resolve();
        if(value instanceof String)
            return RuleResult.passes(((String) value).length() <= max);

        if(value instanceof Collection)
            return RuleResult.passes(((Collection<?>) value).size() <= max);

        if(value instanceof Map)
            return RuleResult.passes(((Map<?,?>) value).size() <= max);

        if(value instanceof boolean[])
            return RuleResult.passes(((boolean[]) value).length <= max);

        if(value instanceof byte[])
            return RuleResult.passes(((byte[]) value).length <= max);

        if(value instanceof short[])
            return RuleResult.passes(((short[]) value).length <= max);

        if(value instanceof char[])
            return RuleResult.passes(((char[]) value).length <= max);

        if(value instanceof int[])
            return RuleResult.passes(((int[]) value).length <= max);

        if(value instanceof long[])
            return RuleResult.passes(((long[]) value).length <= max);

        if(value instanceof float[])
            return RuleResult.passes(((float[]) value).length <= max);

        if(value instanceof double[])
            return RuleResult.passes(((double[]) value).length <= max);

        if(value instanceof Object[])
            return RuleResult.passes(((Object[]) value).length <= max);

        return RuleResult.passes(Long.parseLong(value.toString()) <= max);
    }

    @Override
    public String message(String field) {
        return String.format("The field \"%s\" must be shorter than %d", field, max);
    }

    @Override
    public String getType() {
        return RULE_TYPE_PREFIX + "size.max";
    }
}

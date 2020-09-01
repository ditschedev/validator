package dev.ditsche.validator.rule.ruleset;

import dev.ditsche.validator.rule.Rule;
import dev.ditsche.validator.rule.RuleResult;
import lombok.AllArgsConstructor;

import java.util.Collection;
import java.util.Map;

/**
 * Defines a validation rule.
 * Checks if the given fields value has a minimum
 * defined length.
 */
@AllArgsConstructor
public class MinRule implements Rule {

    private long min;

    @Override
    public RuleResult test(Object value) {
        if(value == null)
            return RuleResult.reject();
        if(value instanceof String)
            return RuleResult.passes(((String) value).length() >= min);

        if(value instanceof Collection)
            return RuleResult.passes(((Collection<?>) value).size() >= min);

        if(value instanceof Map)
            return RuleResult.passes(((Map<?,?>) value).size() >= min);

        if(value instanceof boolean[])
            return RuleResult.passes(((boolean[]) value).length >= min);

        if(value instanceof byte[])
            return RuleResult.passes(((byte[]) value).length >= min);

        if(value instanceof short[])
            return RuleResult.passes(((short[]) value).length >= min);

        if(value instanceof char[])
            return RuleResult.passes(((char[]) value).length >= min);

        if(value instanceof int[])
            return RuleResult.passes(((int[]) value).length >= min);

        if(value instanceof long[])
            return RuleResult.passes(((long[]) value).length >= min);

        if(value instanceof float[])
            return RuleResult.passes(((float[]) value).length >= min);

        if(value instanceof double[])
            return RuleResult.passes(((double[]) value).length >= min);

        if(value instanceof Object[])
            return RuleResult.passes(((Object[]) value).length >= min);

        return RuleResult.passes(Long.parseLong(value.toString()) >= min);
    }

    @Override
    public String message(String field) {
        return String.format("The field \"%s\" must be greater than %d", field, min);
    }

    @Override
    public String getType() {
        return RULE_TYPE_PREFIX + "size.min";
    }

}

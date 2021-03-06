package dev.ditsche.validator.rule.ruleset;

import dev.ditsche.validator.rule.Rule;
import dev.ditsche.validator.rule.RuleResult;
import lombok.AllArgsConstructor;

import java.util.Collection;
import java.util.Map;

/*
 * Defines a validation rule.
 * Checks if the given fields values size is
 * in a defined range.
 * Does not support floating-point numbers.
 */
@AllArgsConstructor
public class SizeRule implements Rule {

    /**
     * The minimum value of the field.
     */
    private long min;

    /**
     * The maximum number of the field.
     */
    private long max;

    @Override
    public RuleResult test(Object value) {
        if(value == null)
            return RuleResult.reject();
        if(value instanceof String)
            return RuleResult.passes(((String) value).length() >= min && ((String) value).length() <= max);

        if(value instanceof Collection)
            return RuleResult.passes(((Collection<?>) value).size() >= min && ((Collection<?>) value).size() <= max);

        if(value instanceof Map)
            return RuleResult.passes(((Map<?,?>) value).size() >= min && ((Map<?,?>) value).size() <= max);

        if(value instanceof boolean[])
            return RuleResult.passes(((boolean[]) value).length >= min && ((boolean[]) value).length <= max);

        if(value instanceof byte[])
            return RuleResult.passes(((byte[]) value).length >= min && ((byte[]) value).length <= max);

        if(value instanceof short[])
            return RuleResult.passes(((short[]) value).length >= min && ((short[]) value).length <= max);

        if(value instanceof char[])
            return RuleResult.passes(((char[]) value).length >= min && ((char[]) value).length <= max);

        if(value instanceof int[])
            return RuleResult.passes(((int[]) value).length >= min && ((int[]) value).length <= max);

        if(value instanceof long[])
            return RuleResult.passes(((long[]) value).length >= min && ((long[]) value).length <= max);

        if(value instanceof float[])
            return RuleResult.passes(((float[]) value).length >= min && ((float[]) value).length <= max);

        if(value instanceof double[])
            return RuleResult.passes(((double[]) value).length >= min && ((double[]) value).length <= max);

        if(value instanceof Object[])
            return RuleResult.passes(((Object[]) value).length >= min && ((Object[]) value).length <= max);

        if(!(value instanceof Number))
            return RuleResult.reject();

        return RuleResult.passes(((Number) value).longValue() >= min && ((Number) value).longValue() <= max);
    }

    @Override
    public String message(String field) {
        return String.format("The field \"%s\" needs to be between %s and %s", field, min, max);
    }

    @Override
    public String getType() {
        return RULE_TYPE_PREFIX + "size.size";
    }
}

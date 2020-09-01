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
    public RuleResult test(Object value) {

        if(value == null)
            return RuleResult.reject();

        if(value instanceof String)
            return RuleResult.passes(((String) value).length() == length);

        if(value instanceof Number)
            return RuleResult.passes(((Number) value).longValue() == length);

        if(value instanceof Collection)
            return RuleResult.passes(((Collection<?>) value).size() == length);

        if(value instanceof Map)
            return RuleResult.passes(((Map<?,?>) value).size() == length);

        if(value instanceof boolean[])
            return RuleResult.passes(((boolean[]) value).length == length);

        if(value instanceof byte[])
            return RuleResult.passes(((byte[]) value).length == length);

        if(value instanceof short[])
            return RuleResult.passes(((short[]) value).length == length);

        if(value instanceof char[])
            return RuleResult.passes(((char[]) value).length == length);

        if(value instanceof int[])
            return RuleResult.passes(((int[]) value).length == length);

        if(value instanceof long[])
            return RuleResult.passes(((long[]) value).length == length);

        if(value instanceof float[])
            return RuleResult.passes(((float[]) value).length == length);

        if(value instanceof double[])
            return RuleResult.passes(((double[]) value).length == length);

        if(value instanceof Object[])
            return RuleResult.passes(((Object[]) value).length == length);

        return RuleResult.reject();
    }

    @Override
    public String message(String field) {
        return String.format("The field \"%s\" needs to have a length of %s", field, length);
    }

    @Override
    public String getType() {
        return RULE_TYPE_PREFIX + "size.length";
    }
}

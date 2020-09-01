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
    public RuleResult test(Object value) {
        if(value == null) return RuleResult.reject();

        if(value instanceof String)
            return RuleResult.passes(!((String) value).trim().isEmpty());

        if(value instanceof Collection)
            return RuleResult.passes(!((Collection<?>) value).isEmpty());

        if(value instanceof Map)
            return RuleResult.passes(!((Map<?,?>) value).isEmpty());

        if(value instanceof boolean[])
            return RuleResult.passes(((boolean[]) value).length != 0);

        if(value instanceof byte[])
            return RuleResult.passes(((byte[]) value).length != 0);

        if(value instanceof short[])
            return RuleResult.passes(((short[]) value).length != 0);

        if(value instanceof char[])
            return RuleResult.passes(((char[]) value).length != 0);

        if(value instanceof int[])
            return RuleResult.passes(((int[]) value).length != 0);

        if(value instanceof long[])
            return RuleResult.passes(((long[]) value).length != 0);

        if(value instanceof float[])
            return RuleResult.passes(((float[]) value).length != 0);

        if(value instanceof double[])
            return RuleResult.passes(((double[]) value).length != 0);

        if(value instanceof Object[])
            return RuleResult.passes(((Object[]) value).length != 0);

        return RuleResult.resolve();
    }

    @Override
    public String message(String field) {
        return String.format("The field \"%s\" is required", field);
    }

    @Override
    public String getType() {
        return RULE_TYPE_PREFIX + "format.required";
    }
}

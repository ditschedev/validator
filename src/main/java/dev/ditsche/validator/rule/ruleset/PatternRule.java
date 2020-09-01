package dev.ditsche.validator.rule.ruleset;

import dev.ditsche.validator.rule.Rule;
import dev.ditsche.validator.rule.RuleResult;
import lombok.AllArgsConstructor;

import java.util.regex.Pattern;

/**
 * Defines a validation rule.
 * Checks if the given fields value against a maximum
 * defined length.
 */
@AllArgsConstructor
public class PatternRule implements Rule {

    private String pattern;

    @Override
    public RuleResult test(Object value) {
        if(value == null)
            return RuleResult.reject();

        if(!(value instanceof String))
            return RuleResult.reject();

        return RuleResult.passes(Pattern.matches(pattern, String.valueOf(value)));
    }

    @Override
    public String message(String field) {
        return String.format("The field \"%s\" has an invalid format", field);
    }

    @Override
    public String getType() {
        return RULE_TYPE_PREFIX + "format.pattern";
    }
}

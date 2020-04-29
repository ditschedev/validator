package dev.ditsche.validator.rule;

import lombok.Getter;

/**
 * Describes a rule for parsing its string representation.
 */
@Getter
public class RuleInfo {

    /**
     * The rule class.
     */
    private Class<? extends Rule> rule;

    /**
     * If the rule need params this array
     * holds the types for conversion.
     */
    private Class<?>[] paramTypes;

    /**
     * Initiates a new instance.
     *
     * @param rule The rule class.
     * @param paramTypes The parameter types if any.
     */
    public RuleInfo(Class<? extends Rule> rule, Class<?> ...paramTypes) {
        if(rule == null)
            throw new IllegalArgumentException("Rule class cannot be null");
        this.rule = rule;
        this.paramTypes = paramTypes;
    }

}

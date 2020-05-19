package dev.ditsche.validator.rule.builder;

/**
 * @author Tobias Dittmann
 */
public final class RuleBuilder {

    public static StringRuleBuilder string(String field) {
        return new StringRuleBuilder(field);
    }

    public static NumberRuleBuilder number(String field) {
        return new NumberRuleBuilder(field);
    }

    public static ObjectRuleBuilder object(String field) {
        return new ObjectRuleBuilder(field);
    }

    public static ArrayRuleBuilder object(String field) {
        return new ArrayRuleBuilder(field);
    }

}

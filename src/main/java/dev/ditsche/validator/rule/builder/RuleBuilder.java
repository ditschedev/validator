package dev.ditsche.validator.rule.builder;

import dev.ditsche.validator.rule.Rule;
import dev.ditsche.validator.ruleset.StringRule;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Tobias Dittmann
 */
public final class RuleBuilder {

    private String field;

    private List<Rule> rules;

    private RuleBuilder(String field, Rule rule) {
        this.field = field;
        this.rules = new LinkedList<>();
        this.rules.add(rule);
    }

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

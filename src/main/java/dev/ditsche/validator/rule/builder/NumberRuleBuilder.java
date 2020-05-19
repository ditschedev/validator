package dev.ditsche.validator.rule.builder;

import dev.ditsche.validator.rule.Rule;
import dev.ditsche.validator.rule.Validatable;
import dev.ditsche.validator.rule.ValidationField;
import dev.ditsche.validator.rule.ruleset.*;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Tobias Dittmann
 */
public class NumberRuleBuilder implements Builder {

    private final String field;

    private List<Rule> rules;

    NumberRuleBuilder(String field) {
        this.field = field;
        this.rules = new LinkedList<>();
        this.rules.add(new NumberRule());
    }

    public NumberRuleBuilder min(long min) {
        rules.add(new MinRule(min));
        return this;
    }

    public NumberRuleBuilder max(long max) {
        rules.add(new MaxRule(max));
        return this;
    }

    public NumberRuleBuilder size(long min, long max) {
        rules.add(new SizeRule(min, max));
        return this;
    }

    public NumberRuleBuilder length(int length) {
        rules.add(new LengthRule(length));
        return this;
    }

    public NumberRuleBuilder defaultValue(Number number) {
        rules.add(new DefaultRule(number));
        return this;
    }

    public NumberRuleBuilder custom(Rule rule) {
        rules.add(rule);
        return this;
    }

    @Override
    public Validatable build() {
        return new ValidationField(field, rules);
    }

}

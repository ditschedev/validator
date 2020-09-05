package dev.ditsche.validator.rule.builder;

import dev.ditsche.validator.rule.Rule;
import dev.ditsche.validator.rule.ruleset.*;
import dev.ditsche.validator.validation.Validatable;
import dev.ditsche.validator.validation.ValidationField;

import java.util.LinkedList;

/**
 * @author Tobias Dittmann
 */
public class NumberRuleBuilder extends RuleBuilder {

    private final String field;

    NumberRuleBuilder(String field) {
        this.field = field;
        this.rules = new LinkedList<>();
        this.rules.add(new NumberRule());
    }

    public NumberRuleBuilder min(long min) {
        this.rules.add(new MinRule(min));
        return this;
    }

    public NumberRuleBuilder max(long max) {
        this.rules.add(new MaxRule(max));
        return this;
    }

    public NumberRuleBuilder size(long min, long max) {
        this.rules.add(new SizeRule(min, max));
        return this;
    }

    public NumberRuleBuilder length(int length) {
        this.rules.add(new LengthRule(length));
        return this;
    }

    public NumberRuleBuilder defaultValue(Number number) {
        this.rules.add(new DefaultRule(number));
        return this;
    }

    @Override
    public RuleBuilder custom(Rule rule) {
        this.rules.add(rule);
        return this;
    }

    @Override
    public Validatable build() {
        return new ValidationField(this.field, this.rules);
    }

}

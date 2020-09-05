package dev.ditsche.validator.rule.builder;

import dev.ditsche.validator.rule.Rule;
import dev.ditsche.validator.rule.ruleset.*;
import dev.ditsche.validator.validation.Validatable;
import dev.ditsche.validator.validation.ValidationArray;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Tobias Dittmann
 */
public class ArrayRuleBuilder extends RuleBuilder {

    private String field;

    private List<Rule> childRules;

    private List<Validatable> childValidatable;

    private boolean optional;

    public ArrayRuleBuilder(String field) {
        this.field = field;
        this.rules = new LinkedList<>();
        this.rules.add(new ArrayRule());
    }

    public ArrayRuleBuilder required() {
        this.rules.add(new RequiredRule());
        return this;
    }

    public ArrayRuleBuilder length(int length) {
        this.rules.add(new LengthRule(length));
        return this;
    }

    public ArrayRuleBuilder min(int min) {
        this.rules.add(new MinRule(min));
        return this;
    }

    public ArrayRuleBuilder max(int max) {
        this.rules.add(new MaxRule(max));
        return this;
    }

    public ArrayRuleBuilder size(int min, int max) {
        rules.add(new SizeRule(min, max));
        return this;
    }

    public ArrayElementRuleBuilder elements() {
        return new ArrayElementRuleBuilder(this.field, this.rules, this.optional);
    }

    public ArrayRuleBuilder objects(Builder ...builders) {
        this.childRules = null;
        this.childValidatable = new LinkedList<>();
        for(Builder builder : builders) {
            this.childValidatable.add(builder.build());
        }
        return this;
    }

    public ArrayRuleBuilder optional() {
        this.optional = true;
        return this;
    }

    @Override
    public RuleBuilder custom(Rule rule) {
        this.rules.add(rule);
        return this;
    }

    @Override
    public Validatable build() {
        return new ValidationArray(this.field, this.rules, this.childRules, this.childValidatable, this.optional);
    }
}

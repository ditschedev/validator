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
public class ArrayElementRuleBuilder implements Builder {

    private String field;

    private List<Rule> rules;

    private List<Rule> children;

    private boolean optional;

    protected ArrayElementRuleBuilder(String field, List<Rule> rules, boolean optional) {
        this.field = field;
        this.rules = rules;
        this.optional = optional;
        children = new LinkedList<>();
    }

    public ArrayElementRuleBuilder required() {
        children.add(new RequiredRule());
        return this;
    }

    public ArrayElementRuleBuilder string() {
        children.add(new StringRule());
        return this;
    }

    public ArrayElementRuleBuilder number() {
        children.add(new NumberRule());
        return this;
    }

    public ArrayElementRuleBuilder bool(boolean value) {
        children.add(new BooleanRule(value));
        return this;
    }

    public ArrayElementRuleBuilder min(int min) {
        children.add(new MinRule(min));
        return this;
    }

    public ArrayElementRuleBuilder max(int max) {
        children.add(new MaxRule(max));
        return this;
    }

    public ArrayElementRuleBuilder length(int length) {
        children.add(new LengthRule(length));
        return this;
    }

    public ArrayElementRuleBuilder size(int min, int max) {
        children.add(new SizeRule(min, max));
        return this;
    }

    @Override
    public Validatable build() {
        return new ValidationArray(field, rules, children, null, optional);
    }
}

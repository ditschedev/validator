package dev.ditsche.validator.rule.builder;

import dev.ditsche.validator.rule.Rule;
import dev.ditsche.validator.rule.ruleset.*;
import dev.ditsche.validator.validation.Validatable;
import dev.ditsche.validator.validation.ValidationArray;
import dev.ditsche.validator.validation.ValidationObject;
import lombok.Getter;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Tobias Dittmann
 */
public class ArrayRuleBuilder implements Builder {

    private String field;

    private List<Rule> rules;

    private List<Rule> childRules;

    private List<Validatable> childValidateables;

    private boolean optional;

    public ArrayRuleBuilder(String field) {
        this.field = field;
        rules = new LinkedList<>();
        rules.add(new ArrayRule());
    }

    public ArrayRuleBuilder required() {
        rules.add(new RequiredRule());
        return this;
    }

    public ArrayRuleBuilder length(int length) {
        rules.add(new LengthRule(length));
        return this;
    }

    public ArrayRuleBuilder min(int min) {
        rules.add(new MinRule(min));
        return this;
    }

    public ArrayRuleBuilder max(int max) {
        rules.add(new MaxRule(max));
        return this;
    }

    public ArrayRuleBuilder size(int min, int max) {
        rules.add(new SizeRule(min, max));
        return this;
    }

    public ArrayElementRuleBuilder elements() {
        return new ArrayElementRuleBuilder(field, this.rules, this.optional);
    }

    public ArrayRuleBuilder objects(Builder ...builders) {
        childRules = null;
        childValidateables = new LinkedList<>();
        for(Builder builder : builders) {
            this.childValidateables.add(builder.build());
        }
        return this;
    }

    public ArrayRuleBuilder optional() {
        this.optional = true;
        return this;
    }

    @Override
    public Validatable build() {
        return new ValidationArray(field, rules, childRules, childValidateables, optional);
    }
}

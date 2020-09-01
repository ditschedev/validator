package dev.ditsche.validator.rule.builder;

import dev.ditsche.validator.rule.Rule;
import dev.ditsche.validator.rule.ruleset.BooleanRule;
import dev.ditsche.validator.validation.Validatable;
import dev.ditsche.validator.validation.ValidationField;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Tobias Dittmann
 */
public class BooleanRuleBuilder implements Builder {

    private String field;

    private List<Rule> rules;

    public BooleanRuleBuilder(String field) {
        this.field = field;
        this.rules = new LinkedList<>();
    }

    public BooleanRuleBuilder isTrue() {
        rules.add(new BooleanRule(true));
        return this;
    }

    public BooleanRuleBuilder isFalse() {
        rules.add(new BooleanRule(false));
        return this;
    }

    @Override
    public Validatable build() {
        return new ValidationField(field, rules);
    }
}

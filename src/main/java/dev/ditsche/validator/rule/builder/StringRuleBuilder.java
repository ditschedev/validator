package dev.ditsche.validator.rule.builder;

import dev.ditsche.validator.rule.Rule;
import dev.ditsche.validator.rule.ValidationField;
import dev.ditsche.validator.ruleset.*;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Tobias Dittmann
 */
public class StringRuleBuilder implements Builder {

    private String field;

    private List<Rule> rules;

    StringRuleBuilder(String field) {
        this.field = field;
        this.rules = new LinkedList<>();
        this.rules.add(new StringRule());
    }

    public StringRuleBuilder length(int length) {
        rules.add(new LengthRule(length));
        return this;
    }

    public StringRuleBuilder between(int min, int max) {
        rules.add(new BetweenRule(min, max));
        return this;
    }

    public StringRuleBuilder min(int min) {
        rules.add(new MinRule(min));
        return this;
    }

    public StringRuleBuilder max(int max) {
        rules.add(new MaxRule(max));
        return this;
    }

    public StringRuleBuilder email() {
        rules.add(new EmailRule());
        return this;
    }

    public StringRuleBuilder url() {
        rules.add(new UrlRule());
        return this;
    }

    public StringRuleBuilder pattern(String pattern) {
        rules.add(new PatternRule(pattern));
        return this;
    }

    public StringRuleBuilder required() {
        rules.add(new RequiredRule());
        return this;
    }

    public StringRuleBuilder alphanum() {
        rules.add(new AlphaNumericRule());
        return this;
    }

    public ValidationField build() {
        return new ValidationField(field, (Rule[]) rules.toArray());
    }

}
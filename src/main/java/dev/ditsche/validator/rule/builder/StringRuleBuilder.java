package dev.ditsche.validator.rule.builder;

import dev.ditsche.validator.rule.Rule;
import dev.ditsche.validator.rule.ruleset.*;
import dev.ditsche.validator.validation.ValidationField;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Tobias Dittmann
 */
public class StringRuleBuilder implements Builder {

    private final String field;

    private boolean optional = false;

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
        rules.add(new SizeRule(min, max));
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

    public StringRuleBuilder ip() {
        rules.add(new IpAddressRule());
        return this;
    }

    public StringRuleBuilder creditCard() {
        rules.add(new CreditCardRule());
        return this;
    }

    public StringRuleBuilder defaultValue(String value) {
        rules.add(new DefaultRule(value));
        return this;
    }

    public StringRuleBuilder trim() {
        rules.add(new TrimRule());
        return this;
    }

    public StringRuleBuilder optional() {
        this.optional = true;
        return this;
    }

    public StringRuleBuilder custom(Rule rule) {
        rules.add(rule);
        return this;
    }

    public ValidationField build() {
        return new ValidationField(field, rules, optional);
    }

}

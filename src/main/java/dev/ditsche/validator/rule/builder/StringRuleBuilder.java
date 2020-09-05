package dev.ditsche.validator.rule.builder;

import dev.ditsche.validator.rule.Rule;
import dev.ditsche.validator.rule.ruleset.*;
import dev.ditsche.validator.validation.ValidationField;

import java.util.LinkedList;

/**
 * @author Tobias Dittmann
 */
public class StringRuleBuilder extends RuleBuilder {

    private final String field;

    private boolean optional = false;

    StringRuleBuilder(String field) {
        this.field = field;
        this.rules = new LinkedList<>();
        this.rules.add(new StringRule());
    }

    public StringRuleBuilder length(int length) {
        this.rules.add(new LengthRule(length));
        return this;
    }

    public StringRuleBuilder between(int min, int max) {
        this.rules.add(new SizeRule(min, max));
        return this;
    }

    public StringRuleBuilder min(int min) {
        this.rules.add(new MinRule(min));
        return this;
    }

    public StringRuleBuilder max(int max) {
        this.rules.add(new MaxRule(max));
        return this;
    }

    public StringRuleBuilder email() {
        this.rules.add(new EmailRule());
        return this;
    }

    public StringRuleBuilder url() {
        this.rules.add(new UrlRule());
        return this;
    }

    public StringRuleBuilder pattern(String pattern) {
        this.rules.add(new PatternRule(pattern));
        return this;
    }

    public StringRuleBuilder required() {
        this.rules.add(new RequiredRule());
        return this;
    }

    public StringRuleBuilder alphanum() {
        this.rules.add(new AlphaNumericRule());
        return this;
    }

    public StringRuleBuilder ip() {
        this.rules.add(new IpAddressRule());
        return this;
    }

    public StringRuleBuilder creditCard() {
        this.rules.add(new CreditCardRule());
        return this;
    }

    public StringRuleBuilder defaultValue(String value) {
        this.rules.add(new DefaultRule(value));
        return this;
    }

    public StringRuleBuilder trim() {
        this.rules.add(new TrimRule());
        return this;
    }

    public StringRuleBuilder optional() {
        this.optional = true;
        return this;
    }

    @Override
    public RuleBuilder custom(Rule rule) {
        this.rules.add(rule);
        return this;
    }

    @Override
    public ValidationField build() {
        return new ValidationField(this.field, this.rules, this.optional);
    }

}

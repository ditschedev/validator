package dev.ditsche.validator.rule.builder;

import dev.ditsche.validator.rule.Rule;
import dev.ditsche.validator.rule.ruleset.AfterRule;
import dev.ditsche.validator.rule.ruleset.BeforeRule;
import dev.ditsche.validator.rule.ruleset.RequiredRule;
import dev.ditsche.validator.rule.ruleset.TemporalRule;
import dev.ditsche.validator.validation.Validatable;
import dev.ditsche.validator.validation.ValidationField;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.LinkedList;

public class TemporalRuleBuilder extends RuleBuilder {

    private final String field;

    private boolean optional = false;

    TemporalRuleBuilder(String field) {
        this.field = field;
        this.rules = new LinkedList<>();
        this.rules.add(new TemporalRule());
    }

    public TemporalRuleBuilder optional() {
        this.optional = true;
        return this;
    }

    public TemporalRuleBuilder future() {
        this.rules.add(new AfterRule());
        return this;
    }

    public TemporalRuleBuilder past() {
        this.rules.add(new BeforeRule());
        return this;
    }

    public TemporalRuleBuilder after(LocalDateTime now) {
        this.rules.add(new AfterRule(now));
        return this;
    }

    public TemporalRuleBuilder after(LocalDate now) {
        this.rules.add(new AfterRule(now));
        return this;
    }

    public TemporalRuleBuilder after(LocalTime now) {
        this.rules.add(new AfterRule(now));
        return this;
    }

    public TemporalRuleBuilder after(Timestamp now) {
        this.rules.add(new AfterRule(now));
        return this;
    }

    public TemporalRuleBuilder after(Date now) {
        this.rules.add(new AfterRule(now));
        return this;
    }

    public TemporalRuleBuilder before(LocalDateTime now) {
        this.rules.add(new BeforeRule(now));
        return this;
    }

    public TemporalRuleBuilder before(LocalDate now) {
        this.rules.add(new BeforeRule(now));
        return this;
    }

    public TemporalRuleBuilder before(LocalTime now) {
        this.rules.add(new BeforeRule(now));
        return this;
    }

    public TemporalRuleBuilder before(Timestamp now) {
        this.rules.add(new BeforeRule(now));
        return this;
    }

    public TemporalRuleBuilder before(Date now) {
        this.rules.add(new BeforeRule(now));
        return this;
    }

    public TemporalRuleBuilder required() {
        this.rules.add(new RequiredRule());
        return this;
    }

    @Override
    public TemporalRuleBuilder custom(Rule rule) {
        this.rules.add(rule);
        return this;
    }

    @Override
    public Validatable build() {
        return new ValidationField(this.field, this.rules, this.optional);
    }
}

package dev.ditsche.validator.validation;

import dev.ditsche.validator.error.ErrorBag;
import dev.ditsche.validator.error.ValidationException;
import dev.ditsche.validator.rule.Rule;
import dev.ditsche.validator.rule.RuleResult;
import dev.ditsche.validator.rule.ruleset.RequiredRule;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;

/**
 * Describes a validatable field.
 * Holds information about the fields name and the assigned rules.
 */
public class ValidationField implements Validatable {

    /**
     * The fields name.
     * This variable is readonly.
     */
    @Getter
    private String field;

    @Getter
    @Setter
    private boolean optional;

    /**
     * Stores the rules assigned to the field.
     */
    @Getter
    private List<Rule> rules;

    /**
     * Initiates a validatable field without any rules.
     *
     * @param field The fields name.
     */
    public ValidationField(String field) {
        this(field, new LinkedList<>(), false);
    }

    /**
     * Initiates a validatable field.
     *
     * @param field The fields name.
     * @param rules The rules that will be applied.
     */
    public ValidationField(String field, List<Rule> rules) {
        this(field, rules, false);
    }

    public ValidationField(String field, List<Rule> rules, boolean optional) {
        if(field == null || field.trim().isEmpty())
            throw new IllegalArgumentException("Validation field requires a valid field name");
        if(rules == null)
            throw new IllegalArgumentException("Validation rules cannot be null");
        this.field = field;
        this.rules = rules;
        this.optional = optional;
    }

    /**
     * Adds a rule to the field.
     * Replaces the rule if existing.
     *
     * @param rule The applied rule.
     */
    public void addRule(Rule rule) {
        this.rules.remove(rule);
        this.rules.add(rule);
    }

    @Override
    public ValidationResult validate(String parent, Object object, boolean abortEarly) {
        ErrorBag errorBag = new ErrorBag();
        boolean changed = false;
        if(optional && !(new RequiredRule().passes(object).isPassed()))
            return new ValidationResult(errorBag, object, false);
        for(Rule rule : rules) {
            RuleResult ruleResult = rule.passes(object);
            if(!ruleResult.isPassed()) {
                errorBag.add(parent + field, rule.message(field));
                if(abortEarly)
                    throw new ValidationException(errorBag);
            } else if(ruleResult.isChanged()) {
                changed = true;
                object = ruleResult.getValue();
            }
        }
        return new ValidationResult(errorBag, object, changed);
    }
}

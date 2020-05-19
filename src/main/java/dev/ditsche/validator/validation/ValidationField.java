package dev.ditsche.validator.validation;

import dev.ditsche.validator.error.ErrorBag;
import dev.ditsche.validator.error.ValidationException;
import dev.ditsche.validator.rule.Rule;
import dev.ditsche.validator.rule.RuleResult;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        this(field, new Rule[0]);
    }

    /**
     * Initiates a validatable field.
     *
     * @param field The fields name.
     * @param rules The rules that will be applied.
     */
    public ValidationField(String field, Rule ...rules) {
        if(field == null || field.trim().isEmpty())
            throw new IllegalArgumentException("Validation field requires a valid field name");
        if(rules == null)
            throw new IllegalArgumentException("Validation rules cannot be null");
        this.field = field;
        this.rules = Stream.of(rules).collect(Collectors.toList());
    }

    public ValidationField(String field, List<Rule> rules) {
        if(field == null || field.trim().isEmpty())
            throw new IllegalArgumentException("Validation field requires a valid field name");
        if(rules == null)
            throw new IllegalArgumentException("Validation rules cannot be null");
        this.field = field;
        this.rules = rules;
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
    public ValidationResult validate(Object object, boolean abortEarly) {
        ErrorBag errorBag = new ErrorBag();
        boolean changed = false;
        for(Rule rule : rules) {
            RuleResult ruleResult = rule.passes(object);
            if(!ruleResult.isPassed()) {
                errorBag.add(field, rule.message(field));
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

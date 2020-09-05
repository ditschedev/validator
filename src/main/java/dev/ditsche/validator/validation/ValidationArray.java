package dev.ditsche.validator.validation;

import dev.ditsche.validator.error.ErrorBag;
import dev.ditsche.validator.error.ValidationException;
import dev.ditsche.validator.rule.Rule;
import dev.ditsche.validator.rule.RuleResult;
import dev.ditsche.validator.rule.ruleset.RequiredRule;
import lombok.Getter;

import java.util.List;

/**
 * @author Tobias Dittmann
 */
public class ValidationArray implements Validatable {

    @Getter
    private String field;

    private boolean optional;

    @Getter
    private List<Rule> rules;

    @Getter
    private List<Rule> childRules;

    @Getter
    private List<Validatable> validatables;

    public ValidationArray(String field, List<Rule> rules, List<Rule> childRules, List<Validatable> validatables) {
        this(field, rules, childRules, validatables, false);
    }

    public ValidationArray(String field, List<Rule> rules, List<Rule> childRules, List<Validatable> validatables, boolean optional) {
        this.field = field;
        this.rules = rules;
        this.childRules = childRules;
        this.validatables = validatables;
        this.optional = optional;
    }

    @Override
    public ValidationResult validate(String parent, Object object, boolean abortEarly) {
        ErrorBag errorBag = new ErrorBag();
        boolean changed = false;
        if(optional && !(new RequiredRule().test(object).isPassed()))
            return new ValidationResult(errorBag, object, false);

        for(Rule rule : rules) {
            RuleResult ruleResult = rule.test(object);
            if(!ruleResult.isPassed()) {
                errorBag.add(parent + field, rule.getType(), rule.message(field));
                if(abortEarly)
                    throw new ValidationException(errorBag);
            } else if(ruleResult.isChanged()) {
                changed = true;
                object = ruleResult.getValue();
            }
        }
        List<Object> test = List.of(object);
        for(Object element : test) {
            if(childRules != null) {
                for(Rule rule : childRules) {
                    RuleResult ruleResult = rule.test(element);
                    if(!ruleResult.isPassed()) {
                        errorBag.add(parent + field, rule.getType(), rule.message(field));
                        if(abortEarly)
                            throw new ValidationException(errorBag);
                    } else if(ruleResult.isChanged()) {
                        changed = true;
                        element = ruleResult.getValue();
                    }
                }
            } else if(validatables != null) {
                for(Validatable validatable : validatables) {
                    ValidationResult validationResult = validatable.validate(parent + field, element, abortEarly);
                    errorBag.merge(validationResult.getErrorBag());
                }
            }
        }

        return new ValidationResult(errorBag, object, changed);
    }
}

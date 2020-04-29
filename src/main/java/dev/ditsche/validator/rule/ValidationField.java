package dev.ditsche.validator.rule;

import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Describes a validatable field.
 * Holds information about the fields name and the assigned rules.
 */
public class ValidationField {

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

    /**
     * Adds a rule to the field.
     *
     * @param rule The applied rule.
     */
    public void addRule(Rule rule) {
        if(!rules.contains(rule))
            this.rules.add(rule);
    }

}

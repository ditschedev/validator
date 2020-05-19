package dev.ditsche.validator.rule.builder;

import dev.ditsche.validator.validation.Validatable;
import dev.ditsche.validator.validation.ValidationObject;

import java.util.List;

/**
 * @author Tobias Dittmann
 */
public class ObjectRuleBuilder implements Builder {

    private final String field;

    private List<Validatable> children;

    ObjectRuleBuilder(String field) {
        this.field = field;
    }

    public ObjectRuleBuilder child(Builder builder) {
        children.add(builder.build());
        return this;
    }

    public ObjectRuleBuilder child(Validatable validatable) {
        children.add(validatable);
        return this;
    }

    @Override
    public Validatable build() {
        return new ValidationObject(field, children);
    }
}

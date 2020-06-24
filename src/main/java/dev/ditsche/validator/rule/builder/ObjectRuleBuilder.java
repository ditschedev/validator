package dev.ditsche.validator.rule.builder;

import dev.ditsche.validator.validation.Validatable;
import dev.ditsche.validator.validation.ValidationObject;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Tobias Dittmann
 */
public class ObjectRuleBuilder implements Builder {

    private final String field;

    private boolean optional;

    private List<Validatable> children;

    ObjectRuleBuilder(String field) {
        this.field = field;
        this.children = new LinkedList<>();
    }

    public ObjectRuleBuilder child(Builder builder) {
        children.add(builder.build());
        return this;
    }

    public ObjectRuleBuilder child(Validatable validatable) {
        children.add(validatable);
        return this;
    }

    public ObjectRuleBuilder optional() {
        this.optional = true;
        return this;
    }

    @Override
    public Validatable build() {
        return new ValidationObject(field, children, optional);
    }
}

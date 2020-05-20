package dev.ditsche.validator;

import dev.ditsche.validator.error.ErrorBag;
import dev.ditsche.validator.error.FieldNotAccessibleException;
import dev.ditsche.validator.error.ValidationError;
import dev.ditsche.validator.error.ValidationException;
import dev.ditsche.validator.rule.*;
import dev.ditsche.validator.rule.builder.Builder;
import dev.ditsche.validator.validation.Validatable;
import dev.ditsche.validator.validation.ValidationResult;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Validates an object against a defined schema.
 */
public class Validator {

    /**
     * The error bag for the
     * collected errors.
     */
    private ErrorBag errorBag;

    /**
     * All registered fields.
     */
    private List<Validatable> fields;

    /**
     * Parses a pattern and creates a Rule instance dynamically if it
     * is registered inside the RuleMap.
     */
    private RuleParser ruleParser;

    /**
     * Create a new validator instance based on a given type.
     */
    private Validator() {
        this.errorBag = new ErrorBag();
        this.ruleParser = new RuleParser();
        this.fields = new ArrayList<>();
    }

    public static Validator fromRules(Builder ...builders) {
        Validator validator = new Validator();
        for(Builder builder : builders) {
            validator.add(builder);
        }
        return validator;
    }

    public static Validator fromRules(Validatable ...rules) {
        Validator validator = new Validator();
        for(Validatable validatable : rules) {
            validator.add(validatable);
        }
        return validator;
    }

    public static Validator empty() {
        return new Validator();
    }

    public Validator add(Builder builder) {
        return add(builder.build());
    }

    public Validator add(Validatable validatable) {
        this.fields.add(validatable);
        return this;
    }

    public <T> T validate(T object) {
        return validate(object, false);
    }

    /**
     * Validates an object against a schema and returns an error bag.
     *
     * @param object The object that need to be validated.
     */
    public <T> T validate(T object, boolean abortEarly) {
        errorBag.clear();
        List<Field> fieldSet = new ArrayList<>();
        for (Class<?> c = object.getClass(); c != null; c = c.getSuperclass())
        {
            Field[] fields = c.getDeclaredFields();
            fieldSet.addAll(Arrays.asList(fields));
        }
        for(Validatable validatable : this.fields) {
            try {
                Field field = fieldSet.stream().filter(f -> f.getName().equals(validatable.getField())).findFirst().orElse(null);
                if(field == null) continue;
                Object value = FieldUtils.readField(field, object, true);
                ValidationResult result = validatable.validate("", value, abortEarly);
                if(result.isChanged())
                    FieldUtils.writeField(field, object, result.getValue(), true);
                errorBag.merge(result.getErrorBag());
            } catch (IllegalAccessException ex) {
                throw new FieldNotAccessibleException();
            }
        }
        if(!errorBag.isEmpty())
            throw new ValidationException(errorBag);

        return object;
    }

    /**
     * Registers a custom rule with an abbreviation.
     *
     * @param ruleKey The abbreviation for the custom rule.
     * @param ruleClass The custom rule class.
     * @param paramTypes If the rule needs params pass the types in the correct order.
     */
    public void register(String ruleKey, Class<? extends Rule> ruleClass, Class<?> ...paramTypes) {
        this.ruleParser.register(ruleKey, new RuleInfo(ruleClass, paramTypes));
    }

}

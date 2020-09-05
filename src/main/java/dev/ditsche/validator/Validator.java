package dev.ditsche.validator;

import dev.ditsche.validator.error.ErrorBag;
import dev.ditsche.validator.error.FieldNotAccessibleException;
import dev.ditsche.validator.error.ValidationException;
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
     * Create a new validator instance based on a given type.
     */
    private Validator() {
        this.errorBag = new ErrorBag();
        this.fields = new ArrayList<>();
    }

    /**
     * Creates a validator from a given set of rules.
     *
     * @param builders The rule builders defining the fields of
     *                 the validation object.
     * @return A validator instance containing the provided rules.
     */
    public static Validator fromRules(Builder ...builders) {
        Validator validator = new Validator();
        for(Builder builder : builders) {
            validator.addField(builder);
        }
        return validator;
    }

    /**
     * Creates a validator from a given set of rules.
     *
     * @param validatable The rule builders defining the fields of
     *                 the validation object.
     * @return A validator instance containing the provided rules.
     */
    public static Validator fromRules(Validatable ...validatable) {
        Validator validator = new Validator();
        for(Validatable val : validatable) {
            validator.addField(val);
        }
        return validator;
    }

    /**
     * Creates a new and empty Validator without any fields and rules.
     *
     * @return A fresh and empty validator instance.
     */
    public static Validator empty() {
        return new Validator();
    }

    /**
     * Adds a field and the provided rules to the validator.
     * If the field already exists, the rules will be added.
     *
     * @param builder The rules of the field as a builder.
     * @return The updated validator instance.
     */
    public Validator addField(Builder builder) {
        return addField(builder.build());
    }

    /**
     * Adds a field and the provided rules to the validator.
     * If the field already exists, the rules will be added.
     *
     * @param validatable The rules of the field as a validatable object.
     * @return The updated validator instance.
     */
    public Validator addField(Validatable validatable) {
        this.fields.add(validatable);
        return this;
    }

    /**
     * Adds multiple fields and the provided rules to the validator.
     * If a field already exists, the rules will be added.
     *
     * @param builders The rules of the fields as builders.
     * @return The updated validator instance.
     */
    public Validator addFields(Builder ...builders) {
        for(Builder builder : builders) {
            this.addField(builder.build());
        }
        return this;
    }

    /**
     * Adds multiple fields and the provided rules to the validator.
     * If a field already exists, the rules will be added.
     *
     * @param validatable The rules of the fields as validatable objects.
     * @return The updated validator instance.
     */
    public Validator addFields(Validatable ...validatable) {
        this.fields.addAll(List.of(validatable));
        return this;
    }

    /**
     * Validates an object against a schema and returns an error bag.
     * Sets abort early to false.
     *
     * @param object The object that need to be validated.
     * @param <T> The type of the validated object.
     * @return The validated object.
     */
    public <T> T validate(T object) {
        return validate(object, false);
    }

    /**
     * Validates an object against a schema and returns an error bag.
     *
     * @param object The object that need to be validated.
     * @param abortEarly Indicates, if the validator should return after finding
     *                   the first unsuccessful rule.
     * @param <T> The type of the validated object.
     * @return The validated object.
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

}

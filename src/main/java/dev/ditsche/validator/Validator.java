package dev.ditsche.validator;

import dev.ditsche.validator.error.ErrorBag;
import dev.ditsche.validator.error.ValidationException;
import dev.ditsche.validator.rule.*;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Validates an object against a defined schema.
 *
 * @param <T> The type of the validated object.
 */
public class Validator<T> {

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
    public Validator() {
        this.errorBag = new ErrorBag();
        this.ruleParser = new RuleParser();
        this.fields = new ArrayList<>();
    }

    public Validator<T> add(Validatable validatable) {
        this.fields.add(validatable);
        return this;
    }

    public T validate(T object) throws ValidationException, IllegalAccessException {
        return validate(object, false);
    }

    /**
     * Validates an object against a schema and returns an error bag.
     *
     * @param object The object that need to be validated.
     * @throws ValidationException Thrown when at least one rule fails.
     * @throws IllegalAccessException Thrown when the field is not public.
     */
    public T validate(T object, boolean abortEarly) throws ValidationException, IllegalAccessException {
        errorBag.clear();
        List<Field> fieldSet = new ArrayList<>();
        for (Class<?> c = object.getClass(); c != null; c = c.getSuperclass())
        {
            Field[] fields = c.getDeclaredFields();
            fieldSet.addAll(Arrays.asList(fields));
        }
        for(Validatable validatable : this.fields) {
            Field field = fieldSet.stream().filter(f -> f.getName().equals(validatable.getField())).findFirst().orElse(null);
            if(field == null) continue;
            Object value = FieldUtils.readField(field, object, true);
            ValidationResult result = validatable.validate(value, abortEarly);
            if(result.isChanged())
                FieldUtils.writeField(field, object, value, true);
            errorBag.merge(result.getErrorBag());
        }
        if(!errorBag.isEmpty())
            throw new ValidationException(errorBag);

        return object;
    }

    /**
     * Uses reflection to invoke a getter of the validation target.
     * Falls back to the fields default getter. Will fail if the variable
     * is not publicly accessible.
     *
     * @param field The field name whose value should be received.
     * @param object The validation target object.
     * @return {@code null} if the field cannot be resolved, or the value.
     */
    private Object getValue(Field field, Object object) throws IllegalAccessException {
        return FieldUtils.readField(field, object, true);
    }

    private Object setValue(Field field, Object value, Object object) throws IllegalAccessException {
        FieldUtils.writeField(field, object, value, true);
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

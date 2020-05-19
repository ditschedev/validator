package dev.ditsche.validator.rule;

import dev.ditsche.validator.error.ErrorBag;
import dev.ditsche.validator.error.ValueNotAccessibleException;
import lombok.Getter;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Tobias Dittmann
 */
public class ValidationObject implements Validatable {

    @Getter
    private String field;

    @Getter
    private List<Validatable> fields;

    @Override
    public ErrorBag validate(Object object, boolean abortEarly) {
        ErrorBag errorBag = new ErrorBag();
        List<Field> fieldSet = new ArrayList<>();
        for (Class<?> c = object.getClass(); c != null; c = c.getSuperclass()) {
            Field[] fields = c.getDeclaredFields();
            fieldSet.addAll(Arrays.asList(fields));
        }
        for(Validatable validatable : fields) {
            Field field = fieldSet.stream().filter(f -> f.getName().equals(validatable.getField())).findFirst().orElse(null);
            if(field == null) continue;
            try {
                Object value = getValue(field, object);
                errorBag.merge(validatable.validate(value, abortEarly));
            } catch (IllegalAccessException e) {
                throw new ValueNotAccessibleException();
            }
        }
        return errorBag;
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
        for (Method method : object.getClass().getMethods()) {
            if ((method.getName().startsWith("get")) && (method.getName().length() == (field.getName().length() + 3)) ||
                    (method.getName().startsWith("is")) && (method.getName().length() == (field.getName().length() + 2))) {
                if (method.getName().toLowerCase().endsWith(field.getName().toLowerCase())) {
                    try {
                        return method.invoke(object);
                    }
                    catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return field.get(object);
    }
}

package dev.ditsche.validator.validation;

import dev.ditsche.validator.error.ErrorBag;
import dev.ditsche.validator.error.FieldNotAccessibleException;
import lombok.Getter;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Field;
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
    private List<Validatable> children;

    public ValidationObject(String field, List<Validatable> children) {
        this.field = field;
        this.children = children;
    }

    @Override
    public ValidationResult validate(String parent, Object object, boolean abortEarly) {
        ErrorBag errorBag = new ErrorBag();
        boolean changed = false;
        List<Field> fieldSet = new ArrayList<>();
        for (Class<?> c = object.getClass(); c != null; c = c.getSuperclass()) {
            Field[] fields = c.getDeclaredFields();
            fieldSet.addAll(Arrays.asList(fields));
        }
        for(Validatable validatable : children) {
            Field field = fieldSet.stream().filter(f -> f.getName().equals(validatable.getField())).findFirst().orElse(null);
            if(field == null) continue;
            try {
                Object value = FieldUtils.readField(field, object, true);
                ValidationResult validationResult = validatable.validate(this.field + ".", value, abortEarly);
                errorBag.merge(validationResult.getErrorBag());
                if(validationResult.isChanged())
                    FieldUtils.writeField(field, object, validationResult.getValue(), true);
            } catch (IllegalAccessException e) {
                throw new FieldNotAccessibleException();
            }
        }
        return new ValidationResult(errorBag, object, changed);
    }
}

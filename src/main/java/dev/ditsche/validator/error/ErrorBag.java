package dev.ditsche.validator.error;

import lombok.Getter;

import java.util.HashMap;
import java.util.List;

/**
 * The error bag holds the fields for which at least one rule has not passed.
 */
public class ErrorBag {

    /**
     * Holds information about the errors for each field.
     */
    @Getter
    HashMap<String, ValidationError> errors;

    public ErrorBag() {
        clear();
    }

    /**
     * Adds an error for a given field
     * to the bag.
     *
     * @param field The field that did not pass.
     * @param message The error message.
     */
    public void add(String field, String message) {
        add(new ValidationError(field, List.of(message)));
    }

    public void add(ValidationError validationError) {
        List<String> errorList = errors.getOrDefault(validationError.getField(), new ValidationError(validationError.getField())).getErrors();
        errorList.addAll(validationError.getErrors());
        errors.put(validationError.getField(), new ValidationError(validationError.getField(), errorList));
    }

    /**
     * Clears the errors.
     */
    public void clear() {
        this.errors = new HashMap<>();
    }

    /**
     * Checks if the bag contains
     * any errors.
     *
     * @return {@code true} if any errors
     * occur, {@code false} if not.
     */
    public boolean isEmpty() {
        return errors.isEmpty();
    }

    public void merge(ErrorBag errorBag) {
        for(ValidationError error: errorBag.getErrors().values()) {
            add(error);
        }
    }

}
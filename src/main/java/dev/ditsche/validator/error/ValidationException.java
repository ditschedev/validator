package dev.ditsche.validator.error;

import java.util.Collection;

/**
 * Thrown when the validation of an object fails.
 * Gets the error bag of the Validator and provides access to the errors.
 */
public class ValidationException extends RuntimeException {

    /**
     * The error bag holding the errors.
     */
    private final ErrorBag errorBag;


    /**
     * Initiates a new exception.
     *
     * @param errorBag The error bag.
     */
    public ValidationException(ErrorBag errorBag) {
        if(errorBag == null)
            throw new IllegalArgumentException("error bag cannot be null");
        this.errorBag = errorBag;
    }

    /**
     * Returns the errors is a mapped representation.
     *
     * @return A mapped representation of fields and its errors.
     */
    public Collection<ValidationError> getErrors() {
        return errorBag.getErrors().values();
    }

}

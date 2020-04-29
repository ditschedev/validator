package dev.ditsche.validator.error;

import java.util.HashMap;
import java.util.List;

/**
 * Thrown when the validation of an object fails.
 * Gets the error bag of the Validator and provides access
 * to the errors.
 */
public class ValidationException extends Exception {

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
        this.errorBag = errorBag;
    }

    /**
     * Returns the errors is a mapped representation.
     *
     * @return A mapped representation of fields and its errors.
     */
    public HashMap<String, List<String>> getErrors() {
        return errorBag.getErrors();
    }

}

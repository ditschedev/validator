package dev.ditsche.validator.error;

import java.util.HashMap;
import java.util.List;

public class ValidationException extends Exception {

    private final ErrorBag errorBag;

    public ValidationException(ErrorBag errorBag) {
        this.errorBag = errorBag;
    }

    public HashMap<String, List<String>> getErrors() {
        return errorBag.getErrors();
    }

}

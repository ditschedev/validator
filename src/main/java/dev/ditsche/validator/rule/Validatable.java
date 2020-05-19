package dev.ditsche.validator.rule;

import dev.ditsche.validator.error.ErrorBag;

public interface Validatable {

    String getField();

    ValidationResult validate(Object object, boolean abortEarly);

}

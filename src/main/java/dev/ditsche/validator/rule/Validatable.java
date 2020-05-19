package dev.ditsche.validator.rule;

import dev.ditsche.validator.error.ErrorBag;

public interface Validatable {

    String getField();

    ErrorBag validate(Object object, boolean abortEarly);

}

package dev.ditsche.validator.validation;

public interface Validatable {

    String getField();

    ValidationResult validate(Object object, boolean abortEarly);

}

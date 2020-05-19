package dev.ditsche.validator.rule;

public interface Validatable {

    String getField();

    ValidationResult validate(Object object, boolean abortEarly);

}

package dev.ditsche.validator.validation;

public interface Validatable {

    String getField();

    ValidationResult validate(String parent, Object object, boolean abortEarly);

}

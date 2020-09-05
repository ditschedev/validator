package dev.ditsche.validator.error;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ValidationErrorTest {

    @Test
    public void shouldCreateEmptyObject() {
        final ValidationError error = new ValidationError();
        assertThat(error.getField()).isNull();
        assertThat(error.getErrors()).isNull();
    }

    @Test
    public void shouldCreateValidObject() {
        final String field = "field";
        final ValidationError error = new ValidationError(field);

        assertThat(error.getField()).isEqualTo(field);
        assertThat(error.getErrors()).isNotNull().isEmpty();
    }

    @Test
    public void shouldAlterAttributes() {
        final String field = "field";
        final List<ValidationErrorInfo> errors = new ArrayList<>();
        final ValidationError error = new ValidationError(field, errors);

        assertThat(error.getField()).isEqualTo(field);
        assertThat(error.getErrors()).isEqualTo(errors);

        error.setField("newfield");
        error.setErrors(null);
        assertThat(error.getField()).isNotEqualTo(field);
        assertThat(error.getErrors()).isNotEqualTo(errors);
    }

}
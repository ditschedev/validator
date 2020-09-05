package dev.ditsche.validator.error;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ValidationErrorInfoTest {

    @Test
    public void shouldCreateEmptyObject() {
        final ValidationErrorInfo info = new ValidationErrorInfo();
        assertThat(info.getMessage()).isNull();
        assertThat(info.getErrorType()).isNull();
    }

    @Test
    public void shouldCreateValidObject() {
        final String message = "message";
        final String errorType = "type";
        final ValidationErrorInfo info = new ValidationErrorInfo(message, errorType);

        assertThat(info.getMessage()).isEqualTo(message);
        assertThat(info.getErrorType()).isEqualTo(errorType);
    }

    @Test
    public void shouldAlterAttributes() {
        final String message = "message";
        final String errorType = "type";
        final ValidationErrorInfo info = new ValidationErrorInfo(message, errorType);

        assertThat(info.getMessage()).isEqualTo(message);
        assertThat(info.getErrorType()).isEqualTo(errorType);

        info.setErrorType("type2");
        info.setMessage("message2");
        assertThat(info.getMessage()).isNotEqualTo(message);
        assertThat(info.getErrorType()).isNotEqualTo(errorType);
    }

}
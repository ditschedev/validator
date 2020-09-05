package dev.ditsche.validator.error;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

public class ValidationExceptionTest {

    @Test
    public void constructorShouldNotAllowInvalidArguments() {
        assertThatIllegalArgumentException().isThrownBy(() -> new ValidationException(null));
    }

    @Test
    public void constructorShouldCreateValidObjectWithEmptyErrorBag() {
        ErrorBag errorBag = new ErrorBag();
        final ValidationException exception = new ValidationException(errorBag);
        assertThat(exception.getErrors()).isEmpty();
    }

    @Test
    public void constructorShouldCreateValidObjectWithNonEmptyErrorBag() {
        ErrorBag errorBag = new ErrorBag();
        errorBag.add("test", "required", "Field is required");
        errorBag.add("test2", "required", "Field is also required");
        final ValidationException exception = new ValidationException(errorBag);
        assertThat(exception.getErrors()).isNotEmpty();
        assertThat(exception.getErrors().size()).isEqualTo(2);
    }

}
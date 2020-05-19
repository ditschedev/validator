package dev.ditsche.validator.validation;

import dev.ditsche.validator.error.ErrorBag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Tobias Dittmann
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidationResult {

    private ErrorBag errorBag;

    private Object value;

    private boolean changed;

}

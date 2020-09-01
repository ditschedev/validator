package dev.ditsche.validator.error;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Tobias Dittmann
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ValidationErrorInfo {

    private String message;

    private String errorType;

}

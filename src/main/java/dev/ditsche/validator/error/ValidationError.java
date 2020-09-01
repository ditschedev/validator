package dev.ditsche.validator.error;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Tobias Dittmann
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidationError {

    private String field;

    private List<ValidationErrorInfo> errors;

    public ValidationError(String field) {
        this(field, new LinkedList<>());
    }

}

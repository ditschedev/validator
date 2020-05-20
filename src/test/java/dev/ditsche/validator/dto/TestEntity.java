package dev.ditsche.validator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Tobias Dittmann
 */
@Data
@AllArgsConstructor
public class TestEntity {
    private String title;
    private String email;
    private String firstName;
    private int count;
    private NestedEntity nestedEntity;
}

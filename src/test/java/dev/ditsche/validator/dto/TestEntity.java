package dev.ditsche.validator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

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
    private boolean active;
    private int[] elements;
    private List<NestedEntity> objects;
}

package dev.ditsche.validator.dto;

import lombok.Data;

/**
 * @author Tobias Dittmann
 */
@Data
public class TestEntity {
    private String title;
    private String email;
    private String firstName;
    private int count;

    public TestEntity(String title, String email, String firstName, int count) {
        this.title = title;
        this.email = email;
        this.firstName = firstName;
        this.count = count;
    }
}

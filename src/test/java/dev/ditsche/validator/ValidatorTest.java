package dev.ditsche.validator;

import dev.ditsche.validator.dto.NestedEntity;
import dev.ditsche.validator.dto.TestEntity;
import dev.ditsche.validator.error.ValidationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static dev.ditsche.validator.rule.builder.Rules.*;

public class ValidatorTest {

    Validator validator;

    @BeforeEach
    public void setUp() {
        validator = Validator.empty();
    }

    @Test
    public void shouldValidateExample() {
        TestEntity testEntity = new TestEntity("Mr", "hello@ditsche.dev", "Tobias", 4, new NestedEntity("Max"));

        validator = Validator.fromRules(
                string("title").required().trim().max(3),
                string("email").required().trim().email(),
                string("firstName").defaultValue("").trim().alphanum().max(80),
                number("count").max(5),
                object("nestedEntity").child(
                        string("name").required().trim().min(1)
                )
        );

        try {
            testEntity = validator.validate(testEntity);
        } catch (ValidationException ex) {
            throw  ex;
        }
    }

}
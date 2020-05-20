package dev.ditsche.validator;

import dev.ditsche.validator.dto.NestedEntity;
import dev.ditsche.validator.dto.TestEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static dev.ditsche.validator.rule.builder.Rules.*;
import static org.assertj.core.api.Assertions.assertThat;

public class ValidatorTest {

    Validator validator;

    @BeforeEach
    public void setUp() {
        validator = Validator.empty();
    }

    @Test
    public void shouldValidateExample() {
        final String email = "hello@ditsche.dev  ";
        TestEntity testEntity = new TestEntity("Mr", email, "Tobias", 4, new NestedEntity("Max"));
        assertThat(email).isEqualTo(testEntity.getEmail());
        validator = Validator.fromRules(
                string("title").required().trim().max(3),
                string("email").required().trim().email(),
                string("firstName").defaultValue("").trim().alphanum().max(80),
                number("count").max(5),
                object("nestedEntity").child(
                        string("name").required().trim().min(1)
                )
        );
        testEntity = validator.validate(testEntity);
        assertThat(email).isNotEqualTo(testEntity.getEmail());

    }

}
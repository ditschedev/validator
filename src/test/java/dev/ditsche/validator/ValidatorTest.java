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
        TestEntity testEntity = new TestEntity("Mr", email, "Tobias", 4, new NestedEntity("Max"), true, new int[2], null);
        assertThat(email).isEqualTo(testEntity.getEmail());
        validator = Validator.fromRules(
                array("elements").required().max(2),
                string("title").required().trim().max(3),
                string("email").required().trim().email(),
                string("firstName").defaultValue("").trim().alphanum().max(80),
                number("count").max(5),
                object("nestedEntity").child(
                        string("name").required().trim().min(1)
                ),
                bool("active").isTrue()
        );
        testEntity = validator.validate(testEntity);
        assertThat(email).isNotEqualTo(testEntity.getEmail());
        assertThat(testEntity.getEmail()).isEqualTo(email.trim());
    }

    @Test
    public void shouldValidateNestedArray() {
        final String email = "hello@ditsche.dev  ";
        TestEntity testEntity = new TestEntity("Mr", email, "Tobias", 4, new NestedEntity("Max"), true, null, null);
        assertThat(email).isEqualTo(testEntity.getEmail());
        validator = Validator.fromRules(
                array("elements").optional().elements().max(10),
                array("objects").optional().objects(
                        string("name").required().trim().min(1)
                ),
                string("title").required().trim().max(3),
                string("email").required().trim().email(),
                string("firstName").defaultValue("").trim().alphanum().max(80),
                number("count").max(5),
                object("nestedEntity").child(
                        string("name").required().trim().min(1)
                ),
                bool("active").isTrue()
        );
        testEntity = validator.validate(testEntity);
        assertThat(email).isNotEqualTo(testEntity.getEmail());
        assertThat(testEntity.getEmail()).isEqualTo(email.trim());
    }

    @Test
    public void shouldSkipOptionalPropertyIfNotSet() {
        TestEntity testEntity = new TestEntity("Mr", "max@muster.de", null, 3, null, true, null, null);
        validator = Validator.fromRules(
            string("title").required().trim().max(3),
            string("email").required().trim().email(),
            string("firstName").optional().trim().alphanum().max(80),
            number("count").max(5)
        );
        testEntity = validator.validate(testEntity);
        assertThat(testEntity.getFirstName()).isNull();
    }

    @Test
    public void shouldValidateOptionalPropertyIfSet() {
        TestEntity testEntity = new TestEntity("Mr", "max@muster.de", "Max   ", 3, null, true, null, null);
        validator = Validator.fromRules(
                string("title").required().trim().max(3),
                string("email").required().trim().email(),
                string("firstName").optional().trim().alphanum().max(80),
                number("count").max(5)
        );
        testEntity = validator.validate(testEntity);
        assertThat(testEntity.getFirstName().length()).isEqualTo(3);
    }

    @Test
    public void shouldSkipOptionalNestedPropertyIfNotSet() {
        TestEntity testEntity = new TestEntity("Mr", "max@muster.de", null, 3, null, true, null, null);
        validator = Validator.fromRules(
                string("title").required().trim().max(3),
                string("email").required().trim().email(),
                string("firstName").optional().trim().alphanum().max(80),
                number("count").max(5),
                object("nestedEntity").optional().fields(
                        string("name").required().trim().min(1)
                )
        );
        testEntity = validator.validate(testEntity);
        assertThat(testEntity.getNestedEntity()).isNull();
    }

    @Test
    public void shouldValidateOptionalNestedPropertyIfSet() {
        final String name = "  Test   ";
        TestEntity testEntity = new TestEntity("Mr", "max@muster.de", null, 3, new NestedEntity(name), true, null, null);
        validator = Validator.fromRules(
                string("title").required().trim().max(3),
                string("email").required().trim().email(),
                string("firstName").optional().trim().alphanum().max(80),
                number("count").max(5),
                object("nestedEntity").optional().fields(
                        string("name").required().trim().min(1)
                )
        );
        testEntity = validator.validate(testEntity);
        assertThat(testEntity.getNestedEntity().getName()).isNotEqualTo(name);
    }

}
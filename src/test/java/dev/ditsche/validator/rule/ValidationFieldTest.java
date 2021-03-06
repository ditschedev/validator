package dev.ditsche.validator.rule;

import dev.ditsche.validator.validation.ValidationField;
import org.junit.jupiter.api.Test;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class ValidationFieldTest {

    @Test
    public void constructorShouldNotAllowInvalidFieldName() {
        Stream.of(null, "", "\t", " ").forEach(field ->
                assertThatExceptionOfType(IllegalArgumentException.class)
                        .isThrownBy(() -> new ValidationField(field))
                        .withMessage("Validation field requires a valid field name")
        );
    }

    @Test
    public void addRuleAddsTheGivenRule() {
        ValidationField vf = new ValidationField("field");
        assertThat(vf.getRules().size()).isEqualTo(0);
        vf.addRule(new Rule() {
            @Override
            public RuleResult test(Object value) {
                return RuleResult.reject();
            }

            @Override
            public String message(String field) {
                return "Testmessage";
            }

            @Override
            public String getType() {
                return RULE_TYPE_PREFIX + "test";
            }
        });
        assertThat(vf.getRules().size()).isEqualTo(1);
    }

    @Test
    public void addRuleShouldAddOnlyNewRules() {
        ValidationField vf = new ValidationField("field");
        assertThat(vf.getRules().size()).isEqualTo(0);
        Rule rule = new Rule() {
            @Override
            public RuleResult test(Object value) {
                return RuleResult.reject();
            }

            @Override
            public String message(String field) {
                return "Testmessage";
            }

            @Override
            public String getType() {
                return RULE_TYPE_PREFIX + "test";
            }
        };
        vf.addRule(rule);
        assertThat(vf.getRules().size()).isEqualTo(1);
        vf.addRule(rule);
        assertThat(vf.getRules().size()).isEqualTo(1);
    }

}
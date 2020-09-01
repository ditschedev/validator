package dev.ditsche.validator.rule;

import dev.ditsche.validator.rule.ruleset.MaxRule;
import dev.ditsche.validator.rule.ruleset.RequiredRule;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RuleParserTest {

    private final RuleParser ruleParser = new RuleParser();

    @Test
    public void shouldParseRequiredRuleByString() {
        Rule rule = ruleParser.parse("required").orElse(null);
        assertThat(rule.getClass()).isEqualTo(RequiredRule.class);
        assertThat(rule.test("abc").isPassed()).isTrue();
    }

    @Test
    public void shouldParseMaxRuleByString() {
        Rule rule = ruleParser.parse("max:50").orElse(null);
        assertThat(rule.getClass()).isEqualTo(MaxRule.class);

        assertThat(rule.test(51).isPassed()).isFalse();
        assertThat(rule.test(49).isPassed()).isTrue();
    }

}
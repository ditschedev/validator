package com.github.ditschedev.validator.rule;

import com.github.ditschedev.validator.ruleset.MaxRule;
import com.github.ditschedev.validator.ruleset.RequiredRule;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RuleParserTest {

    private final RuleParser ruleParser = new RuleParser();

    @Test
    public void shouldParseRequiredRuleByString() {
        Rule rule = ruleParser.parse("required").orElse(null);
        assertThat(rule.getClass()).isEqualTo(RequiredRule.class);
        assertThat(rule.passes("abc")).isTrue();
    }

    @Test
    public void shouldParseMaxRuleByString() {
        Rule rule = ruleParser.parse("max:50").orElse(null);
        assertThat(rule.getClass()).isEqualTo(MaxRule.class);

        assertThat(rule.passes(51)).isFalse();
        assertThat(rule.passes(49)).isTrue();
    }

}
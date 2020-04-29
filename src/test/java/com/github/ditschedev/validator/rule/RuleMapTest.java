package com.github.ditschedev.validator.rule;

import com.github.ditschedev.validator.ruleset.RequiredRule;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RuleMapTest {

    private final RuleMap ruleMap = new RuleMap();

    @Test
    public void ruleMapShouldRegisterRulesByDefault() {
        assertThat(ruleMap.lookup("required")).isPresent();
    }

    @Test
    public void ruleMapShouldNotFindUnregisteredRule() {
        assertThat(ruleMap.lookup("nottherehopefully")).isNotPresent();
    }

    @Test
    public void ruleMapShouldRegisterRule() {
        assertThat(ruleMap.lookup("test")).isNotPresent();
        ruleMap.add("test", new RuleInfo(RequiredRule.class));
        assertThat(ruleMap.lookup("test")).isPresent();
    }

    @Test
    public void ruleMapShouldOverrideRegisteredRules() {
        assertThat(ruleMap.lookup("required")).isPresent();
        RuleInfo info = ruleMap.lookup("required").get();
        RuleInfo newInfo = new RuleInfo(RequiredRule.class, String.class);
        ruleMap.add("required", newInfo);
        assertThat(ruleMap.lookup("required")).isPresent();
        assertThat(ruleMap.lookup("required").get()).isNotEqualTo(info);
        assertThat(ruleMap.lookup("required").get()).isEqualTo(newInfo);
    }

}
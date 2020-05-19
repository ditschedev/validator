package dev.ditsche.validator.rule;

import dev.ditsche.validator.rule.builder.StringRuleBuilder;
import dev.ditsche.validator.ruleset.*;

import java.util.HashMap;
import java.util.Optional;

/**
 * Handles the mapping of string representations to {@link RuleInfo} objects for parsing.
 * Registers every parsable string representation of all rules.
 */
public class RuleMap {

    /**
     * Maps the strings to rule infos.
     */
    private HashMap<String, RuleInfo> ruleMap;

    /**
     * Initiates the rule map.
     */
    public RuleMap() {
        init();
    }

    /**
     * Initializes the initial rule state
     * and registers all default rules.
     */
    private void init() {
        ruleMap = new HashMap<>();
        add("required", new RuleInfo(RequiredRule.class));
        add("max", new RuleInfo(MaxRule.class, long.class));
        add("min", new RuleInfo(MinRule.class, long.class));
        add("alphanum", new RuleInfo(AlphaNumericRule.class));
        add("email", new RuleInfo(EmailRule.class));
        add("size", new RuleInfo(SizeRule.class, long.class, long.class));
        add("pattern", new RuleInfo(PatternRule.class, String.class));
    }

    /**
     * Registers a custom rule.
     *
     * @param ruleKey The string representation of the custom rule.
     * @param ruleInfo The rule info object.
     */
    public void add(String ruleKey, RuleInfo ruleInfo) {
        this.ruleMap.put(ruleKey, ruleInfo);
    }

    /**
     * Performs a lookup against the map and returns a
     * {@link RuleMap} if present.
     *
     * @param ruleKey The string representation that should be looked up.
     * @return An optional containing a rule info object if present.
     */
    public Optional<RuleInfo> lookup(String ruleKey) {
        return Optional.ofNullable(ruleMap.get(ruleKey));
    }

}

package com.github.ditschedev.validator.rule;

import com.github.ditschedev.validator.ruleset.MaxRule;
import com.github.ditschedev.validator.ruleset.RequiredRule;

import java.util.HashMap;
import java.util.Optional;

/**
 * Handles the mapping of string representations
 * to {@link RuleInfo} objects for parsing.
 * Registers every parsable string representation
 * of all rules.
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

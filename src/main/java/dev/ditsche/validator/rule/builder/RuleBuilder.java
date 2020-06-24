package dev.ditsche.validator.rule.builder;

import dev.ditsche.validator.rule.Rule;

import java.util.List;

/**
 * @author Tobias Dittmann
 */
public abstract class RuleBuilder implements Builder {

    protected String field;

    protected boolean optional;

    protected List<Rule> rules;

}

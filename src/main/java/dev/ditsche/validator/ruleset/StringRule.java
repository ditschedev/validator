package dev.ditsche.validator.ruleset;

import dev.ditsche.validator.rule.Rule;

/**
 * @author Tobias Dittmann
 */
public class StringRule implements Rule {

    @Override
    public boolean passes(Object value) {
        return (value instanceof String);
    }

    @Override
    public String message(String field) {
        return null;
    }
}

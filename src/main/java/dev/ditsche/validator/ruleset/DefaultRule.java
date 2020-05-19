package dev.ditsche.validator.ruleset;

import dev.ditsche.validator.rule.Rule;
import lombok.AllArgsConstructor;

/**
 * @author Tobias Dittmann
 */
@AllArgsConstructor
public class DefaultRule implements Rule {

    private final Object defaultValue;

    @Override
    public boolean passes(Object value) {
        return false;
    }

    @Override
    public String message(String field) {
        return null;
    }
}

package dev.ditsche.validator.rule;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Tobias Dittmann
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RuleResult {

    private boolean passed;

    private Object value;

    private boolean changed;

    public static RuleResult passes(boolean passes) {
        return new RuleResult(passes, null, false);
    }

    public static RuleResult resolve() {
        return new RuleResult(true, null, false);
    }

    public static RuleResult resolve(Object object) {
        return new RuleResult(true, object, true);
    }

    public static RuleResult reject() {
        return new RuleResult(false, null, false);
    }

}

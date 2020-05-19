package dev.ditsche.validator.rule;

/**
 * Describes a passable rule for the validator.
 * Optional parameters can be passed using the constructor.
 */
public interface Rule {

    /**
     * Checks if the field passes the rule.
     *
     * @param value The value that will be tested.
     * @return {@code true} if the test passes, {@code false} if not.
     */
    RuleResult passes(Object value);

    /**
     * The error message, when the test does not pass.
     *
     * @param field The name of the field.
     * @return A {@code} string representation of the error.
     */
    String message(String field);

}

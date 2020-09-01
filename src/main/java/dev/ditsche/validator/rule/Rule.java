package dev.ditsche.validator.rule;

/**
 * Describes a passable rule for the validator.
 * Optional parameters can be passed using the constructor.
 */
public interface Rule {

    String RULE_TYPE_PREFIX = "validation.error.";

    /**
     * Checks if the field passes the rule.
     *
     * @param value The value that will be tested.
     * @return {@code true} if the test passes, {@code false} if not.
     */
    RuleResult test(Object value);

    /**
     * The error message, when the test does not pass.
     *
     * @param field The name of the field.
     * @return A {@code String} representation of the error.
     */
    String message(String field);

    /**
     * Gets the type of the error message so that applications consuming
     * the validation results can make use of internationalization using the
     * error type key.
     *
     * @return A {@code String} key representing the error type.
     */
    String getType();

}

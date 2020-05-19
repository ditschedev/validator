package dev.ditsche.validator.rule.ruleset;

import dev.ditsche.validator.rule.Rule;
import dev.ditsche.validator.rule.RuleResult;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

/**
 * Defines a validation rule.
 * Checks if the given fields value is a valid email address
 * defined by RFC 822.
 */
public class EmailRule implements Rule {

    @Override
    public RuleResult passes(Object value) {
        if(value == null)
            return RuleResult.reject();

        if(!(value instanceof String))
            return RuleResult.reject();

        try {
            InternetAddress address = new InternetAddress((String) value);
            address.validate();
            return RuleResult.resolve();
        } catch (AddressException e) {
            return RuleResult.reject();
        }
    }

    @Override
    public String message(String field) {
        return String.format("The field \"%s\" must be a valid email address", field);
    }
}

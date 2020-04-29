package dev.ditsche.validator.ruleset;

import dev.ditsche.validator.rule.Rule;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

/**
 * Defines a validation rule.
 * Checks if the given fields value is a valid email address
 * defined by RFC 822.
 */
public class EmailRule implements Rule {
    @Override
    public boolean passes(Object value) {

        if(!(value instanceof String))
            return false;

        try {
            InternetAddress address = new InternetAddress((String) value);
            address.validate();
            return true;
        } catch (AddressException e) {
            return false;
        }
    }

    @Override
    public String message(String field) {
        return String.format("The field \"%s\" must be a valid email address", field);
    }
}

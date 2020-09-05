# Java Validator
A rule based validator developed for easy use with the Spring Boot framework.

- *Extendable*: You can always add custom rules

## Adding dependency
To use the validator add the following dependency to your `pom.xml`.
```xml
<dependency>
    <groupId>dev.ditsche</groupId>
    <artifactId>validator</artifactId>
    <version>2.2.0</version>
</dependency>
```

## Usage

Easiest way to use the validator in a Spring Boot project is to setup a `ValidatorConfig` class. There you can
define a bean for each validator you need. Or you can create a validator in the controller method, do it, as you like.

A validator ist built using static helper methods. Here is a working example:

```java
Validator validator = Validator.fromRules(
    string("name").required().trim().alphanum().max(50),
    string("gender").optional().defaultValue("m").max(1),
    number("age").required().min(18),
    object("tickets").fields(
        string("event").required().trim()
    )
);
```

This snippet validates an incoming DTO for the given fields and the nested object `tickets`.

To validate an object, just call the validate method of the validator. Pass in the object and the optional `abortEarly`
parameter. This is a boolean indicating, if the `ValidationException` should be thrown after the first validation error
or at the end if there are any.

Make sure, that if you use altering rules like `defaul()` or `trim()` you reassign the input object with the result of
the validate method. That way you get the validated and updated input DTO for further operations.

```java
dto = validator.validate(dto);
```

## Internationalization
Unfortunately it is not possible to change the outputted language to something different than english.
If you need to provide other languages as well, you can make use of the error type. When
you return the result of the `getErrors()` method of the ErrorBag that you got from the thrown
`ValidationException` you have the error type available and can show messages based on this
unique key. 

## Builders and Methods
As said, to generate a validator you should make use of the static helper methods.
The following types are supported at the moment:
- string
- number (int, long, float, ...)
- object
- array

In the following sections you learn which methods and rules are available by default and how
to use them.

### `string` rules

#### Usage
The following snippet returns an instance of the `StringRuleBuilder` class.
```java
string(field)
```

#### Available rules

- ##### `required()`
    Marks the field as *required* meaning it cannot be null or empty.
    
- ##### `optional()`
    Marks the field as *optional*. All rules behind this rule can fail.

- ##### `trim()`
    *Trims the input and alters* the value to the trimmed string.

- ##### `length(int)`
    Checks if the provided string has the *exact same length* as the provided parameter `length`.
    
- ##### `between(int, int)`
    Checks if the *length of the string is between* the first and second parameter integers.
    
- ##### `min(int)`
    Checks if the length of the string is *greater or equal* to the given parameter.

- ##### `max(int)`
    Checks if the length of the string is *smaller or equal* to the given parameter.

- ##### `email()`
    Checks if the fields value is a *valid email address*.

- ##### `url()`
    Checks if the fields value is a *valid url*.

- ##### `pattern(String)`
    Checks if the fields value *matches the given Regex* pattern. The 
    `pattern` parameter should be the `String` representation of a Regex.

- ##### `alphanum()`
    Checks if the fields value is *alphanumeric*.

- ##### `ip()`
    Checks if the fields value is a *valid ipv4 address*.

- ##### `creditCard()`
    Checks if the fields value is a *valid credit card number*.

- ##### `defaultValue(String)`
    *Sets the fields value to the given string*, if the fields value is null or empty.

- ##### `custom(Rule)`
    Registers a custom defined rule.

---    



## Custom rules
You can easily extend the functionality of the validator by defining custom rules. If you need a specific Regex and don't
want to use the `PatternRule` over and over again you can create your own rule.

To do this, implement the `Rule` interface and add an instance of your rule to the validator like shown below:

```java
import dev.ditsche.validator.rule.RuleResult;public class MyRule implements Rule {
    @Override
    public RuleResult test(Object value) {
        
        // Your validation logic


        // You have the following methods to generate a result:
        // RuleResult.reject()              -> Rejects the rule and marks it as not passed.
        // RuleResult.resolve()             -> Resolve the rule and mark it passed.
        // RuleResult.resolve(Object)       -> Resolve the rule and update the value of the field.
        // RuleResult.passes(boolean)       -> Resolves or rejects based on the given boolean or expression.
        return RuleResult.resolve();
    }

    @Override
    public String message(String field) {
        // The error message
        return String.format("The field \"%s\" is a custom error", field);
    }

    @Override
    public String getType() {
        // The error type, to make internationalization possible
        return RULE_TYPE_PREFIX + "my.rule";
    }
}
```

And use that rule in a validator:

```java
Validator.fromRules(
    ...,
    string("myfield").custom(new MyRule())
);
```

# Java Validator
A rule based validator developed for easy use with the Spring Boot framework.

- *Extendable* ✨: You can always add custom rules
- *Error format* ✅: You can directly return the result of the failed validation
- *Well documented* 📑: Always know what you are doing

## Table of contents
- [Adding dependency](#adding-dependency)
- [Usage](#usage)
- [Internationalization](#internationalization)
- [Builders and Methods](#builders-and-methods)
    - [`string` rules](#string-rules)
    - [`number` rules](#number-rules)
    - [`object` rules](#object-rules)
    - [`array` rules](#array-rules)
- [Use with Spring Boot](#use-with-spring-boot)
- [Custom rules](#custom-rules)


---


## Adding dependency
To use the validator add the following dependency to your `pom.xml`.
```xml
<dependency>
    <groupId>dev.ditsche</groupId>
    <artifactId>validator</artifactId>
    <version>2.2.0</version>
</dependency>
```


---


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


---


## Internationalization
Unfortunately it is not possible to change the outputted language to something different than english.

If you need to provide other languages as well, you can make use of the error type. When
you return the result of the `getErrors()` method of the ErrorBag that you got from the thrown
`ValidationException` you have the error type available and can show messages based on this
unique key. 


---


## Builders and Methods
As said, to generate a validator you should make use of the static helper methods.
The following types are supported at the moment:
- string
- number (int, long, float, ...)
- object
- array

In the following sections you learn which methods and rules are available by default and how
to use them. Each `Builder` has a `build()` method which returns a `Validateable`, if you need
to have one of those.

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
    Rejects if the given parameter is not assignable to the fields value.
    
- ##### `custom(Rule)`
    Registers a custom defined rule.

---    

### `number` rules

#### Usage
The following snippet returns an instance of the `NumberRuleBuilder` class.
```java
number(field)
```

#### Available rules

- ##### `required()`
    Marks the field as *required* meaning it cannot be null.
    
- ##### `optional()`
    Marks the field as *optional*. All rules behind this rule can fail.

- ##### `length(int)`
    Checks if the *fields value is the same* as the provided parameter `length`.
    
- ##### `size(int, int)`
    Checks if the *value is between* the first and second parameter integers.
    
- ##### `min(int)`
    Checks if the fields value is *greater or equal* to the given parameter.

- ##### `max(int)`
    Checks if the fields value of the string is *smaller or equal* to the given parameter.

- ##### `defaultValue(int)`
    *Sets the fields value to the given number*, if the fields value is null or 0.
    Rejects if the given parameter is not assignable to the fields value.

- ##### `custom(Rule)`
    Registers a custom defined rule.

---

### `object` rules

#### Usage
The following snippet returns an instance of the `ObjectRuleBuilder` class.
```java
object(field)
```

#### Available rules

- ##### `optional()`
    Marks the field as *optional*. All rules behind this rule can fail.
    
- ##### `child(Builder)`
    Adds a check for *one child property* using a `Builder`.

- ##### `child(Validatable)`
    Adds a check for *one child property* using a `Validatable`.

- ##### `fields(Builder[])`
    Adds a check for *one or more child properties* using the given `Builder`.

---

### `array` rules

#### Usage
The following snippet returns an instance of the `ArrayRuleBuilder` class.
```java
array(field)
```

#### Available rules

- ##### `required()`
    Marks the field as *required* meaning it cannot be null or empty.
    
- ##### `optional()`
    Marks the field as *optional*. All rules behind this rule can fail.

- ##### `length(int)`
    Checks if the *length of the array is the same* as the provided parameter `length`.
    
- ##### `size(int, int)`
    Checks if the *length of the array is between* the first and second parameter integers.
    
- ##### `min(int)`
    Checks if the length of the array is *greater or equal* to the given parameter.

- ##### `max(int)`
    Checks if the length of the array is *smaller or equal* to the given parameter.

- ##### `objects(Builder[])`
    *Used for object arrays only!* Gets an array of `Builder` instances, which are validated for every object in the array.
    
- ##### `custom(Rule)`
    Registers a custom defined rule.
    
#### Additional methods

Arrays are handled differently as they can have elements or objects as children. To handle
an array of elements you have to use the `elements` method which returns an `ArrayElementRuleBuilder`.

- ##### `elements()`
    Returns an `ArrayElementRuleBuilder` which has access to nearly every rule from above as
    the type of the arrays elements is unknown.
    

---


## Use with Spring Boot
To make use of the functionalities of Spring Boot and the structure of the errors returned by 
the `ErrorBag` instance of the thrown `ValidationException` you'd need to define a custom exception
handler. Spring Boot makes it easy to create one. 

The following advice generates a well-structured json response that the frontend can use
to display the errors in the UI. It returns with the `422 Unprocessable Entity` status code
of http which indicates a validation problem.

```java
@ControllerAdvice
public class ValidationAdvice {

    @ExceptionHandler(ValidationException.class)
    @ResponseBody
    public ResponseEntity<?> handleValidationErrors(ValidationException ex) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(
                ex.getErrors()
        );
    }
    
}
``` 

The resulting json would look like this:

```json
[
  {
    "field": "email",
    "errors": [
      {
        "message": "The field \"email\" is required",
        "errorType": "validation.error.format.required"
      },
      {
        "message": "The field \"email\" needs to be a valid email address",
        "errorType": "validation.error.format.email"
      } 
    ] 
  },
  ...
]
```


---


## Custom rules
You can easily extend the functionality of the validator by defining custom rules. If you need a specific Regex and don't
want to use the `PatternRule` over and over again you can create your own rule.

To do this, implement the `Rule` interface and add an instance of your rule to the validator like shown below:

```java
public class MyRule implements Rule {
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

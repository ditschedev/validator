# Java Validator
A rule based validator developed for easy use with the Spring Boot framework.

- *Extendable*: You can always add custom rules

## Usage
The Validator is generic and can be used to validate multiple objects. 
```java
Validator<User> validator = new Validator<>();
validator.addRule()
```
package com.github.ditschedev.validator;

import com.github.ditschedev.validator.error.ErrorBag;
import com.github.ditschedev.validator.error.ValidationException;
import com.github.ditschedev.validator.rule.Rule;
import com.github.ditschedev.validator.rule.RuleInfo;
import com.github.ditschedev.validator.rule.RuleParser;
import com.github.ditschedev.validator.rule.ValidationField;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Validates an object against a
 * defined schema.
 *
 * @param <T>
 */
public class Validator<T> {

    /**
     * The error bag for the
     * collected errors.
     */
    private ErrorBag errorBag;

    /**
     * All registered fields.
     */
    private List<ValidationField> fields;

    /**
     * Parses a pattern and creates a
     * Rule instance dynamically if it
     * is registered inside the RuleMap.
     */
    private RuleParser ruleParser;

    /**
     * Create a new validator instance based on a given type.
     */
    public Validator() {
        this.errorBag = new ErrorBag();
        this.ruleParser = new RuleParser();
        this.fields = new ArrayList<>();
    }

    /**
     * Adds a field to the schema.
     *
     * @param field The name of the field.
     * @param rules The assigned rules.
     * @return The instance of the validator.
     */
    public Validator<T> addField(String field, Rule ...rules) {
        ValidationField vf = fields.stream().filter(f -> f.getField().equals(field))
                .findFirst().orElse(null);
        if(vf == null) {
            fields.add(new ValidationField(field, rules));
        } else {
            fields.remove(vf);
            for(Rule rule : rules) {
                vf.addRule(rule);
            }
        }
        fields.add(vf);
        return this;
    }

    /**
     * Adds a field and rules based on a string
     * representation. Divide rule using a
     * {@code |} symbol. Parameters can be passed
     * using a {@code :} syntax.
     * Sample: required|max:50|length:10:50
     *
     *
     * @param field The name of the field.
     * @param rulesString he assigned rules in string representation.
     * @return The instance of the validator.
     */
    public Validator<T> addField(String field, String rulesString) {
        String[] rules = rulesString.split("\\|");
        ValidationField vf = fields.stream().filter(f -> f.getField().equals(field))
                .findFirst().orElse(new ValidationField(field));
        fields.remove(vf);
        for(String rule : rules) {
            ruleParser.parse(rule).ifPresent(vf::addRule);
        }
        fields.add(vf);
        return this;
    }

    /**
     * Validates an object against a schema and
     * returns an error bag.
     *
     * @param object The object that need to be validated.
     * @throws ValidationException
     */
    public void validate(T object) throws ValidationException {
        errorBag.clear();
        List<Field> fieldSet = new ArrayList<>();
        for (Class<?> c = object.getClass(); c != null; c = c.getSuperclass())
        {
            Field[] fields = c.getDeclaredFields();
            fieldSet.addAll(Arrays.asList(fields));
        }
        for(ValidationField vf : this.fields) {
            Field field = fieldSet.stream().filter(f -> f.getName().equals(vf.getField())).findFirst().orElse(null);
            if(field == null) continue;
            Object value = getValue(field, object);
            for(Rule rule : vf.getRules()) {
                if(!rule.passes(value)) errorBag.add(vf.getField(), rule.message(vf.getField()));
            }
        }
        if(!errorBag.isEmpty())
            throw new ValidationException(errorBag);
    }

    public static Object getValue(Field field, Object object) {
        for (Method method : object.getClass().getMethods()) {
            if ((method.getName().startsWith("get")) && (method.getName().length() == (field.getName().length() + 3)) ||
                    (method.getName().startsWith("is")) && (method.getName().length() == (field.getName().length() + 2))) {
                if (method.getName().toLowerCase().endsWith(field.getName().toLowerCase())) {
                    try {
                        return method.invoke(object);
                    }
                    catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return null;
    }

    /**
     * Registers a custom rule with an
     * abbreviation.
     *
     * @param ruleKey The abbreviation for the custom rule.
     * @param ruleClass The custom rule class.
     * @param paramTypes If the rule needs params pass the types in the correct order.
     */
    public void register(String ruleKey, Class<? extends Rule> ruleClass, Class<?> ...paramTypes) {
        this.ruleParser.register(ruleKey, new RuleInfo(ruleClass, paramTypes));
    }

}

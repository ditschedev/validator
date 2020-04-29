package dev.ditsche.validator.rule;

import java.beans.PropertyEditor;
import java.beans.PropertyEditorManager;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * Parses a string representation of a rule into a rule object using the {@link RuleInfo}.
 * <p>
 * Parameters for the rules can be separated using a {@code :}.
 * Multiple rules are separated by a {@code |}.
 */
public class RuleParser {

    /**
     * The global rule map registry.
     */
    private RuleMap ruleMap = new RuleMap();

    /**
     * Initiates a new rule parser.
     */
    public RuleParser() {
    }

    /**
     * Tries to parse a pattern to a rule.
     *
     * @param pattern The string representation of the rule including parameters.
     * @return The Rule if present.
     */
    public Optional<Rule> parse(String pattern) {
        try {
            String[] parts = pattern.split(":");
            Optional<RuleInfo> optionalRuleInfo = ruleMap.lookup(parts[0]);
            if(!optionalRuleInfo.isPresent())
                throw new IllegalArgumentException("There is no rule mapped to the pattern: " + pattern);
            RuleInfo ruleInfo = optionalRuleInfo.get();
            List<Object> params = new LinkedList<>();
            for (int i = 1; i < parts.length; i++) {
                String unparsedParam = parts[i];
                Class<?> paramType = ruleInfo.getParamTypes()[i-1];
                params.add(convert(paramType, unparsedParam));
            }
            return Optional.of(ruleInfo.getRule().getConstructor(ruleInfo.getParamTypes()).newInstance(params.toArray()));
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    /**
     * Registers a new rule to the rule map.
     *
     * @param ruleKey The name of the rule.
     * @param ruleInfo The rule infos.
     */
    public void register(String ruleKey, RuleInfo ruleInfo) {
        this.ruleMap.add(ruleKey, ruleInfo);
    }

    /**
     * Converts a string to a defined type.
     *
     * @param targetType The target type.
     * @param text The string that gets converted.
     * @return Returns the converted object.
     */
    private Object convert(Class<?> targetType, String text) {
        PropertyEditor editor = PropertyEditorManager.findEditor(targetType);
        editor.setAsText(text);
        return editor.getValue();
    }

}

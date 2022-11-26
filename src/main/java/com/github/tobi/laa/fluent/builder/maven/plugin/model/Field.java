package com.github.tobi.laa.fluent.builder.maven.plugin.model;

import java.lang.reflect.Modifier;

/**
 * <p>
 * Represents a single field to be used in the generation of source code.
 * </p>
 */
public record Field(
        int modifiers,
        @lombok.NonNull Class<?> type,
        @lombok.NonNull String name) implements CodeFragment, Comparable<Field> {

    @Override
    public int compareTo(final Field anotherField) {
        if (anotherField == null) {
            return 1;
        } else if (equals(anotherField)) {
            return 0;
        } else if (Modifier.isStatic(modifiers) && !Modifier.isStatic(anotherField.modifiers)) {
            return -1;
        } else if (!Modifier.isStatic(modifiers) && Modifier.isStatic(anotherField.modifiers)) {
            return 1;
        } else {
            final int nameCompare = name.compareTo(anotherField.name);
            if (nameCompare != 0) {
                return nameCompare;
            } else {
                return 1;
            }
        }
    }

    /**
     * <p>
     * Generates the Java source code for this field.
     * </p>
     * <p> Example:
     * <blockquote><pre>
     * new Field(2, BigDecimal.class, "myVar").toString()
     *     returns "    private BigDecimal myVar;"
     * </pre></blockquote>
     * </p>
     *
     * @return The Java source code for this field.
     */
    @Override
    public String toString() {
        return indent(1) + Modifier.toString(modifiers) +
                ' ' + type.getSimpleName() +
                ' ' + name + ';';
    }
}

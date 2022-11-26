package com.github.tobi.laa.fluent.builder.maven.plugin.model;

/**
 * <p>
 *     Represents a single parameter of a method or constructor to be used in the generation of source code.
 * </p>
 */
public record Parameter(
        boolean finalParam,
        @lombok.NonNull Class<?> type,
        @lombok.NonNull String name) implements CodeFragment {

    /**
     * <p>
     * Generates the Java source code for this parameter.
     * </p>
     * <p> Example:
     * <blockquote><pre>
     * new Parameter(false, BigDecimal.class, "myParam").toString()
     *     returns "BigDecimal myParam"
     * </pre></blockquote>
     * </p>
     *
     * @return The Java source code for this parameter.
     */
    @Override
    public String toString() {
        final StringBuilder parameter = new StringBuilder();
        if (finalParam) {
            parameter.append("final ");
        }
        parameter.append(type.getSimpleName()).append(' ').append(name);
        return parameter.toString();
    }
}

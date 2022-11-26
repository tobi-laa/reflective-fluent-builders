package com.github.tobi.laa.fluent.builder.maven.plugin.model;

/**
 * <p>
 *     Represents a fragment to be used in the generation of source code, such as an import statement, a constructor or
 *     a method.
 * </p>
 */
public interface CodeFragment {

    /**
     * <p>
     *     Generates the Java source code for this code fragment.
     * </p>
     * @return The Java source code for this code fragment.
     */
    String toString();

    /**
     * <p>
     *     Returns indentation, i.e. a certain number of spaces according to {@code depth}.
     * </p>
     * @param depth The depth of indentation. Must not be negative.
     * @return Indentation consisting of {@code depth} times 4 spaces.
     */
    default String indent(final int depth) {
        if (depth < 0) {
            throw new IllegalArgumentException("Depth must not be negative.");
        }
        return "    ".repeat(depth);
    }
}

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
}

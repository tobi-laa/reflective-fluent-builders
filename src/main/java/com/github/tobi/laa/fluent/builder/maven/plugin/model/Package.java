package com.github.tobi.laa.fluent.builder.maven.plugin.model;

/**
 * <p>
 * Represents the package statement to be used in the generation of source code.
 * </p>
 */
public record Package(@lombok.NonNull java.lang.Package pack) implements CodeFragment {

    /**
     * <p>
     * Generates the Java source code for this package statement.
     * </p>
     * <p> Example:
     * <blockquote><pre>
     * new Package(BigDecimal.class.getPackage()).toString()
     *     returns "package java.math;"
     * </pre></blockquote>
     * </p>
     *
     * @return The Java source code for this package statement.
     */
    @Override
    public String toString() {
        return "package " + pack.getName() + ';';
    }
}

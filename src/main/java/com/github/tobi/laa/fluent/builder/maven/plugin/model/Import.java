package com.github.tobi.laa.fluent.builder.maven.plugin.model;

/**
 * <p>
 * Represents an import statement to be used in the generation of source code.
 * </p>
 */
public record Import(@lombok.NonNull Class<?> clazz, boolean staticImport) implements CodeFragment, Comparable<Import> {

    @Override
    public int compareTo(final Import anotherFragment) {
        if (anotherFragment == null) {
            return 1;
        } else if (equals(anotherFragment)) {
            return 0;
        } else if (staticImport && !anotherFragment.staticImport) {
            return -1;
        } else if (!staticImport && anotherFragment.staticImport) {
            return 1;
        } else {
            return clazz.getName().compareTo(anotherFragment.clazz.getName());
        }
    }

    /**
     * <p>
     * Generates the Java source code for this import statement.
     * </p>
     * <p> Example:
     * <blockquote><pre>
     * new Import(BigDecimal.class).toString()
     *     returns "import java.math.BigDecimal;"
     * </pre></blockquote>
     * </p>
     *
     * @return The Java source code for this import fragment.
     */
    @Override
    public String toString() {
        final StringBuilder importCode = new StringBuilder();
        importCode.append("import ");
        if (staticImport) {
            importCode.append("static ");
        }
        importCode.append(clazz.getName()).append(';');
        return importCode.toString();
    }
}

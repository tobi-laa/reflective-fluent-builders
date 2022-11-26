package com.github.tobi.laa.fluent.builder.maven.plugin.model;

import java.util.stream.IntStream;

/**
 * <p>
 *     Some helper methods for {@link CodeFragment} implementations.
 * </p>
 */
abstract class CodeFragmentBase implements CodeFragment {

    protected String indent(final int depth) {
        if (depth < 0) {
            throw new IllegalArgumentException("Depth must not be negative.");
        }
        return "    ".repeat(depth);
    }
}

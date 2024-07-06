package io.github.tobi.laa.reflective.fluent.builders.exception;

/**
 * <p>
 * Thrown when the generation of (parts of) source code fails.
 * </p>
 */
public class CodeGenerationException extends RuntimeException {

    private static final long serialVersionUID = -3217188375956051584L;

    public CodeGenerationException(final String message) {
        super(message);
    }
}
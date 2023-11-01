package io.github.tobi.laa.reflective.fluent.builders.model.javaclass;

/**
 * <p>
 * Holds the various types a {@link JavaClass} can represent.
 * </p>
 */
public enum JavaType {

    /**
     * <p>
     * A regular Java class.
     * </p>
     */
    CLASS,

    /**
     * <p>
     * An {@code abstract} Java class.
     * </p>
     */
    ABSTRACT_CLASS,

    /**
     * <p>
     * An {@link Class#isAnonymousClass()}  anonymous} Java class.
     * </p>
     */
    ANONYMOUS_CLASS,

    /**
     * <p>
     * A {@link Class#isPrimitive() primitive} Java type.
     * </p>
     */
    PRIMITIVE,

    /**
     * <p>
     * An {@link Class#isInterface() interface}.
     * </p>
     */
    INTERFACE,

    /**
     * A Java record.
     */
    RECORD,

    /**
     * An {@link Class#isEnum() enum}.
     */
    ENUM;
}

package io.github.tobi.laa.reflective.fluent.builders.mapper.api;

import io.github.classgraph.ClassInfo;
import io.github.tobi.laa.reflective.fluent.builders.model.JavaClass;

/**
 * <p>
 * A mapper between {@code ClassGraph's} {@link ClassInfo} an this project's {@link JavaClass}.
 * </p>
 */
public interface JavaClassMapper {

    /**
     * <p>
     * Maps {@code classInfo} to an equivalent {@link JavaClass}.
     * </p>
     *
     * @param classInfo The {@link ClassInfo} to map. Must not be {@code null}.
     * @return A {@link JavaClass} equivalent to {@code classInfo}. Never {@code null}.
     */
    JavaClass map(final ClassInfo classInfo);
}

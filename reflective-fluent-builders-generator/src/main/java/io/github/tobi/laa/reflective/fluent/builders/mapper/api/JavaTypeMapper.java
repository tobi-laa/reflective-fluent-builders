package io.github.tobi.laa.reflective.fluent.builders.mapper.api;

import io.github.classgraph.ClassInfo;
import io.github.tobi.laa.reflective.fluent.builders.model.JavaType;

/**
 * <p>
 * A mapper that extracts the {@link JavaType} from {@code ClassGraph's} {@link ClassInfo}.
 * </p>
 */
public interface JavaTypeMapper {

    /**
     * <p>
     * Extracts the {@link JavaType} from {@code classInfo}.
     * </p>
     *
     * @param classInfo The {@link ClassInfo} object from which to extract the {@link JavaType}. Must not be
     *                  {@code null}.
     * @return The {@link JavaType} extracted from {@code classInfo}.
     */
    JavaType map(final ClassInfo classInfo);
}

package io.github.tobi.laa.reflective.fluent.builders.mapper.impl;

import com.google.common.collect.ImmutableSet;
import io.github.classgraph.ClassInfo;
import io.github.tobi.laa.reflective.fluent.builders.mapper.api.JavaTypeMapper;
import io.github.tobi.laa.reflective.fluent.builders.model.JavaType;

import javax.inject.Named;
import javax.inject.Singleton;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

import static io.github.tobi.laa.reflective.fluent.builders.model.JavaType.*;

/**
 * <p>
 * Standard implementation of {@link JavaTypeMapper}.
 * </p>
 */
@Singleton
@Named
class JavaTypeMapperImpl implements JavaTypeMapper {

    private final Set<String> PRIMITIVES = Stream.of( //
                    byte.class, short.class, int.class, long.class, //
                    char.class, //
                    float.class, double.class, //
                    boolean.class, //
                    void.class)
            .map(Class::getName)
            .collect(ImmutableSet.toImmutableSet());

    @Override
    public JavaType map(final ClassInfo classInfo) {
        Objects.requireNonNull(classInfo);
        if (classInfo.isInterface()) {
            return INTERFACE;
        } else if (classInfo.isAnonymousInnerClass()) {
            return ANONYMOUS_CLASS;
        } else if (classInfo.isAbstract()) {
            return ABSTRACT_CLASS;
        } else if (classInfo.isRecord()) {
            return RECORD;
        } else if (classInfo.isEnum()) {
            return ENUM;
        } else if (isPrimitive(classInfo)) {
            return PRIMITIVE;
        } else {
            return CLASS;
        }
    }

    private boolean isPrimitive(final ClassInfo classInfo) {
        return PRIMITIVES.contains(classInfo.getName());
    }
}

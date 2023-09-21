package io.github.tobi.laa.reflective.fluent.builders.generator.impl;

import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import io.github.tobi.laa.reflective.fluent.builders.generator.api.TypeNameGenerator;
import io.github.tobi.laa.reflective.fluent.builders.model.Setter;
import lombok.RequiredArgsConstructor;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.Arrays;
import java.util.Objects;

/**
 * <p>
 * Standard implementation of {@link TypeNameGenerator}.
 * </p>
 */
@Named
@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
class TypeNameGeneratorImpl implements TypeNameGenerator {

    @SuppressWarnings("java:S3252") // false positive for static method call
    @Override
    public TypeName generateTypeNameForParam(final Setter setter) {
        Objects.requireNonNull(setter);
        if (setter.getParamType() instanceof ParameterizedType) {
            final var parameterizedType = (ParameterizedType) setter.getParamType();
            final var rawType = (Class<?>) parameterizedType.getRawType();
            final Type[] typeArgs = Arrays.stream(parameterizedType.getActualTypeArguments()).map(this::wildcardToUpperBound).toArray(Type[]::new);
            return ParameterizedTypeName.get(rawType, typeArgs);
        } else {
            return TypeName.get(setter.getParamType());
        }
    }

    @Override
    public TypeName generateTypeNameForParam(final Type type) {
        Objects.requireNonNull(type);
        return TypeName.get(wildcardToUpperBound(type));

    }

    private Type wildcardToUpperBound(final Type type) {
        if (type instanceof WildcardType) {
            final Type[] upperBounds = ((WildcardType) type).getUpperBounds();
            return upperBounds.length == 0 ? Object.class : upperBounds[0];
        } else {
            return type;
        }
    }
}

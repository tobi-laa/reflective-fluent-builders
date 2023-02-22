package com.github.tobi.laa.reflective.fluent.builders.generator.impl;

import com.github.tobi.laa.reflective.fluent.builders.generator.api.TypeNameGenerator;
import com.github.tobi.laa.reflective.fluent.builders.model.CollectionSetter;
import com.github.tobi.laa.reflective.fluent.builders.model.MapSetter;
import com.github.tobi.laa.reflective.fluent.builders.model.Setter;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;

import javax.inject.Named;
import javax.inject.Singleton;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.Objects;

/**
 * <p>
 * Standard implementation of {@link TypeNameGenerator}.
 * </p>
 */
@Named
@Singleton
class TypeNameGeneratorImpl implements TypeNameGenerator {

    @SuppressWarnings("java:S3252") // false positive for static method call
    @Override
    public TypeName generateTypeNameForParam(final Setter setter) {
        Objects.requireNonNull(setter);
        if (setter instanceof CollectionSetter) {
            final CollectionSetter collectionSetter = (CollectionSetter) setter;
            return ParameterizedTypeName.get( //
                    collectionSetter.getParamType(), //
                    wildcardToUpperBound(collectionSetter.getParamTypeArg()));
        } else if (setter instanceof MapSetter) {
            final MapSetter mapSetter = (MapSetter) setter;
            return ParameterizedTypeName.get( //
                    mapSetter.getParamType(), //
                    wildcardToUpperBound(mapSetter.getKeyType()), //
                    wildcardToUpperBound(mapSetter.getValueType()));
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

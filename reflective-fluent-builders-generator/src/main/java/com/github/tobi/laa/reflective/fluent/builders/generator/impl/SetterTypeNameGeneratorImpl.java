package com.github.tobi.laa.reflective.fluent.builders.generator.impl;

import com.github.tobi.laa.reflective.fluent.builders.generator.api.SetterTypeNameGenerator;
import com.github.tobi.laa.reflective.fluent.builders.model.CollectionSetter;
import com.github.tobi.laa.reflective.fluent.builders.model.MapSetter;
import com.github.tobi.laa.reflective.fluent.builders.model.Setter;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;

import java.util.Objects;

/**
 * <p>
 * Standard implementation of {@link SetterTypeNameGenerator}.
 * </p>
 */
public class SetterTypeNameGeneratorImpl implements SetterTypeNameGenerator {

    @Override
    public TypeName generateTypeNameForParam(final Setter setter) {
        Objects.requireNonNull(setter);
        if (setter instanceof CollectionSetter collectionSetter) {
            return ParameterizedTypeName.get(collectionSetter.getParamType(), collectionSetter.getParamTypeArg());
        } else if (setter instanceof MapSetter mapSetter) {
            return ParameterizedTypeName.get(mapSetter.getParamType(), mapSetter.getKeyType(), mapSetter.getValueType());
        } else {
            return TypeName.get(setter.getParamType());
        }
    }
}

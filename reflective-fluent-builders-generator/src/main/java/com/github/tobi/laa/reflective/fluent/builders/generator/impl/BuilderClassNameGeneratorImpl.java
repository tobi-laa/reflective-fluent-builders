package com.github.tobi.laa.reflective.fluent.builders.generator.impl;

import com.github.tobi.laa.reflective.fluent.builders.generator.api.BuilderClassNameGenerator;
import com.github.tobi.laa.reflective.fluent.builders.model.BuilderMetadata;
import com.squareup.javapoet.ClassName;

import java.util.Objects;

/**
 * <p>
 * Standard implementation of {@link BuilderClassNameGenerator}.
 * </p>
 */
public class BuilderClassNameGeneratorImpl implements BuilderClassNameGenerator {

    @Override
    public ClassName generateClassName(final BuilderMetadata builderMetadata) {
        Objects.requireNonNull(builderMetadata);
        return ClassName.get(builderMetadata.getPackageName(), builderMetadata.getName());
    }
}

package io.github.tobi.laa.reflective.fluent.builders.generator.impl;

import com.squareup.javapoet.ClassName;
import io.github.tobi.laa.reflective.fluent.builders.generator.api.BuilderClassNameGenerator;
import io.github.tobi.laa.reflective.fluent.builders.model.BuilderMetadata;

import javax.inject.Named;
import javax.inject.Singleton;
import java.util.Objects;

/**
 * <p>
 * Standard implementation of {@link BuilderClassNameGenerator}.
 * </p>
 */
@Named
@Singleton
class BuilderClassNameGeneratorImpl implements BuilderClassNameGenerator {

    @Override
    public ClassName generateClassName(final BuilderMetadata builderMetadata) {
        Objects.requireNonNull(builderMetadata);
        return ClassName.get(builderMetadata.getPackageName(), builderMetadata.getName());
    }
}

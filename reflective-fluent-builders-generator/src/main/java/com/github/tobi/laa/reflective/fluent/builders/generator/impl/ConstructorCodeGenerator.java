package com.github.tobi.laa.reflective.fluent.builders.generator.impl;

import com.github.tobi.laa.reflective.fluent.builders.generator.api.BuilderClassNameGenerator;
import com.github.tobi.laa.reflective.fluent.builders.generator.api.MethodCodeGenerator;
import com.github.tobi.laa.reflective.fluent.builders.model.BuilderMetadata;
import com.squareup.javapoet.MethodSpec;
import lombok.RequiredArgsConstructor;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.lang.model.element.Modifier;
import java.util.Objects;
import java.util.Optional;

import static com.github.tobi.laa.reflective.fluent.builders.constants.BuilderConstants.OBJECT_TO_BUILD_FIELD_NAME;

/**
 * <p>
 * Implementation of {@link MethodCodeGenerator} for generating the default constructor.
 * </p>
 */
@Named
@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
class ConstructorCodeGenerator implements MethodCodeGenerator {

    @lombok.NonNull
    private final BuilderClassNameGenerator builderClassNameGenerator;

    @Override
    public Optional<MethodSpec> generate(final BuilderMetadata builderMetadata) {
        Objects.requireNonNull(builderMetadata);
        final var builderClassName = builderClassNameGenerator.generateClassName(builderMetadata);
        return Optional.of(MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PRIVATE)
                .addParameter(builderMetadata.getBuiltType().getType(), OBJECT_TO_BUILD_FIELD_NAME, Modifier.FINAL)
                .addStatement("this.$1L = $1L", OBJECT_TO_BUILD_FIELD_NAME)
                .build());
    }
}

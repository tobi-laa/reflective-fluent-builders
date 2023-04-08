package io.github.tobi.laa.reflective.fluent.builders.generator.impl;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import io.github.tobi.laa.reflective.fluent.builders.generator.api.BuilderClassNameGenerator;
import io.github.tobi.laa.reflective.fluent.builders.generator.api.MethodCodeGenerator;
import io.github.tobi.laa.reflective.fluent.builders.model.BuilderMetadata;
import lombok.RequiredArgsConstructor;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.lang.model.element.Modifier;
import java.util.Objects;
import java.util.Optional;

/**
 * <p>
 * Generates a {@code thatModifies} factory method for a builder in cases where the object to be built has an accessible
 * no-args constructor.
 * </p>
 */
@Named
@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
class ThatModifiesFactoryMethodCodeGenerator implements MethodCodeGenerator {

    @lombok.NonNull
    private final BuilderClassNameGenerator builderClassNameGenerator;

    @Override
    public Optional<MethodSpec> generate(final BuilderMetadata builderMetadata) {
        Objects.requireNonNull(builderMetadata);
        final ClassName builderClassName = builderClassNameGenerator.generateClassName(builderMetadata);
        return Optional.of(MethodSpec.methodBuilder("thatModifies")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(builderClassName)
                .addParameter(builderMetadata.getBuiltType().getType(), "objectToModify", Modifier.FINAL)
                .addStatement("$T.requireNonNull(objectToModify)", Objects.class)
                .addStatement("return new $T(objectToModify)", builderClassName)
                .build());
    }
}

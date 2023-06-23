package io.github.tobi.laa.reflective.fluent.builders.generator.impl;

import com.squareup.javapoet.MethodSpec;
import io.github.tobi.laa.reflective.fluent.builders.generator.api.MethodCodeGenerator;
import io.github.tobi.laa.reflective.fluent.builders.model.BuilderMetadata;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.lang.model.element.Modifier;
import java.util.Objects;
import java.util.Optional;

import static io.github.tobi.laa.reflective.fluent.builders.constants.BuilderConstants.OBJECT_TO_BUILD_FIELD_NAME;

/**
 * <p>
 * Implementation of {@link MethodCodeGenerator} for generating the default constructor.
 * </p>
 */
@Named
@Singleton
class ConstructorCodeGenerator implements MethodCodeGenerator {

    @Override
    public Optional<MethodSpec> generate(final BuilderMetadata builderMetadata) {
        Objects.requireNonNull(builderMetadata);
        return Optional.of(MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PRIVATE)
                .addParameter(builderMetadata.getBuiltType().getType(), OBJECT_TO_BUILD_FIELD_NAME, Modifier.FINAL)
                .addStatement("this.$1L = $1L", OBJECT_TO_BUILD_FIELD_NAME)
                .build());
    }
}

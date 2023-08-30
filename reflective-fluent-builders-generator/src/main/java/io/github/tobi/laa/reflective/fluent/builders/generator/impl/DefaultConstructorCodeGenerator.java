package io.github.tobi.laa.reflective.fluent.builders.generator.impl;

import com.squareup.javapoet.MethodSpec;
import io.github.tobi.laa.reflective.fluent.builders.generator.api.MethodCodeGenerator;
import io.github.tobi.laa.reflective.fluent.builders.model.BuilderMetadata;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.lang.model.element.Modifier;
import java.util.Objects;
import java.util.Optional;

/**
 * <p>
 * Implementation of {@link MethodCodeGenerator} for generating a default (i.e. parameterless) constructor if possible.
 * </p>
 */
@Named
@Singleton
class DefaultConstructorCodeGenerator implements MethodCodeGenerator {

    @Override
    public Optional<MethodSpec> generate(final BuilderMetadata builderMetadata) {
        Objects.requireNonNull(builderMetadata);
        if (builderMetadata.getBuiltType().isAccessibleNonArgsConstructor()) {
            return Optional.of(MethodSpec.constructorBuilder() //
                    .addModifiers(Modifier.PROTECTED) //
                    .addComment("noop") //
                    .build());
        } else {
            return Optional.empty();
        }
    }
}

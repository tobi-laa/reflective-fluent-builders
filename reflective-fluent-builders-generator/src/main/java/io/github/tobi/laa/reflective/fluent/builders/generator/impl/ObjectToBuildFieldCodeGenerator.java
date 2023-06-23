package io.github.tobi.laa.reflective.fluent.builders.generator.impl;

import com.squareup.javapoet.FieldSpec;
import io.github.tobi.laa.reflective.fluent.builders.generator.api.FieldCodeGenerator;
import io.github.tobi.laa.reflective.fluent.builders.model.BuilderMetadata;

import javax.inject.Named;
import javax.inject.Singleton;
import java.util.Objects;

import static io.github.tobi.laa.reflective.fluent.builders.constants.BuilderConstants.OBJECT_TO_BUILD_FIELD_NAME;
import static javax.lang.model.element.Modifier.PRIVATE;

/**
 * <p>
 * Generates the field {@link }
 * </p>
 */
@Singleton
@Named
class ObjectToBuildFieldCodeGenerator implements FieldCodeGenerator {

    @Override
    public FieldSpec generate(final BuilderMetadata builderMetadata) {
        Objects.requireNonNull(builderMetadata);
        return FieldSpec.builder(builderMetadata.getBuiltType().getType(), OBJECT_TO_BUILD_FIELD_NAME, PRIVATE)
                .build();
    }
}

package io.github.tobi.laa.reflective.fluent.builders.generator.impl;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.FieldSpec;
import io.github.tobi.laa.reflective.fluent.builders.generator.api.FieldCodeGenerator;
import io.github.tobi.laa.reflective.fluent.builders.model.BuilderMetadata;

import javax.inject.Named;
import javax.inject.Singleton;

import static io.github.tobi.laa.reflective.fluent.builders.constants.BuilderConstants.GENERATED_BUILDER_MARKER_FIELD_NAME;
import static javax.lang.model.element.Modifier.PRIVATE;

/**
 * <p>
 * Generates an unused field with an unusual name that is solely used to be able to detect generated builders via reflection at a later stage.
 * </p>
 */
@Named
@Singleton
class GeneratedBuilderMarkerFieldCodeGenerator implements FieldCodeGenerator {
    @Override
    public FieldSpec generate(final BuilderMetadata builderMetadata) {
        return FieldSpec.builder(boolean.class, GENERATED_BUILDER_MARKER_FIELD_NAME, PRIVATE)
                .addAnnotation(AnnotationSpec.builder(SuppressWarnings.class)
                        .addMember("value", "$S", "unused")
                        .build())
                .addJavadoc("This field is solely used to be able to detect generated builders via reflection at a later stage.")
                .build();
    }
}

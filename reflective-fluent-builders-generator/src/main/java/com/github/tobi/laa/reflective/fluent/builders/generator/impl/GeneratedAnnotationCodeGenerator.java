package com.github.tobi.laa.reflective.fluent.builders.generator.impl;

import com.github.tobi.laa.reflective.fluent.builders.generator.api.AnnotationCodeGenerator;
import com.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator;
import com.github.tobi.laa.reflective.fluent.builders.model.BuilderMetadata;
import com.squareup.javapoet.AnnotationSpec;
import lombok.RequiredArgsConstructor;

import javax.annotation.Generated;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.time.Clock;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * <p>
 * Implementation of {@link AnnotationCodeGenerator} that generates a {@link Generated} annotation.
 * </p>
 */
@Singleton
@Named
@RequiredArgsConstructor(onConstructor = @__(@Inject))
class GeneratedAnnotationCodeGenerator implements AnnotationCodeGenerator {

    @lombok.NonNull
    private final Clock clock;

    @Override
    public AnnotationSpec generate(final BuilderMetadata builderMetadata) {
        Objects.requireNonNull(builderMetadata);
        return AnnotationSpec.builder(Generated.class)
                .addMember("value", "$S", JavaFileGenerator.class.getName())
                .addMember("date", "$S", ZonedDateTime.now(clock).toString())
                .build();
    }
}

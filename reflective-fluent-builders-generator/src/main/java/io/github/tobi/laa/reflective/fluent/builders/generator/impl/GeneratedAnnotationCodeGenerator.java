package io.github.tobi.laa.reflective.fluent.builders.generator.impl;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import io.github.tobi.laa.reflective.fluent.builders.generator.api.AnnotationCodeGenerator;
import io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator;
import io.github.tobi.laa.reflective.fluent.builders.model.BuilderMetadata;
import lombok.RequiredArgsConstructor;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.time.Clock;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * <p>
 * Implementation of {@link AnnotationCodeGenerator} that generates a {@code Generated} annotation.
 * </p>
 */
@Singleton
@Named
@RequiredArgsConstructor(onConstructor_ = @Inject)
class GeneratedAnnotationCodeGenerator implements AnnotationCodeGenerator {

    @lombok.NonNull
    private final Clock clock;

    @Override
    public AnnotationSpec generate(final BuilderMetadata builderMetadata) {
        Objects.requireNonNull(builderMetadata);
        return AnnotationSpec.builder(ClassName.get("javax.annotation", "Generated"))
                .addMember("value", "$S", JavaFileGenerator.class.getName())
                .addMember("date", "$S", ZonedDateTime.now(clock).toString())
                .build();
    }
}

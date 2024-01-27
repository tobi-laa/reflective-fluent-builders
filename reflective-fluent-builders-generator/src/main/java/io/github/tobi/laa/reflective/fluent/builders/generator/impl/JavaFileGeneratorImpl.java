package io.github.tobi.laa.reflective.fluent.builders.generator.impl;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;
import io.github.tobi.laa.reflective.fluent.builders.generator.api.BuilderClassCodeGenerator;
import io.github.tobi.laa.reflective.fluent.builders.generator.api.BuilderClassNameGenerator;
import io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator;
import io.github.tobi.laa.reflective.fluent.builders.model.BuilderMetadata;
import lombok.RequiredArgsConstructor;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import java.util.Objects;

/**
 * <p>
 * Standard implementation of {@link io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator}.
 * </p>
 */
@Singleton
@Named
@RequiredArgsConstructor(onConstructor_ = @Inject)
class JavaFileGeneratorImpl implements JavaFileGenerator {

    @lombok.NonNull
    private final BuilderClassNameGenerator builderClassNameGenerator;

    @lombok.NonNull
    private final BuilderClassCodeGenerator builderClassCodeGenerator;

    @Override
    public JavaFile generateJavaFile(final BuilderMetadata builderMetadata) {
        Objects.requireNonNull(builderMetadata);
        final ClassName builderClassName = builderClassNameGenerator.generateClassName(builderMetadata);
        final TypeSpec builderTypeSpec = builderClassCodeGenerator.generateBuilderClass(builderMetadata);
        return generateJavaFile(
                builderClassName,
                builderTypeSpec);
    }

    private static JavaFile generateJavaFile(final ClassName builderClassName, final TypeSpec builderTypeSpec) {
        return JavaFile.builder(builderClassName.packageName(), builderTypeSpec).build();
    }
}

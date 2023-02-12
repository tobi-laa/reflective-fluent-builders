package com.github.tobi.laa.reflective.fluent.builders.generator.impl;

import com.github.tobi.laa.reflective.fluent.builders.generator.api.*;
import com.github.tobi.laa.reflective.fluent.builders.model.BuilderMetadata;
import com.github.tobi.laa.reflective.fluent.builders.model.Setter;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeSpec;
import lombok.RequiredArgsConstructor;

import javax.annotation.processing.Generated;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.lang.model.element.Modifier;
import java.time.Clock;
import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.Set;

/**
 * <p>
 * Standard implementation of {@link JavaFileGenerator}.
 * </p>
 */
@Singleton
@Named
@RequiredArgsConstructor(onConstructor = @__(@Inject))
class JavaFileGeneratorImpl implements JavaFileGenerator {

    @lombok.NonNull
    private final Clock clock;

    @lombok.NonNull
    private final BuilderClassNameGenerator builderClassNameGenerator;

    @lombok.NonNull
    private final Set<EncapsulatingClassCodeGenerator> encapsulatingClassCodeGenerators;

    @lombok.NonNull
    private final Set<CollectionClassCodeGenerator> collectionClassCodeGenerators;

    @lombok.NonNull
    private final SetterCodeGenerator setterCodeGenerator;

    @lombok.NonNull
    private final BuildMethodCodeGenerator buildMethodCodeGenerator;

    @Override
    public JavaFile generateJavaFile(final BuilderMetadata builderMetadata) {
        Objects.requireNonNull(builderMetadata);
        final var builderClassName = builderClassNameGenerator.generateClassName(builderMetadata);
        final var builderTypeSpec = generateModifiersAndAnnotations(builderClassName);
        generateEncapsulatingClasses(builderMetadata, builderTypeSpec);
        generateCollectionClasses(builderMetadata, builderTypeSpec);
        generateSetters(builderMetadata, builderTypeSpec);
        generateBuildMethod(builderMetadata, builderTypeSpec);
        return generateJavaFile(builderClassName, builderTypeSpec);
    }

    private TypeSpec.Builder generateModifiersAndAnnotations(ClassName builderClassName) {
        return TypeSpec.classBuilder(builderClassName)
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(AnnotationSpec.builder(Generated.class)
                        .addMember("value", "$S", getClass().getName())
                        .addMember("date", "$S", ZonedDateTime.now(clock).toString())
                        .build());
    }

    private void generateEncapsulatingClasses(BuilderMetadata builderMetadata, TypeSpec.Builder builderTypeSpec) {
        for (final EncapsulatingClassCodeGenerator generator : encapsulatingClassCodeGenerators) {
            final var encapsulatingClassSpec = generator.generate(builderMetadata);
            builderTypeSpec.addField(encapsulatingClassSpec.getField());
            builderTypeSpec.addType(encapsulatingClassSpec.getInnerClass());
        }
    }

    private void generateCollectionClasses(BuilderMetadata builderMetadata, TypeSpec.Builder builderTypeSpec) {
        for (final CollectionClassCodeGenerator generator : collectionClassCodeGenerators) {
            for (final Setter setter : builderMetadata.getBuiltType().getSetters()) {
                if (generator.isApplicable(setter)) {
                    final var collectionClassSpec = generator.generate(builderMetadata, setter);
                    builderTypeSpec.addMethod(collectionClassSpec.getGetter());
                    builderTypeSpec.addType(collectionClassSpec.getInnerClass());
                }
            }
        }
    }

    private void generateSetters(BuilderMetadata builderMetadata, TypeSpec.Builder builderTypeSpec) {
        for (final Setter setter : builderMetadata.getBuiltType().getSetters()) {
            builderTypeSpec.addMethod(setterCodeGenerator.generate(builderMetadata, setter));
        }
    }

    private void generateBuildMethod(BuilderMetadata builderMetadata, TypeSpec.Builder builderTypeSpec) {
        builderTypeSpec.addMethod(buildMethodCodeGenerator.generateBuildMethod(builderMetadata));
    }

    private static JavaFile generateJavaFile(ClassName builderClassName, TypeSpec.Builder builderTypeSpec) {
        return JavaFile.builder(builderClassName.packageName(), builderTypeSpec.build()).build();
    }
}

package com.github.tobi.laa.reflective.fluent.builders.generator.impl;

import com.github.tobi.laa.reflective.fluent.builders.generator.api.*;
import com.github.tobi.laa.reflective.fluent.builders.model.BuilderMetadata;
import com.github.tobi.laa.reflective.fluent.builders.model.Setter;
import com.squareup.javapoet.*;

import javax.annotation.processing.Generated;
import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.lang.model.element.Modifier;
import java.time.Clock;
import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.Objects;
import java.util.Set;
import java.util.SortedSet;

import static com.github.tobi.laa.reflective.fluent.builders.constants.BuilderConstants.OBJECT_TO_BUILD_FIELD_NAME;
import static com.google.common.collect.ImmutableSortedSet.copyOf;

/**
 * <p>
 * Standard implementation of {@link JavaFileGenerator}.
 * </p>
 */
@Singleton
@Named
class JavaFileGeneratorImpl implements JavaFileGenerator {

    @Inject
    @SuppressWarnings("unused")
    JavaFileGeneratorImpl( //
                           final Clock clock, //
                           final BuilderClassNameGenerator builderClassNameGenerator, //
                           final Set<EncapsulatingClassCodeGenerator> encapsulatingClassCodeGenerators, //
                           final Set<CollectionClassCodeGenerator> collectionClassCodeGenerators, //
                           final SetterCodeGenerator setterCodeGenerator, //
                           final BuildMethodCodeGenerator buildMethodCodeGenerator) {

        this.clock = Objects.requireNonNull(clock);
        this.builderClassNameGenerator = Objects.requireNonNull(builderClassNameGenerator);
        this.setterCodeGenerator = Objects.requireNonNull(setterCodeGenerator);
        this.buildMethodCodeGenerator = Objects.requireNonNull(buildMethodCodeGenerator);
        Objects.requireNonNull(encapsulatingClassCodeGenerators);
        Objects.requireNonNull(collectionClassCodeGenerators);
        // to ensure deterministic outputs, sets are sorted on construction
        final var compareByClassName = Comparator.comparing(o -> o.getClass().getName());
        this.encapsulatingClassCodeGenerators = copyOf(compareByClassName, encapsulatingClassCodeGenerators);
        this.collectionClassCodeGenerators = copyOf(compareByClassName, collectionClassCodeGenerators);
    }

    @lombok.NonNull
    private final Clock clock;

    @lombok.NonNull
    private final BuilderClassNameGenerator builderClassNameGenerator;

    @lombok.NonNull
    private final SortedSet<EncapsulatingClassCodeGenerator> encapsulatingClassCodeGenerators;

    @lombok.NonNull
    private final SortedSet<CollectionClassCodeGenerator> collectionClassCodeGenerators;

    @lombok.NonNull
    private final SetterCodeGenerator setterCodeGenerator;

    @lombok.NonNull
    private final BuildMethodCodeGenerator buildMethodCodeGenerator;

    @Override
    public JavaFile generateJavaFile(final BuilderMetadata builderMetadata) {
        Objects.requireNonNull(builderMetadata);
        final var builderClassName = builderClassNameGenerator.generateClassName(builderMetadata);
        final var builderTypeSpec = generateModifiersAndAnnotations(builderClassName);
        builderTypeSpec.addField(
                FieldSpec.builder(builderMetadata.getBuiltType().getType(), OBJECT_TO_BUILD_FIELD_NAME, Modifier.PRIVATE)
                        .build());
        builderTypeSpec.addMethod(MethodSpec
                .constructorBuilder()
                .addModifiers(Modifier.PRIVATE)
                .addParameter(builderMetadata.getBuiltType().getType(), OBJECT_TO_BUILD_FIELD_NAME, Modifier.FINAL)
                .addStatement("this.$L = $L", OBJECT_TO_BUILD_FIELD_NAME, OBJECT_TO_BUILD_FIELD_NAME)
                .build());
        builderTypeSpec.addMethod(MethodSpec
                .methodBuilder("newInstance")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(builderClassName)
                .addStatement("return new $T(null)", builderClassName)
                .build());
        builderTypeSpec.addMethod(MethodSpec
                .methodBuilder("from")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(builderClassName)
                .addParameter(builderMetadata.getBuiltType().getType(), "objectToModify", Modifier.FINAL)
                .addStatement("$T.requireNonNull(objectToModify)", Objects.class)
                .addStatement("return new $T(objectToModify)", builderClassName)
                .build());
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

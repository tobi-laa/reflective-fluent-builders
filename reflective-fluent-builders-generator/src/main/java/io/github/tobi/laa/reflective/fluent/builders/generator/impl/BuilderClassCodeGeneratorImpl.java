package io.github.tobi.laa.reflective.fluent.builders.generator.impl;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeVariableName;
import io.github.tobi.laa.reflective.fluent.builders.generator.api.*;
import io.github.tobi.laa.reflective.fluent.builders.model.BuilderMetadata;
import io.github.tobi.laa.reflective.fluent.builders.model.WriteAccessor;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.lang.model.element.Modifier;
import java.util.Comparator;
import java.util.Objects;
import java.util.Set;
import java.util.SortedSet;

import static com.google.common.collect.ImmutableSortedSet.copyOf;

/**
 * <p>
 * Standard implementation of {@link BuilderClassCodeGenerator}.
 * </p>
 */
@Singleton
@Named
class BuilderClassCodeGeneratorImpl implements BuilderClassCodeGenerator {

    @lombok.NonNull
    private final BuilderClassNameGenerator builderClassNameGenerator;

    @lombok.NonNull
    private final SortedSet<AnnotationCodeGenerator> annotationCodeGenerators;

    @lombok.NonNull
    private final SortedSet<FieldCodeGenerator> fieldCodeGenerators;

    @lombok.NonNull
    private final SortedSet<MethodCodeGenerator> methodCodeGenerators;

    @lombok.NonNull
    private final SortedSet<EncapsulatingClassCodeGenerator> encapsulatingClassCodeGenerators;

    @lombok.NonNull
    private final SortedSet<CollectionClassCodeGenerator> collectionClassCodeGenerators;

    @lombok.NonNull
    private final SetterCodeGenerator setterCodeGenerator;

    @lombok.NonNull
    private final BuildMethodCodeGenerator buildMethodCodeGenerator;

    @Inject
    @SuppressWarnings("unused")
    BuilderClassCodeGeneratorImpl( //
                                   final BuilderClassNameGenerator builderClassNameGenerator, //
                                   final Set<AnnotationCodeGenerator> annotationCodeGenerators, //
                                   final Set<FieldCodeGenerator> fieldCodeGenerators, //
                                   final Set<MethodCodeGenerator> methodCodeGenerators, //
                                   final Set<EncapsulatingClassCodeGenerator> encapsulatingClassCodeGenerators, //
                                   final Set<CollectionClassCodeGenerator> collectionClassCodeGenerators, //
                                   final SetterCodeGenerator setterCodeGenerator, //
                                   final BuildMethodCodeGenerator buildMethodCodeGenerator) {

        this.builderClassNameGenerator = Objects.requireNonNull(builderClassNameGenerator);
        this.setterCodeGenerator = Objects.requireNonNull(setterCodeGenerator);
        this.buildMethodCodeGenerator = Objects.requireNonNull(buildMethodCodeGenerator);
        Objects.requireNonNull(annotationCodeGenerators);
        Objects.requireNonNull(fieldCodeGenerators);
        Objects.requireNonNull(methodCodeGenerators);
        Objects.requireNonNull(encapsulatingClassCodeGenerators);
        Objects.requireNonNull(collectionClassCodeGenerators);
        // to ensure deterministic outputs, sets are sorted on construction
        final var compareByClassName = Comparator.comparing(o -> o.getClass().getName());
        this.annotationCodeGenerators = copyOf(compareByClassName, annotationCodeGenerators);
        this.fieldCodeGenerators = copyOf(compareByClassName, fieldCodeGenerators);
        this.methodCodeGenerators = copyOf(compareByClassName, methodCodeGenerators);
        this.encapsulatingClassCodeGenerators = copyOf(compareByClassName, encapsulatingClassCodeGenerators);
        this.collectionClassCodeGenerators = copyOf(compareByClassName, collectionClassCodeGenerators);
    }

    @Override
    public TypeSpec generateBuilderClass(final BuilderMetadata builderMetadata) {
        Objects.requireNonNull(builderMetadata);
        final var builderClassName = builderClassNameGenerator.generateClassName(builderMetadata);
        return generateBuilderClass(builderMetadata, builderClassName);
    }

    private TypeSpec generateBuilderClass(final BuilderMetadata builderMetadata, final ClassName builderClassName) {
        final var builderTypeSpec = generateTypeSpec(builderMetadata, builderClassName);
        generateAnnotations(builderMetadata, builderTypeSpec);
        generateFields(builderMetadata, builderTypeSpec);
        generateConstructorsAndMethods(builderMetadata, builderTypeSpec);
        generateEncapsulatingClasses(builderMetadata, builderTypeSpec);
        generateCollectionClasses(builderMetadata, builderTypeSpec);
        generateSetters(builderMetadata, builderTypeSpec);
        generateBuildMethod(builderMetadata, builderTypeSpec);
        generateNestedBuilders(builderMetadata, builderTypeSpec, builderClassName);
        return builderTypeSpec.build();
    }

    private TypeSpec.Builder generateTypeSpec(final BuilderMetadata builderMetadata, final ClassName builderClassName) {
        final var builder = TypeSpec.classBuilder(builderClassName).addModifiers(Modifier.PUBLIC);
        if (builderMetadata.getEnclosingBuilder().isPresent()) {
            builder.addModifiers(Modifier.STATIC);
        }
        for (final var typeParam : builderMetadata.getBuiltType().getType().loadClass().getTypeParameters()) {
            builder.addTypeVariable(TypeVariableName.get(typeParam));
        }
        return builder;
    }

    private void generateAnnotations(final BuilderMetadata builderMetadata, final TypeSpec.Builder builderTypeSpec) {
        for (final AnnotationCodeGenerator generator : annotationCodeGenerators) {
            builderTypeSpec.addAnnotation(generator.generate(builderMetadata));
        }
    }

    private void generateFields(final BuilderMetadata builderMetadata, final TypeSpec.Builder builderTypeSpec) {
        for (final FieldCodeGenerator generator : fieldCodeGenerators) {
            builderTypeSpec.addField(generator.generate(builderMetadata));
        }
    }

    private void generateConstructorsAndMethods(final BuilderMetadata builderMetadata, final TypeSpec.Builder builderTypeSpec) {
        for (final MethodCodeGenerator generator : methodCodeGenerators) {
            generator.generate(builderMetadata).ifPresent(builderTypeSpec::addMethod);
        }
    }

    private void generateEncapsulatingClasses(final BuilderMetadata builderMetadata, final TypeSpec.Builder builderTypeSpec) {
        for (final EncapsulatingClassCodeGenerator generator : encapsulatingClassCodeGenerators) {
            final var encapsulatingClassSpec = generator.generate(builderMetadata);
            builderTypeSpec.addField(encapsulatingClassSpec.getField());
            builderTypeSpec.addType(encapsulatingClassSpec.getInnerClass());
        }
    }

    private void generateCollectionClasses(final BuilderMetadata builderMetadata, final TypeSpec.Builder builderTypeSpec) {
        for (final CollectionClassCodeGenerator generator : collectionClassCodeGenerators) {
            for (final WriteAccessor writeAccessor : builderMetadata.getBuiltType().getWriteAccessors()) {
                if (generator.isApplicable(writeAccessor)) {
                    final var collectionClassSpec = generator.generate(builderMetadata, writeAccessor);
                    builderTypeSpec.addMethod(collectionClassSpec.getGetter());
                    builderTypeSpec.addType(collectionClassSpec.getInnerClass());
                }
            }
        }
    }

    private void generateSetters(final BuilderMetadata builderMetadata, final TypeSpec.Builder builderTypeSpec) {
        for (final WriteAccessor writeAccessor : builderMetadata.getBuiltType().getWriteAccessors()) {
            builderTypeSpec.addMethod(setterCodeGenerator.generate(builderMetadata, writeAccessor));
        }
    }

    private void generateBuildMethod(final BuilderMetadata builderMetadata, final TypeSpec.Builder builderTypeSpec) {
        builderTypeSpec.addMethod(buildMethodCodeGenerator.generateBuildMethod(builderMetadata));
    }

    private void generateNestedBuilders(final BuilderMetadata builderMetadata, final TypeSpec.Builder builderTypeSpec, final ClassName builderClassName) {
        builderMetadata.getNestedBuilders()
                .forEach(nestedBuilder -> builderTypeSpec.addType(generateBuilderClass(nestedBuilder, builderClassName.nestedClass(nestedBuilder.getName()))));
    }
}

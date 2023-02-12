package com.github.tobi.laa.reflective.fluent.builders.generator.impl;

import com.github.tobi.laa.reflective.fluent.builders.constants.BuilderConstants.FieldValue;
import com.github.tobi.laa.reflective.fluent.builders.generator.api.BuilderClassNameGenerator;
import com.github.tobi.laa.reflective.fluent.builders.generator.api.EncapsulatingClassCodeGenerator;
import com.github.tobi.laa.reflective.fluent.builders.generator.api.SetterTypeNameGenerator;
import com.github.tobi.laa.reflective.fluent.builders.generator.model.EncapsulatingClassSpec;
import com.github.tobi.laa.reflective.fluent.builders.model.BuilderMetadata;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.TypeSpec;
import lombok.RequiredArgsConstructor;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.lang.model.element.Modifier;
import java.util.Objects;

import static javax.lang.model.element.Modifier.FINAL;
import static javax.lang.model.element.Modifier.PRIVATE;

/**
 * <p>
 * Generates a {@link BuilderMetadata builder's} inner class {@link FieldValue} which will hold all the actual field
 * values that will be used for building the resulting object.
 * </p>
 */
@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class InnerClassFieldValueCodeGenerator implements EncapsulatingClassCodeGenerator {

    @lombok.NonNull
    private final BuilderClassNameGenerator builderClassNameGenerator;

    @lombok.NonNull
    private final SetterTypeNameGenerator setterTypeNameGenerator;

    /**
     * <p>
     * Generates the inner class {@link FieldValue} for {@code builderMetadata} which will hold all the actual field
     * values that will be used for building the resulting object.
     * </p>
     *
     * @param builderMetadata The metadata of the builder for which to generate the {@link FieldValue} class. Must not
     *                        be {@code null}.
     * @return The generated {@link FieldValue} class.
     */
    @Override
    public EncapsulatingClassSpec generate(final BuilderMetadata builderMetadata) {
        Objects.requireNonNull(builderMetadata);
        final var builderClassName = builderClassNameGenerator.generateClassName(builderMetadata);
        final var fieldValue = builderClassName.nestedClass(FieldValue.CLASS_NAME);
        final var fields = builderMetadata.getBuiltType().getSetters() //
                .stream() //
                .map(setter -> FieldSpec //
                        .builder(setterTypeNameGenerator.generateTypeNameForParam(setter), setter.getParamName()) //
                        .build()) //
                .toList();
        return EncapsulatingClassSpec.builder() //
                .field(FieldSpec //
                        .builder(fieldValue, FieldValue.FIELD_NAME, PRIVATE, FINAL) //
                        .initializer("new $T()", fieldValue) //
                        .build()) //
                .innerClass(TypeSpec //
                        .classBuilder(fieldValue) //
                        .addModifiers(Modifier.PRIVATE) //
                        .addFields(fields) //
                        .build()) //
                .build();
    }
}

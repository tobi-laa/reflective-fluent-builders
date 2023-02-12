package com.github.tobi.laa.reflective.fluent.builders.generator.impl;

import com.github.tobi.laa.reflective.fluent.builders.constants.BuilderConstants.CallSetterFor;
import com.github.tobi.laa.reflective.fluent.builders.generator.api.BuilderClassNameGenerator;
import com.github.tobi.laa.reflective.fluent.builders.generator.api.EncapsulatingClassCodeGenerator;
import com.github.tobi.laa.reflective.fluent.builders.generator.model.EncapsulatingClassSpec;
import com.github.tobi.laa.reflective.fluent.builders.model.BuilderMetadata;
import com.github.tobi.laa.reflective.fluent.builders.model.Setter;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.TypeSpec;
import lombok.RequiredArgsConstructor;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.lang.model.element.Modifier;
import java.util.Objects;
import java.util.stream.Collectors;

import static javax.lang.model.element.Modifier.FINAL;
import static javax.lang.model.element.Modifier.PRIVATE;

/**
 * <p>
 * Generates a {@link BuilderMetadata builder's} inner class {@link CallSetterFor} which will hold flags for each field
 * to later determine whether the setter on the resulting object should be called.
 * </p>
 */
@Named
@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class InnerClassCallSetterForCodeGenerator implements EncapsulatingClassCodeGenerator {

    @lombok.NonNull
    private final BuilderClassNameGenerator builderClassNameGenerator;

    /**
     * <p>
     * Generates the inner class {@link CallSetterFor} for {@code builderMetadata} which will hold flags for each field
     * to later determine whether the setter on the resulting object should be called.
     * </p>
     *
     * @param builderMetadata The metadata of the builder for which to generate the {@link CallSetterFor} class. Must
     *                        not be {@code null}.
     * @return The generated {@link CallSetterFor} class.
     */
    @Override
    public EncapsulatingClassSpec generate(final BuilderMetadata builderMetadata) {
        Objects.requireNonNull(builderMetadata);
        final var builderClassName = builderClassNameGenerator.generateClassName(builderMetadata);
        final var callSetterFor = builderClassName.nestedClass(CallSetterFor.CLASS_NAME);
        final var fields = builderMetadata.getBuiltType().getSetters() //
                .stream() //
                .map(Setter::getParamName) //
                .map(paramName -> FieldSpec //
                        .builder(boolean.class, paramName) //
                        .build()) //
                .collect(Collectors.toList());
        return EncapsulatingClassSpec.builder() //
                .field(FieldSpec //
                        .builder(callSetterFor, CallSetterFor.FIELD_NAME, PRIVATE, FINAL) //
                        .initializer("new $T()", callSetterFor) //
                        .build()) //
                .innerClass(TypeSpec //
                        .classBuilder(callSetterFor) //
                        .addModifiers(Modifier.PRIVATE) //
                        .addFields(fields) //
                        .build()) //
                .build();
    }
}

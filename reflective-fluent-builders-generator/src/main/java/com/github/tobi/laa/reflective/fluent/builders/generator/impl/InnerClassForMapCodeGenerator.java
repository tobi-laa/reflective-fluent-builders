package com.github.tobi.laa.reflective.fluent.builders.generator.impl;

import com.github.tobi.laa.reflective.fluent.builders.constants.BuilderConstants.CallSetterFor;
import com.github.tobi.laa.reflective.fluent.builders.constants.BuilderConstants.FieldValue;
import com.github.tobi.laa.reflective.fluent.builders.exception.CodeGenerationException;
import com.github.tobi.laa.reflective.fluent.builders.generator.api.BuilderClassNameGenerator;
import com.github.tobi.laa.reflective.fluent.builders.generator.api.CollectionClassCodeGenerator;
import com.github.tobi.laa.reflective.fluent.builders.generator.api.MapInitializerCodeGenerator;
import com.github.tobi.laa.reflective.fluent.builders.generator.model.CollectionClassSpec;
import com.github.tobi.laa.reflective.fluent.builders.model.BuilderMetadata;
import com.github.tobi.laa.reflective.fluent.builders.model.MapSetter;
import com.github.tobi.laa.reflective.fluent.builders.model.Setter;
import com.github.tobi.laa.reflective.fluent.builders.service.api.SetterService;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import lombok.RequiredArgsConstructor;

import javax.lang.model.element.Modifier;
import java.util.List;
import java.util.Objects;

import static javax.lang.model.element.Modifier.FINAL;
import static org.apache.commons.lang3.StringUtils.capitalize;

/**
 * <p>
 * Implementation of {@link CollectionClassCodeGenerator} for generating inner classes for convenient map construction.
 * </p>
 */
@RequiredArgsConstructor
public class InnerClassForMapCodeGenerator implements CollectionClassCodeGenerator {

    @lombok.NonNull
    private final BuilderClassNameGenerator builderClassNameGenerator;

    @lombok.NonNull
    private final SetterService setterService;

    @lombok.NonNull
    private final List<MapInitializerCodeGenerator> initializerGenerators;

    @Override
    public boolean isApplicable(final Setter setter) {
        Objects.requireNonNull(setter);
        return setter instanceof final MapSetter mapSetter //
                && initializerGenerators.stream().anyMatch(gen -> gen.isApplicable(mapSetter));
    }

    @Override
    public CollectionClassSpec generate(final BuilderMetadata builderMetadata, final Setter setter) {
        Objects.requireNonNull(builderMetadata);
        Objects.requireNonNull(setter);
        if (setter instanceof final MapSetter mapSetter) {
            return generate(builderMetadata, mapSetter);
        } else {
            throw new CodeGenerationException("Generation of inner map class for " + setter + " is not supported.");
        }
    }

    private CollectionClassSpec generate(final BuilderMetadata builderMetadata, final MapSetter setter) {
        final var builderClassName = builderClassNameGenerator.generateClassName(builderMetadata);
        final var className = builderClassName.nestedClass("Map" + capitalize(setter.getParamName()));
        return CollectionClassSpec.builder() //
                .getter(MethodSpec //
                        .methodBuilder(setterService.dropSetterPrefix(setter.getMethodName())) //
                        .addModifiers(Modifier.PUBLIC) //
                        .returns(className) //
                        .addStatement("return new $T()", className) //
                        .build()) //
                .innerClass(TypeSpec //
                        .classBuilder(className) //
                        .addModifiers(Modifier.PUBLIC) //
                        .addMethod(MethodSpec.methodBuilder("put") //
                                .addModifiers(Modifier.PUBLIC) //
                                .addParameter(setter.getKeyType(), "key", FINAL) //
                                .addParameter(setter.getValueType(), "value", FINAL) //
                                .returns(className) //
                                .beginControlFlow("if ($T.this.$L.$L == null)", builderClassName, FieldValue.FIELD_NAME, setter.getParamName()) //
                                .addStatement(CodeBlock.builder()
                                        .add("$T.this.$L.$L = ", builderClassName, FieldValue.FIELD_NAME, setter.getParamName())
                                        .add(initializerGenerators //
                                                .stream() //
                                                .filter(gen -> gen.isApplicable(setter)) //
                                                .map(gen -> gen.generateMapInitializer(setter)) //
                                                .findFirst() //
                                                .orElseThrow(() -> new CodeGenerationException("Could not generate initializer for " + setter + '.'))) //
                                        .build()) //
                                .endControlFlow() //
                                .addStatement("$T.this.$L.$L.put($L, $L)", builderClassName, FieldValue.FIELD_NAME, setter.getParamName(), "key", "value") //
                                .addStatement("$T.this.$L.$L = $L", builderClassName, CallSetterFor.FIELD_NAME, setter.getParamName(), true) //
                                .addStatement("return this") //
                                .build()) //
                        .addMethod(MethodSpec.methodBuilder("and") //
                                .addModifiers(Modifier.PUBLIC) //
                                .returns(builderClassName) //
                                .addStatement("return $T.this", builderClassName) //
                                .build()) //
                        .build()) //
                .build();
    }
}

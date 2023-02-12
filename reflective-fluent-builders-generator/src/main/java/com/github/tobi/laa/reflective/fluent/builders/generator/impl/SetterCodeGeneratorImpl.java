package com.github.tobi.laa.reflective.fluent.builders.generator.impl;

import com.github.tobi.laa.reflective.fluent.builders.constants.BuilderConstants;
import com.github.tobi.laa.reflective.fluent.builders.generator.api.BuilderClassNameGenerator;
import com.github.tobi.laa.reflective.fluent.builders.generator.api.SetterCodeGenerator;
import com.github.tobi.laa.reflective.fluent.builders.generator.api.SetterTypeNameGenerator;
import com.github.tobi.laa.reflective.fluent.builders.model.BuilderMetadata;
import com.github.tobi.laa.reflective.fluent.builders.model.Setter;
import com.github.tobi.laa.reflective.fluent.builders.service.api.SetterService;
import com.squareup.javapoet.MethodSpec;
import lombok.RequiredArgsConstructor;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.lang.model.element.Modifier;
import java.util.Objects;

/**
 * <p>
 * Default implementation of {@link SetterCodeGenerator}.
 * </p>
 */
@Named
@Singleton
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class SetterCodeGeneratorImpl implements SetterCodeGenerator {

    @lombok.NonNull
    private final BuilderClassNameGenerator builderClassNameGenerator;

    @lombok.NonNull
    private final SetterTypeNameGenerator setterTypeNameGenerator;

    @lombok.NonNull
    private final SetterService setterService;

    @Override
    public MethodSpec generate(final BuilderMetadata builderMetadata, final Setter setter) {
        Objects.requireNonNull(builderMetadata);
        Objects.requireNonNull(setter);
        final var builderClassName = builderClassNameGenerator.generateClassName(builderMetadata);
        final var name = setterService.dropSetterPrefix(setter.getMethodName());
        return MethodSpec.methodBuilder(name)
                .addModifiers(Modifier.PUBLIC)
                .returns(builderClassName)
                .addParameter(setterTypeNameGenerator.generateTypeNameForParam(setter), setter.getParamName(), Modifier.FINAL)
                .addStatement("$L.$L = $L", BuilderConstants.FieldValue.FIELD_NAME, setter.getParamName(), setter.getParamName())
                .addStatement("$L.$L = $L", BuilderConstants.CallSetterFor.FIELD_NAME, setter.getParamName(), true)
                .addStatement("return this")
                .build();
    }
}

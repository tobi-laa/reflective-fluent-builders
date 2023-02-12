package com.github.tobi.laa.reflective.fluent.builders.generator.impl;

import com.github.tobi.laa.reflective.fluent.builders.constants.BuilderConstants.CallSetterFor;
import com.github.tobi.laa.reflective.fluent.builders.constants.BuilderConstants.FieldValue;
import com.github.tobi.laa.reflective.fluent.builders.generator.api.BuildMethodCodeGenerator;
import com.github.tobi.laa.reflective.fluent.builders.model.BuilderMetadata;
import com.github.tobi.laa.reflective.fluent.builders.model.Setter;
import com.squareup.javapoet.MethodSpec;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.lang.model.element.Modifier;
import java.util.Objects;

/**
 * <p>
 * Standard implementation of {@link BuildMethodCodeGenerator}.
 * </p>
 */
@Named
@Singleton
class BuildMethodCodeGeneratorImpl implements BuildMethodCodeGenerator {

    @Override
    public MethodSpec generateBuildMethod(final BuilderMetadata builderMetadata) {
        Objects.requireNonNull(builderMetadata);
        final var clazz = builderMetadata.getBuiltType().getType();
        final MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("build")
                .addModifiers(Modifier.PUBLIC)
                .returns(clazz)
                .addStatement("final $T result = new $T()", clazz, clazz);
        for (final Setter setter : builderMetadata.getBuiltType().getSetters()) {
            methodBuilder
                    .beginControlFlow("if ($L.$L)", CallSetterFor.FIELD_NAME, setter.getParamName())
                    .addStatement("result.$L($L.$L)", FieldValue.FIELD_NAME, setter.getMethodName(), setter.getParamName())
                    .endControlFlow();
        }
        methodBuilder.addStatement("return result");
        return methodBuilder.build();
    }
}

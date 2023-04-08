package io.github.tobi.laa.reflective.fluent.builders.generator.impl;

import io.github.tobi.laa.reflective.fluent.builders.constants.BuilderConstants.CallSetterFor;
import io.github.tobi.laa.reflective.fluent.builders.constants.BuilderConstants.FieldValue;
import io.github.tobi.laa.reflective.fluent.builders.generator.api.BuildMethodCodeGenerator;
import io.github.tobi.laa.reflective.fluent.builders.model.BuilderMetadata;
import io.github.tobi.laa.reflective.fluent.builders.model.CollectionGetAndAdder;
import io.github.tobi.laa.reflective.fluent.builders.model.Setter;
import com.squareup.javapoet.MethodSpec;

import javax.inject.Named;
import javax.inject.Singleton;
import javax.lang.model.element.Modifier;
import java.util.Objects;

import static io.github.tobi.laa.reflective.fluent.builders.constants.BuilderConstants.OBJECT_TO_BUILD_FIELD_NAME;

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
                .returns(clazz);
        if (builderMetadata.getBuiltType().isAccessibleNonArgsConstructor()) {
            methodBuilder
                    .beginControlFlow("if ($L == null)", OBJECT_TO_BUILD_FIELD_NAME)
                    .addStatement("$L = new $T()", OBJECT_TO_BUILD_FIELD_NAME, clazz)
                    .endControlFlow();
        }
        for (final Setter setter : builderMetadata.getBuiltType().getSetters()) {
            if (setter instanceof CollectionGetAndAdder) {
                methodBuilder
                        .beginControlFlow(
                                "if ($1L.$3L && $2L.$3L != null)",
                                CallSetterFor.FIELD_NAME,
                                FieldValue.FIELD_NAME,
                                setter.getParamName())
                        .addStatement(
                                "$L.$L.forEach($L.$L()::add)",
                                FieldValue.FIELD_NAME,
                                setter.getParamName(),
                                OBJECT_TO_BUILD_FIELD_NAME,
                                setter.getMethodName());
            } else {
                methodBuilder
                        .beginControlFlow("if ($L.$L)", CallSetterFor.FIELD_NAME, setter.getParamName())
                        .addStatement(
                                "$L.$L($L.$L)",
                                OBJECT_TO_BUILD_FIELD_NAME,
                                setter.getMethodName(),
                                FieldValue.FIELD_NAME,
                                setter.getParamName());
            }
            methodBuilder.endControlFlow();
        }
        methodBuilder.addStatement("return $L", OBJECT_TO_BUILD_FIELD_NAME);
        return methodBuilder.build();
    }
}

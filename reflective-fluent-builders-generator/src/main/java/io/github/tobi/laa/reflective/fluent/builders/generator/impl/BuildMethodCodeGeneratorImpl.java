package io.github.tobi.laa.reflective.fluent.builders.generator.impl;

import com.squareup.javapoet.MethodSpec;
import io.github.tobi.laa.reflective.fluent.builders.constants.BuilderConstants.CallSetterFor;
import io.github.tobi.laa.reflective.fluent.builders.constants.BuilderConstants.FieldValue;
import io.github.tobi.laa.reflective.fluent.builders.generator.api.BuildMethodCodeGenerator;
import io.github.tobi.laa.reflective.fluent.builders.model.BuilderMetadata;
import io.github.tobi.laa.reflective.fluent.builders.model.Getter;
import io.github.tobi.laa.reflective.fluent.builders.model.Setter;
import io.github.tobi.laa.reflective.fluent.builders.model.WriteAccessor;
import io.github.tobi.laa.reflective.fluent.builders.service.api.WriteAccessorService;
import lombok.RequiredArgsConstructor;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.lang.model.element.Modifier;
import java.util.Objects;

import static io.github.tobi.laa.reflective.fluent.builders.constants.BuilderConstants.OBJECT_SUPPLIER_FIELD_NAME;

/**
 * <p>
 * Standard implementation of {@link BuildMethodCodeGenerator}.
 * </p>
 */
@Named
@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
class BuildMethodCodeGeneratorImpl implements BuildMethodCodeGenerator {

    private static final String OBJECT_TO_BUILD_FIELD_NAME = "objectToBuild";

    @lombok.NonNull
    private final WriteAccessorService writeAccessorService;

    @Override
    public MethodSpec generateBuildMethod(final BuilderMetadata builderMetadata) {
        Objects.requireNonNull(builderMetadata);
        final var clazz = builderMetadata.getBuiltType().getType();
        final MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder("build")
                .addModifiers(Modifier.PUBLIC)
                .returns(clazz.loadClass());
        methodBuilder.addStatement("final $T $L = this.$L.get()", clazz.loadClass(), OBJECT_TO_BUILD_FIELD_NAME, OBJECT_SUPPLIER_FIELD_NAME);
        for (final WriteAccessor writeAccessor : builderMetadata.getBuiltType().getWriteAccessors()) {
            if (writeAccessorService.isCollectionGetter(writeAccessor)) {
                final var getter = (Getter) writeAccessor;
                methodBuilder
                        .beginControlFlow(
                                "if (this.$1L.$3L && this.$2L.$3L != null)",
                                CallSetterFor.FIELD_NAME,
                                FieldValue.FIELD_NAME,
                                getter.getPropertyName())
                        .addStatement(
                                "this.$L.$L.forEach($L.$L()::add)",
                                FieldValue.FIELD_NAME,
                                getter.getPropertyName(),
                                OBJECT_TO_BUILD_FIELD_NAME,
                                getter.getMethodName());
            } else if (writeAccessorService.isSetter(writeAccessor)) {
                final var setter = (Setter) writeAccessor;
                methodBuilder
                        .beginControlFlow("if (this.$L.$L)", CallSetterFor.FIELD_NAME, setter.getPropertyName())
                        .addStatement(
                                "$L.$L(this.$L.$L)",
                                OBJECT_TO_BUILD_FIELD_NAME,
                                setter.getMethodName(),
                                FieldValue.FIELD_NAME,
                                setter.getPropertyName());
            }
            methodBuilder.endControlFlow();
        }
        methodBuilder.addStatement("return $L", OBJECT_TO_BUILD_FIELD_NAME);
        return methodBuilder.build();
    }
}

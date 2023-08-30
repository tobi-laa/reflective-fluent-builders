package io.github.tobi.laa.reflective.fluent.builders.generator.impl;

import com.squareup.javapoet.MethodSpec;
import io.github.tobi.laa.reflective.fluent.builders.constants.BuilderConstants;
import io.github.tobi.laa.reflective.fluent.builders.generator.api.BuilderClassNameGenerator;
import io.github.tobi.laa.reflective.fluent.builders.generator.api.SetterCodeGenerator;
import io.github.tobi.laa.reflective.fluent.builders.generator.api.TypeNameGenerator;
import io.github.tobi.laa.reflective.fluent.builders.model.BuilderMetadata;
import io.github.tobi.laa.reflective.fluent.builders.model.CollectionGetAndAdder;
import io.github.tobi.laa.reflective.fluent.builders.model.Setter;
import io.github.tobi.laa.reflective.fluent.builders.service.api.SetterService;
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
@RequiredArgsConstructor(onConstructor_ = @Inject)
class SetterCodeGeneratorImpl implements SetterCodeGenerator {

    @lombok.NonNull
    private final BuilderClassNameGenerator builderClassNameGenerator;

    @lombok.NonNull
    private final TypeNameGenerator typeNameGenerator;

    @lombok.NonNull
    private final SetterService setterService;

    @Override
    public MethodSpec generate(final BuilderMetadata builderMetadata, final Setter setter) {
        Objects.requireNonNull(builderMetadata);
        Objects.requireNonNull(setter);
        final var builderClassName = builderClassNameGenerator.generateClassName(builderMetadata);
        final String name;
        if (setter instanceof CollectionGetAndAdder) {
            name = setterService.dropGetterPrefix(setter.getMethodName());
        } else {
            name = setterService.dropSetterPrefix(setter.getMethodName());
        }
        return MethodSpec.methodBuilder(name)
                .addModifiers(Modifier.PUBLIC)
                .returns(builderClassName)
                .addParameter(typeNameGenerator.generateTypeNameForParam(setter), setter.getParamName(), Modifier.FINAL)
                .addStatement("this.$1L.$2L = $2L", BuilderConstants.FieldValue.FIELD_NAME, setter.getParamName())
                .addStatement("this.$L.$L = $L", BuilderConstants.CallSetterFor.FIELD_NAME, setter.getParamName(), true)
                .addStatement("return this")
                .build();
    }
}

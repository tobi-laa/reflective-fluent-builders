package io.github.tobi.laa.reflective.fluent.builders.generator.impl;

import com.squareup.javapoet.MethodSpec;
import io.github.tobi.laa.reflective.fluent.builders.constants.BuilderConstants;
import io.github.tobi.laa.reflective.fluent.builders.generator.api.BuilderClassNameGenerator;
import io.github.tobi.laa.reflective.fluent.builders.generator.api.SetterCodeGenerator;
import io.github.tobi.laa.reflective.fluent.builders.generator.api.TypeNameGenerator;
import io.github.tobi.laa.reflective.fluent.builders.model.BuilderMetadata;
import io.github.tobi.laa.reflective.fluent.builders.model.CollectionGetAndAdder;
import io.github.tobi.laa.reflective.fluent.builders.model.Setter;
import io.github.tobi.laa.reflective.fluent.builders.model.WriteAccessor;
import io.github.tobi.laa.reflective.fluent.builders.service.api.WriteAccessorService;
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
    private final WriteAccessorService writeAccessorService;

    @Override
    public MethodSpec generate(final BuilderMetadata builderMetadata, final WriteAccessor writeAccessor) {
        Objects.requireNonNull(builderMetadata);
        Objects.requireNonNull(writeAccessor);
        final var builderClassName = builderClassNameGenerator.generateClassName(builderMetadata);
        final String name;
        if (writeAccessor instanceof CollectionGetAndAdder) {
            final var collectionGetAndAdder = (CollectionGetAndAdder) writeAccessor;
            name = writeAccessorService.dropGetterPrefix(collectionGetAndAdder.getMethodName());
        } else if (writeAccessor instanceof Setter) {
            final var setter = (Setter) writeAccessor;
            name = writeAccessorService.dropSetterPrefix(setter.getMethodName());
        } else {
            name = writeAccessor.getPropertyName();
        }
        return MethodSpec.methodBuilder(name)
                .addModifiers(Modifier.PUBLIC)
                .returns(builderClassName)
                .addParameter(typeNameGenerator.generateTypeName(writeAccessor), writeAccessor.getPropertyName(), Modifier.FINAL)
                .addStatement("this.$1L.$2L = $2L", BuilderConstants.FieldValue.FIELD_NAME, writeAccessor.getPropertyName())
                .addStatement("this.$L.$L = $L", BuilderConstants.CallSetterFor.FIELD_NAME, writeAccessor.getPropertyName(), true)
                .addStatement("return this")
                .build();
    }
}

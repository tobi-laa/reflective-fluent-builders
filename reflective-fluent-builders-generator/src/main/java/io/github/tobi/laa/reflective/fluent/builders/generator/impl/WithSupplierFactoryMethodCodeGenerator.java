package io.github.tobi.laa.reflective.fluent.builders.generator.impl;

import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import io.github.tobi.laa.reflective.fluent.builders.generator.api.BuilderClassNameGenerator;
import io.github.tobi.laa.reflective.fluent.builders.generator.api.MethodCodeGenerator;
import io.github.tobi.laa.reflective.fluent.builders.model.BuilderMetadata;
import lombok.RequiredArgsConstructor;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.lang.model.element.Modifier;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * <p>
 * Generates a {@code withSupplier} factory method for a builder.
 * </p>
 */
@Named
@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
class WithSupplierFactoryMethodCodeGenerator implements MethodCodeGenerator {

    @lombok.NonNull
    private final BuilderClassNameGenerator builderClassNameGenerator;

    @Override
    public Optional<MethodSpec> generate(final BuilderMetadata builderMetadata) {
        Objects.requireNonNull(builderMetadata);
        final var builderClassName = builderClassNameGenerator.generateClassName(builderMetadata);
        final var supplierTypeName = ParameterizedTypeName.get(Supplier.class, builderMetadata.getBuiltType().getType().loadClass());
        return Optional.of(MethodSpec.methodBuilder("withSupplier")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(builderClassName)
                .addParameter(supplierTypeName, "supplier", Modifier.FINAL)
                .addStatement("return new $T(supplier)", builderClassName)
                .build());
    }
}

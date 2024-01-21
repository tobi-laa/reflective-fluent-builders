package io.github.tobi.laa.reflective.fluent.builders.generator.impl;

import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.TypeSpec;
import io.github.tobi.laa.reflective.fluent.builders.constants.BuilderConstants.FieldValue;
import io.github.tobi.laa.reflective.fluent.builders.generator.api.BuilderClassNameGenerator;
import io.github.tobi.laa.reflective.fluent.builders.generator.api.EncapsulatingClassCodeGenerator;
import io.github.tobi.laa.reflective.fluent.builders.generator.api.TypeNameGenerator;
import io.github.tobi.laa.reflective.fluent.builders.generator.model.EncapsulatingClassSpec;
import io.github.tobi.laa.reflective.fluent.builders.model.BuilderMetadata;
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
 * Generates a {@link BuilderMetadata builder's} inner class {@link FieldValue} which will hold all the actual field
 * values that will be used for building the resulting object.
 * </p>
 */
@Named
@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
class InnerClassFieldValueCodeGenerator implements EncapsulatingClassCodeGenerator {

    @lombok.NonNull
    private final BuilderClassNameGenerator builderClassNameGenerator;

    @lombok.NonNull
    private final TypeNameGenerator typeNameGenerator;

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
                        .builder(typeNameGenerator.generateTypeNameForParam(setter), setter.getPropertyName()) //
                        .build()) //
                .collect(Collectors.toList());
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

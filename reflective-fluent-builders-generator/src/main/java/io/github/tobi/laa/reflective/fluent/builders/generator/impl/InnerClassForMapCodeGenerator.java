package io.github.tobi.laa.reflective.fluent.builders.generator.impl;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import io.github.tobi.laa.reflective.fluent.builders.constants.BuilderConstants.CallSetterFor;
import io.github.tobi.laa.reflective.fluent.builders.constants.BuilderConstants.FieldValue;
import io.github.tobi.laa.reflective.fluent.builders.exception.CodeGenerationException;
import io.github.tobi.laa.reflective.fluent.builders.generator.api.BuilderClassNameGenerator;
import io.github.tobi.laa.reflective.fluent.builders.generator.api.CollectionClassCodeGenerator;
import io.github.tobi.laa.reflective.fluent.builders.generator.api.MapInitializerCodeGenerator;
import io.github.tobi.laa.reflective.fluent.builders.generator.api.TypeNameGenerator;
import io.github.tobi.laa.reflective.fluent.builders.generator.model.CollectionClassSpec;
import io.github.tobi.laa.reflective.fluent.builders.model.BuilderMetadata;
import io.github.tobi.laa.reflective.fluent.builders.model.MapType;
import io.github.tobi.laa.reflective.fluent.builders.model.WriteAccessor;
import lombok.RequiredArgsConstructor;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
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
@Named
@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
class InnerClassForMapCodeGenerator implements CollectionClassCodeGenerator {

    @lombok.NonNull
    private final BuilderClassNameGenerator builderClassNameGenerator;

    @lombok.NonNull
    private final TypeNameGenerator typeNameGenerator;

    @lombok.NonNull
    private final List<MapInitializerCodeGenerator> initializerGenerators;

    @Override
    public boolean isApplicable(final WriteAccessor writeAccessor) {
        Objects.requireNonNull(writeAccessor);
        if (!(writeAccessor.getPropertyType() instanceof MapType)) {
            return false;
        } else {
            final var mapType = (MapType) writeAccessor.getPropertyType();
            return initializerGenerators.stream().anyMatch(gen -> gen.isApplicable(mapType));
        }
    }

    @Override
    public CollectionClassSpec generate(final BuilderMetadata builderMetadata, final WriteAccessor writeAccessor) {
        Objects.requireNonNull(builderMetadata);
        Objects.requireNonNull(writeAccessor);
        if (writeAccessor.getPropertyType() instanceof MapType) {
            final var mapType = (MapType) writeAccessor.getPropertyType();
            return generate(builderMetadata, writeAccessor, mapType);
        } else {
            throw new CodeGenerationException("Generation of inner map class for " + writeAccessor + " is not supported.");
        }
    }

    private CollectionClassSpec generate(final BuilderMetadata builderMetadata, final WriteAccessor writeAccessor, final MapType mapType) {
        final var builderClassName = builderClassNameGenerator.generateClassName(builderMetadata);
        final var className = builderClassName.nestedClass("Map" + capitalize(writeAccessor.getPropertyName()));
        return CollectionClassSpec.builder() //
                .getter(MethodSpec //
                        .methodBuilder(writeAccessor.getPropertyName()) //
                        .addJavadoc(
                                "Returns an inner builder for the map property {@code $L} for chained calls of adding items to it.\n",
                                writeAccessor.getPropertyName()) //
                        .addJavadoc("Can be used like follows:\n") //
                        .addJavadoc("<pre>\n") //
                        .addJavadoc("builder.$L()\n", writeAccessor.getPropertyName()) //
                        .addJavadoc("       .put(key1, value1)\n") //
                        .addJavadoc("       .put(key2, value2)\n") //
                        .addJavadoc("       .and()\n") //
                        .addJavadoc("       .build()\n") //
                        .addJavadoc("</pre>\n") //
                        .addJavadoc("@return The inner builder for the map property {@code $L}.\n", writeAccessor.getPropertyName()) //
                        .addModifiers(Modifier.PUBLIC) //
                        .returns(className) //
                        .addStatement("return new $T()", className) //
                        .build()) //
                .innerClass(TypeSpec //
                        .classBuilder(className) //
                        .addModifiers(Modifier.PUBLIC) //
                        .addMethod(MethodSpec.methodBuilder("put") //
                                .addJavadoc("Adds an entry to the map property {@code $L}.\n", writeAccessor.getPropertyName()) //
                                .addJavadoc("@param key The key of the entry to add to the map {@code $L}.\n", writeAccessor.getPropertyName()) //
                                .addJavadoc("@param value The value of the entry to add to the map {@code $L}.\n", writeAccessor.getPropertyName()) //
                                .addJavadoc("@return This builder for chained calls.\n") //
                                .addModifiers(Modifier.PUBLIC) //
                                .addParameter(typeNameGenerator.generateTypeName(mapType.getKeyType()), "key", FINAL) //
                                .addParameter(typeNameGenerator.generateTypeName(mapType.getValueType()), "value", FINAL) //
                                .returns(className) //
                                .beginControlFlow("if ($T.this.$L.$L == null)", builderClassName, FieldValue.FIELD_NAME, writeAccessor.getPropertyName()) //
                                .addStatement(CodeBlock.builder()
                                        .add("$T.this.$L.$L = ", builderClassName, FieldValue.FIELD_NAME, writeAccessor.getPropertyName())
                                        .add(initializerGenerators //
                                                .stream() //
                                                .filter(gen -> gen.isApplicable(mapType)) //
                                                .map(gen -> gen.generateMapInitializer(mapType)) //
                                                .findFirst() //
                                                .orElseThrow(() -> new CodeGenerationException("Could not generate initializer for " + mapType + '.'))) //
                                        .build()) //
                                .endControlFlow() //
                                .addStatement("$T.this.$L.$L.put($L, $L)", builderClassName, FieldValue.FIELD_NAME, writeAccessor.getPropertyName(), "key", "value") //
                                .addStatement("$T.this.$L.$L = $L", builderClassName, CallSetterFor.FIELD_NAME, writeAccessor.getPropertyName(), true) //
                                .addStatement("return this") //
                                .build()) //
                        .addMethod(MethodSpec.methodBuilder("and") //
                                .addJavadoc("Returns the builder for the parent object.\n") //
                                .addJavadoc("@return The builder for the parent object.\n") //
                                .addModifiers(Modifier.PUBLIC) //
                                .returns(builderClassName) //
                                .addStatement("return $T.this", builderClassName) //
                                .build()) //
                        .build()) //
                .build();
    }
}

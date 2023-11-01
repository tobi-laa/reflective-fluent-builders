package io.github.tobi.laa.reflective.fluent.builders.generator.impl;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import io.github.tobi.laa.reflective.fluent.builders.constants.BuilderConstants.CallSetterFor;
import io.github.tobi.laa.reflective.fluent.builders.constants.BuilderConstants.FieldValue;
import io.github.tobi.laa.reflective.fluent.builders.exception.CodeGenerationException;
import io.github.tobi.laa.reflective.fluent.builders.generator.api.BuilderClassNameGenerator;
import io.github.tobi.laa.reflective.fluent.builders.generator.api.CollectionClassCodeGenerator;
import io.github.tobi.laa.reflective.fluent.builders.generator.api.CollectionInitializerCodeGenerator;
import io.github.tobi.laa.reflective.fluent.builders.generator.api.TypeNameGenerator;
import io.github.tobi.laa.reflective.fluent.builders.generator.model.CollectionClassSpec;
import io.github.tobi.laa.reflective.fluent.builders.model.BuilderMetadata;
import io.github.tobi.laa.reflective.fluent.builders.model.method.CollectionSetter;
import io.github.tobi.laa.reflective.fluent.builders.model.method.Setter;
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
 * Implementation of {@link CollectionClassCodeGenerator} for generating inner classes for convenient collection
 * construction.
 * </p>
 */
@Named
@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
class InnerClassForCollectionCodeGenerator implements CollectionClassCodeGenerator {

    @lombok.NonNull
    private final BuilderClassNameGenerator builderClassNameGenerator;

    @lombok.NonNull
    private final TypeNameGenerator typeNameGenerator;

    @lombok.NonNull
    private final List<CollectionInitializerCodeGenerator> initializerGenerators;

    @Override
    public boolean isApplicable(final Setter setter) {
        Objects.requireNonNull(setter);
        return setter instanceof CollectionSetter //
                && initializerGenerators.stream().anyMatch(gen -> gen.isApplicable((CollectionSetter) setter));
    }

    @Override
    public CollectionClassSpec generate(final BuilderMetadata builderMetadata, final Setter setter) {
        Objects.requireNonNull(builderMetadata);
        Objects.requireNonNull(setter);
        if (setter instanceof CollectionSetter) {
            final CollectionSetter collectionSetter = (CollectionSetter) setter;
            return generate(builderMetadata, collectionSetter);
        } else {
            throw new CodeGenerationException("Generation of inner collection class for " + setter + " is not supported.");
        }
    }

    private CollectionClassSpec generate(final BuilderMetadata builderMetadata, final CollectionSetter setter) {
        final var builderClassName = builderClassNameGenerator.generateClassName(builderMetadata);
        final var className = builderClassName.nestedClass("Collection" + capitalize(setter.getParamName()));
        return CollectionClassSpec.builder() //
                .getter(MethodSpec //
                        .methodBuilder(setter.getParamName()) //
                        .addModifiers(Modifier.PUBLIC) //
                        .returns(className) //
                        .addStatement("return new $T()", className) //
                        .build()) //
                .innerClass(TypeSpec //
                        .classBuilder(className) //
                        .addModifiers(Modifier.PUBLIC) //
                        .addMethod(MethodSpec.methodBuilder("add") //
                                .addModifiers(Modifier.PUBLIC) //
                                .addParameter(typeNameGenerator.generateTypeNameForParam(setter.getParamTypeArg()), "item", FINAL) //
                                .returns(className) //
                                .beginControlFlow("if ($T.this.$L.$L == null)", builderClassName, FieldValue.FIELD_NAME, setter.getParamName()) //
                                .addStatement(CodeBlock.builder()
                                        .add("$T.this.$L.$L = ", builderClassName, FieldValue.FIELD_NAME, setter.getParamName())
                                        .add(initializerGenerators //
                                                .stream() //
                                                .filter(gen -> gen.isApplicable(setter)) //
                                                .map(gen -> gen.generateCollectionInitializer(setter)) //
                                                .findFirst() //
                                                .orElseThrow(() -> new CodeGenerationException("Could not generate initializer for " + setter + '.'))) //
                                        .build()) //
                                .endControlFlow() //
                                .addStatement("$T.this.$L.$L.add($L)", builderClassName, FieldValue.FIELD_NAME, setter.getParamName(), "item") //
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

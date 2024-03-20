package io.github.tobi.laa.reflective.fluent.builders.generator.impl;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;
import io.github.tobi.laa.reflective.fluent.builders.constants.BuilderConstants;
import io.github.tobi.laa.reflective.fluent.builders.generator.api.BuilderClassNameGenerator;
import io.github.tobi.laa.reflective.fluent.builders.generator.api.SetterCodeGenerator;
import io.github.tobi.laa.reflective.fluent.builders.generator.api.SetterMethodNameGenerator;
import io.github.tobi.laa.reflective.fluent.builders.generator.api.TypeNameGenerator;
import io.github.tobi.laa.reflective.fluent.builders.model.*;
import lombok.RequiredArgsConstructor;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.lang.model.element.Modifier;
import java.util.ArrayList;
import java.util.Objects;

/**
 * <p>
 * Default implementation of {@link SetterCodeGenerator}.
 * </p>
 */
@Named
@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
@SuppressWarnings("java:S1192") // extracting the string literals would make the code less readable
class SetterCodeGeneratorImpl implements SetterCodeGenerator {

    @lombok.NonNull
    private final BuilderClassNameGenerator builderClassNameGenerator;

    @lombok.NonNull
    private final TypeNameGenerator typeNameGenerator;

    @lombok.NonNull
    private final SetterMethodNameGenerator methodNameGenerator;

    @Override
    public MethodSpec generate(final BuilderMetadata builderMetadata, final WriteAccessor writeAccessor) {
        Objects.requireNonNull(builderMetadata);
        Objects.requireNonNull(writeAccessor);
        if (writeAccessor instanceof Adder adder) {
            return generateForAdder(builderMetadata, adder);
        } else {
            return generateForNonAdder(builderMetadata, writeAccessor);
        }
    }

    private MethodSpec generateForNonAdder(final BuilderMetadata builderMetadata, final WriteAccessor writeAccessor) {
        final var builderClassName = builderClassNameGenerator.generateClassName(builderMetadata);
        final String name = methodNameGenerator.generate(writeAccessor);
        return MethodSpec.methodBuilder(name)
                .addJavadoc(generateJavadocForNonAdder(writeAccessor, name))
                .addModifiers(Modifier.PUBLIC)
                .returns(builderClassName)
                .addParameter(typeNameGenerator.generateTypeName(writeAccessor.getPropertyType()), name, Modifier.FINAL)
                .addStatement("this.$L.$L = $L", BuilderConstants.FieldValue.FIELD_NAME, writeAccessor.getPropertyName(), name)
                .addStatement("this.$L.$L = $L", BuilderConstants.CallSetterFor.FIELD_NAME, writeAccessor.getPropertyName(), true)
                .addStatement("return this")
                .build();
    }

    private CodeBlock generateJavadocForNonAdder(final WriteAccessor writeAccessor, final String paramName) {
        final var javadoc = CodeBlock.builder()
                .add("Sets the value for the {@code $L} property.\n", writeAccessor.getPropertyName());
        if (writeAccessor instanceof Getter getter) {
            javadoc.add("To be more precise, this will lead to {@link $T#$L()} being called on construction of the object.\n",
                    getter.getDeclaringClass(), getter.getMethodName());
        } else if (writeAccessor instanceof Setter setter) {
            javadoc.add("To be more precise, this will lead to {@link $T#$L($T)} being called on construction of the object.\n",
                    setter.getDeclaringClass(), setter.getMethodName(), setter.getPropertyType().getType());
        } else {
            javadoc.add("To be more precise, this will lead to the field {@link $T#$L} being modified directly on construction of the object.\n",
                    writeAccessor.getDeclaringClass(), writeAccessor.getPropertyName());
        }
        return javadoc
                .add("@param $L the value to set.\n", paramName)
                .add("@return This builder for chained calls.\n")
                .build();
    }

    private MethodSpec generateForAdder(final BuilderMetadata builderMetadata, final Adder adder) {
        final var builderClassName = builderClassNameGenerator.generateClassName(builderMetadata);
        final String name = methodNameGenerator.generate(adder);
        final var methodBuilder = MethodSpec.methodBuilder(name)
                .addJavadoc(generateJavadocForAdder(adder, name))
                .addModifiers(Modifier.PUBLIC)
                .returns(builderClassName)
                .addParameter(typeNameGenerator.generateTypeName(adder.getParamType()), name, Modifier.FINAL);
        return methodBuilder
                .beginControlFlow("if (this.$L.$L == null)", BuilderConstants.FieldValue.FIELD_NAME, adder.getPropertyName()) //
                .addStatement(CodeBlock.builder()
                        .add("this.$L.$L = new $T<>()", BuilderConstants.FieldValue.FIELD_NAME, adder.getPropertyName(), ArrayList.class)
                        .build()) //
                .endControlFlow()
                .addStatement("this.$L.$L.add($L)", BuilderConstants.FieldValue.FIELD_NAME, adder.getPropertyName(), name)
                .addStatement("this.$L.$L = $L", BuilderConstants.CallSetterFor.FIELD_NAME, adder.getPropertyName(), true)
                .addStatement("return this")
                .build();
    }

    private CodeBlock generateJavadocForAdder(final Adder adder, final String paramName) {
        return CodeBlock.builder()
                .add("Adds a value to the {@code $L} property.\n", adder.getPropertyName())
                .add("To be more precise, this will lead to {@link $T#$L($T)} being called on construction of the object.\n",
                        adder.getDeclaringClass(), adder.getMethodName(), adder.getPropertyType().getType())
                .add("@param $L the value to add to {@code $L}.\n", paramName, adder.getPropertyName())
                .add("@return This builder for chained calls.\n")
                .build();
    }
}

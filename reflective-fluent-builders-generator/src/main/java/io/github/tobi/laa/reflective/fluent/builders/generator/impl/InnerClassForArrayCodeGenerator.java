package io.github.tobi.laa.reflective.fluent.builders.generator.impl;


import com.squareup.javapoet.*;
import io.github.tobi.laa.reflective.fluent.builders.constants.BuilderConstants.CallSetterFor;
import io.github.tobi.laa.reflective.fluent.builders.constants.BuilderConstants.FieldValue;
import io.github.tobi.laa.reflective.fluent.builders.exception.CodeGenerationException;
import io.github.tobi.laa.reflective.fluent.builders.generator.api.BuilderClassNameGenerator;
import io.github.tobi.laa.reflective.fluent.builders.generator.api.CollectionClassCodeGenerator;
import io.github.tobi.laa.reflective.fluent.builders.generator.model.CollectionClassSpec;
import io.github.tobi.laa.reflective.fluent.builders.model.ArraySetter;
import io.github.tobi.laa.reflective.fluent.builders.model.BuilderMetadata;
import io.github.tobi.laa.reflective.fluent.builders.model.Setter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ClassUtils;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.lang.model.element.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static javax.lang.model.element.Modifier.FINAL;
import static org.apache.commons.lang3.StringUtils.capitalize;

/**
 * <p>
 * Implementation of {@link CollectionClassCodeGenerator} for generating inner classes for convenient array construction.
 * </p>
 */
@Named
@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
class InnerClassForArrayCodeGenerator implements CollectionClassCodeGenerator {

    @lombok.NonNull
    private final BuilderClassNameGenerator builderClassNameGenerator;

    @Override
    public boolean isApplicable(final Setter setter) {
        Objects.requireNonNull(setter);
        return setter instanceof ArraySetter;
    }

    @Override
    public CollectionClassSpec generate(final BuilderMetadata builderMetadata, final Setter setter) {
        Objects.requireNonNull(builderMetadata);
        Objects.requireNonNull(setter);
        if (setter instanceof ArraySetter) {
            final ArraySetter arraySetter = (ArraySetter) setter;
            return generate(builderMetadata, arraySetter);
        } else {
            throw new CodeGenerationException("Generation of inner array class for " + setter + " is not supported.");
        }
    }

    private CollectionClassSpec generate(final BuilderMetadata builderMetadata, final ArraySetter setter) {
        final ClassName builderClassName = builderClassNameGenerator.generateClassName(builderMetadata);
        final ClassName className = builderClassName.nestedClass("Array" + capitalize(setter.getParamName()));
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
                        .addField(FieldSpec.builder( //
                                        ParameterizedTypeName.get( //
                                                List.class, //
                                                ClassUtils.primitiveToWrapper((Class<?>) setter.getParamComponentType())), //
                                        "list", //
                                        Modifier.PRIVATE) //
                                .build()) //
                        .addMethod(MethodSpec.methodBuilder("add") //
                                .addModifiers(Modifier.PUBLIC) //
                                .addParameter(setter.getParamComponentType(), "item", FINAL) //
                                .returns(className) //
                                .beginControlFlow("if (this.list == null)") //
                                .addStatement("this.list = new $T<>()", ArrayList.class) //
                                .endControlFlow() //
                                .addStatement("this.list.add($L)", "item") //
                                .addStatement("$T.this.$L.$L = $L", builderClassName, CallSetterFor.FIELD_NAME, setter.getParamName(), true) //
                                .addStatement("return this") //
                                .build()) //
                        .addMethod(MethodSpec.methodBuilder("and") //
                                .addModifiers(Modifier.PUBLIC) //
                                .returns(builderClassName) //
                                .beginControlFlow("if (this.list != null)") //
                                .addStatement("$T.this.$L.$L = new $T[this.list.size()]", builderClassName, FieldValue.FIELD_NAME, setter.getParamName(), setter.getParamComponentType()) //
                                .beginControlFlow("for (int i = 0; i < this.list.size(); i++)")
                                .addStatement("$T.this.$L.$L[i] = this.list.get(i)", builderClassName, FieldValue.FIELD_NAME, setter.getParamName()) //
                                .endControlFlow()
                                .endControlFlow()
                                .addStatement("return $T.this", builderClassName) //
                                .build()) //
                        .build()) //
                .build();
    }
}

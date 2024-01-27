package io.github.tobi.laa.reflective.fluent.builders.generator.impl;


import com.squareup.javapoet.*;
import io.github.tobi.laa.reflective.fluent.builders.constants.BuilderConstants.CallSetterFor;
import io.github.tobi.laa.reflective.fluent.builders.constants.BuilderConstants.FieldValue;
import io.github.tobi.laa.reflective.fluent.builders.exception.CodeGenerationException;
import io.github.tobi.laa.reflective.fluent.builders.generator.api.BuilderClassNameGenerator;
import io.github.tobi.laa.reflective.fluent.builders.generator.api.CollectionClassCodeGenerator;
import io.github.tobi.laa.reflective.fluent.builders.generator.model.CollectionClassSpec;
import io.github.tobi.laa.reflective.fluent.builders.model.ArrayType;
import io.github.tobi.laa.reflective.fluent.builders.model.BuilderMetadata;
import io.github.tobi.laa.reflective.fluent.builders.model.WriteAccessor;
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
    public boolean isApplicable(final WriteAccessor writeAccessor) {
        Objects.requireNonNull(writeAccessor);
        return writeAccessor.getPropertyType() instanceof ArrayType;
    }

    @Override
    public CollectionClassSpec generate(final BuilderMetadata builderMetadata, final WriteAccessor writeAccessor) {
        Objects.requireNonNull(builderMetadata);
        Objects.requireNonNull(writeAccessor);
        if (writeAccessor.getPropertyType() instanceof ArrayType) {
            final ArrayType arrayType = (ArrayType) writeAccessor.getPropertyType();
            return generate(builderMetadata, writeAccessor, arrayType);
        } else {
            throw new CodeGenerationException("Generation of inner array class for " + writeAccessor + " is not supported.");
        }
    }

    private CollectionClassSpec generate(final BuilderMetadata builderMetadata, final WriteAccessor writeAccessor, final ArrayType arrayType) {
        final ClassName builderClassName = builderClassNameGenerator.generateClassName(builderMetadata);
        final ClassName className = builderClassName.nestedClass("Array" + capitalize(writeAccessor.getPropertyName()));
        return CollectionClassSpec.builder() //
                .getter(MethodSpec //
                        .methodBuilder(writeAccessor.getPropertyName()) //
                        .addJavadoc(
                                "Returns an inner builder for the array property {@code $L} for chained calls of adding items to it.\n",
                                writeAccessor.getPropertyName()) //
                        .addJavadoc("Can be used like follows:\n") //
                        .addJavadoc("<pre>\n") //
                        .addJavadoc("builder.$L()\n", writeAccessor.getPropertyName()) //
                        .addJavadoc("       .add(item1)\n") //
                        .addJavadoc("       .add(item2)\n") //
                        .addJavadoc("       .and()\n") //
                        .addJavadoc("       .build()\n") //
                        .addJavadoc("</pre>\n") //
                        .addJavadoc("@return The inner builder for the array property {@code $L}.\n", writeAccessor.getPropertyName()) //
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
                                                ClassUtils.primitiveToWrapper((Class<?>) arrayType.getComponentType())), //
                                        "list", //
                                        Modifier.PRIVATE) //
                                .build()) //
                        .addMethod(MethodSpec.methodBuilder("add") //
                                .addJavadoc("Adds an item to the array property {@code $L}.\n", writeAccessor.getPropertyName()) //
                                .addJavadoc("@param item The item to add to the array {@code $L}.\n", writeAccessor.getPropertyName()) //
                                .addJavadoc("@return This builder for chained calls.\n") //
                                .addModifiers(Modifier.PUBLIC) //
                                .addParameter(arrayType.getComponentType(), "item", FINAL) //
                                .returns(className) //
                                .beginControlFlow("if (this.list == null)") //
                                .addStatement("this.list = new $T<>()", ArrayList.class) //
                                .endControlFlow() //
                                .addStatement("this.list.add($L)", "item") //
                                .addStatement("$T.this.$L.$L = $L", builderClassName, CallSetterFor.FIELD_NAME, writeAccessor.getPropertyName(), true) //
                                .addStatement("return this") //
                                .build()) //
                        .addMethod(MethodSpec.methodBuilder("and") //
                                .addJavadoc("Returns the builder for the parent object.\n") //
                                .addJavadoc("@return The builder for the parent object.\n") //
                                .addModifiers(Modifier.PUBLIC) //
                                .returns(builderClassName) //
                                .beginControlFlow("if (this.list != null)") //
                                .addStatement("$T.this.$L.$L = new $T[this.list.size()]", builderClassName, FieldValue.FIELD_NAME, writeAccessor.getPropertyName(), arrayType.getComponentType()) //
                                .beginControlFlow("for (int i = 0; i < this.list.size(); i++)")
                                .addStatement("$T.this.$L.$L[i] = this.list.get(i)", builderClassName, FieldValue.FIELD_NAME, writeAccessor.getPropertyName()) //
                                .endControlFlow()
                                .endControlFlow()
                                .addStatement("return $T.this", builderClassName) //
                                .build()) //
                        .build()) //
                .build();
    }
}

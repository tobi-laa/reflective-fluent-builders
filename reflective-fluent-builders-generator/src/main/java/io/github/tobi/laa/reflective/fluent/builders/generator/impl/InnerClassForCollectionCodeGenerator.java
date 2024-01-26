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
import io.github.tobi.laa.reflective.fluent.builders.model.Adder;
import io.github.tobi.laa.reflective.fluent.builders.model.BuilderMetadata;
import io.github.tobi.laa.reflective.fluent.builders.model.CollectionType;
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
    public boolean isApplicable(final WriteAccessor writeAccessor) {
        Objects.requireNonNull(writeAccessor);
        if (writeAccessor.getPropertyType() instanceof CollectionType && (!(writeAccessor instanceof Adder))) {
            final var collectionType = (CollectionType) writeAccessor.getPropertyType();
            return initializerGenerators.stream().anyMatch(gen -> gen.isApplicable(collectionType));
        } else {
            return false;
        }
    }

    @Override
    public CollectionClassSpec generate(final BuilderMetadata builderMetadata, final WriteAccessor writeAccessor) {
        Objects.requireNonNull(builderMetadata);
        Objects.requireNonNull(writeAccessor);
        if (isApplicable(writeAccessor)) {
            final var type = (CollectionType) writeAccessor.getPropertyType();
            final var builderClassName = builderClassNameGenerator.generateClassName(builderMetadata);
            final var className = builderClassName.nestedClass("Collection" + capitalize(writeAccessor.getPropertyName()));
            return CollectionClassSpec.builder() //
                    .getter(MethodSpec //
                            .methodBuilder(writeAccessor.getPropertyName()) //
                            .addModifiers(Modifier.PUBLIC) //
                            .returns(className) //
                            .addStatement("return new $T()", className) //
                            .build()) //
                    .innerClass(TypeSpec //
                            .classBuilder(className) //
                            .addModifiers(Modifier.PUBLIC) //
                            .addMethod(MethodSpec.methodBuilder("add") //
                                    .addModifiers(Modifier.PUBLIC) //
                                    .addParameter(typeNameGenerator.generateTypeName(type.getTypeArg()), "item", FINAL) //
                                    .returns(className) //
                                    .beginControlFlow("if ($T.this.$L.$L == null)", builderClassName, FieldValue.FIELD_NAME, writeAccessor.getPropertyName()) //
                                    .addStatement(CodeBlock.builder()
                                            .add("$T.this.$L.$L = ", builderClassName, FieldValue.FIELD_NAME, writeAccessor.getPropertyName())
                                            .add(initializerGenerators //
                                                    .stream() //
                                                    .filter(gen -> gen.isApplicable(type)) //
                                                    .map(gen -> gen.generateCollectionInitializer(type)) //
                                                    .findFirst() //
                                                    .orElseThrow(() -> new CodeGenerationException("Could not generate initializer for " + type + '.'))) //
                                            .build()) //
                                    .endControlFlow() //
                                    .addStatement("$T.this.$L.$L.add($L)", builderClassName, FieldValue.FIELD_NAME, writeAccessor.getPropertyName(), "item") //
                                    .addStatement("$T.this.$L.$L = $L", builderClassName, CallSetterFor.FIELD_NAME, writeAccessor.getPropertyName(), true) //
                                    .addStatement("return this") //
                                    .build()) //
                            .addMethod(MethodSpec.methodBuilder("and") //
                                    .addModifiers(Modifier.PUBLIC) //
                                    .returns(builderClassName) //
                                    .addStatement("return $T.this", builderClassName) //
                                    .build()) //
                            .build()) //
                    .build();
        } else {
            throw new CodeGenerationException("Generation of inner collection class for " + writeAccessor + " is not supported.");
        }
    }

}

package io.github.tobi.laa.reflective.fluent.builders.generator.api;

import com.squareup.javapoet.MethodSpec;
import io.github.tobi.laa.reflective.fluent.builders.model.*;
import io.github.tobi.laa.reflective.fluent.builders.test.ClassGraphExtension;
import io.github.tobi.laa.reflective.fluent.builders.test.IntegrationTest;
import io.github.tobi.laa.reflective.fluent.builders.test.models.complex.ClassWithCollections;
import io.github.tobi.laa.reflective.fluent.builders.test.models.complex.DirectFieldAccess;
import io.github.tobi.laa.reflective.fluent.builders.test.models.complex.GetAndAdd;
import io.github.tobi.laa.reflective.fluent.builders.test.models.simple.SimpleClass;
import org.apache.commons.lang3.reflect.TypeUtils;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import javax.inject.Inject;
import java.util.Deque;
import java.util.List;
import java.util.SortedMap;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@IntegrationTest
class SetterCodeGeneratorIT {

    @RegisterExtension
    static ClassGraphExtension classInfo = new ClassGraphExtension();

    @Inject
    private SetterCodeGenerator generator;

    @ParameterizedTest
    @MethodSource
    void testGenerateNull(final BuilderMetadata builderMetadata, final WriteAccessor writeAccessor) {
        // Act
        final Executable generate = () -> generator.generate(builderMetadata, writeAccessor);
        // Assert
        assertThrows(NullPointerException.class, generate);
    }

    static Stream<Arguments> testGenerateNull() {
        return Stream.of( //
                Arguments.of(null, null),
                Arguments.of( //
                        BuilderMetadata.builder() //
                                .packageName("ignored") //
                                .name("Ignored") //
                                .builtType(BuilderMetadata.BuiltType.builder() //
                                        .type(classInfo.get(SimpleClass.class)) //
                                        .accessibleNonArgsConstructor(true) //
                                        .build()) //
                                .build(), //
                        null), //
                Arguments.of( //
                        null, //
                        Setter.builder() //
                                .methodName("setDeque") //
                                .propertyName("deque") //
                                .propertyType(new CollectionType(Deque.class, TypeUtils.wildcardType().build())) //
                                .visibility(Visibility.PRIVATE) //
                                .declaringClass(ClassWithCollections.class) //
                                .build()));
    }

    @ParameterizedTest
    @MethodSource
    void testGenerate(final BuilderMetadata builderMetadata, final WriteAccessor writeAccessor, final String expected) {
        // Act
        final MethodSpec actual = generator.generate(builderMetadata, writeAccessor);
        // Assert
        assertThat(actual).hasToString(expected);
    }

    static Stream<Arguments> testGenerate() {
        final var mockTypeName = MockType.class.getName().replace('$', '.');
        final var builderMetadata = BuilderMetadata.builder() //
                .packageName(MockType.class.getPackageName()) //
                .name("SetterCodeGeneratorIT.MockType") //
                .builtType(BuilderMetadata.BuiltType.builder() //
                        .type(classInfo.get(SimpleClass.class)) //
                        .accessibleNonArgsConstructor(true) //
                        .build()) //
                .build();
        return Stream.of( //
                Arguments.of( //
                        builderMetadata, //
                        Setter.builder() //
                                .methodName("setAnInt") //
                                .propertyName("anInt") //
                                .propertyType(new SimpleType(int.class)) //
                                .visibility(Visibility.PUBLIC) //
                                .declaringClass(SimpleClass.class) //
                                .build(), //
                        String.format(
                                """
                                        /**
                                         * Sets the value for the {@code anInt} property.
                                         * To be more precise, this will lead to {@link io.github.tobi.laa.reflective.fluent.builders.test.models.simple.SimpleClass#setAnInt(int)} being called on construction of the object.
                                         * @param anInt the value to set.
                                         * @return This builder for chained calls.
                                         */
                                        public %1$s anInt(
                                            final int anInt) {
                                          this.fieldValue.anInt = anInt;
                                          this.callSetterFor.anInt = true;
                                          return this;
                                        }
                                        """,
                                mockTypeName)), //
                Arguments.of( //
                        builderMetadata, //
                        Setter.builder() //
                                .methodName("setFloats") //
                                .propertyName("floats") //
                                .propertyType(new ArrayType(float[].class, float.class)) //
                                .visibility(Visibility.PRIVATE) //
                                .declaringClass(ClassWithCollections.class) //
                                .build(), //
                        String.format(
                                """
                                        /**
                                         * Sets the value for the {@code floats} property.
                                         * To be more precise, this will lead to {@link io.github.tobi.laa.reflective.fluent.builders.test.models.complex.ClassWithCollections#setFloats(float[])} being called on construction of the object.
                                         * @param floats the value to set.
                                         * @return This builder for chained calls.
                                         */
                                        public %1$s floats(
                                            final float[] floats) {
                                          this.fieldValue.floats = floats;
                                          this.callSetterFor.floats = true;
                                          return this;
                                        }
                                        """,
                                mockTypeName)), //
                Arguments.of( //
                        builderMetadata, //
                        Setter.builder() //
                                .methodName("setSortedMap") //
                                .propertyName("sortedMap") //
                                .propertyType(new MapType(SortedMap.class, Integer.class, Object.class)) //
                                .visibility(Visibility.PRIVATE) //
                                .declaringClass(ClassWithCollections.class) //
                                .build(), //
                        String.format(
                                """
                                        /**
                                         * Sets the value for the {@code sortedMap} property.
                                         * To be more precise, this will lead to {@link io.github.tobi.laa.reflective.fluent.builders.test.models.complex.ClassWithCollections#setSortedMap(java.util.SortedMap)} being called on construction of the object.
                                         * @param sortedMap the value to set.
                                         * @return This builder for chained calls.
                                         */
                                        public %1$s sortedMap(
                                            final java.util.SortedMap sortedMap) {
                                          this.fieldValue.sortedMap = sortedMap;
                                          this.callSetterFor.sortedMap = true;
                                          return this;
                                        }
                                        """,
                                mockTypeName)), //
                Arguments.of( //
                        builderMetadata, //
                        Setter.builder() //
                                .methodName("setList") //
                                .propertyName("list") //
                                .propertyType(new CollectionType(List.class, String.class)) //
                                .visibility(Visibility.PRIVATE) //
                                .declaringClass(ClassWithCollections.class) //
                                .build(), //
                        String.format(
                                """
                                        /**
                                         * Sets the value for the {@code list} property.
                                         * To be more precise, this will lead to {@link io.github.tobi.laa.reflective.fluent.builders.test.models.complex.ClassWithCollections#setList(java.util.List)} being called on construction of the object.
                                         * @param list the value to set.
                                         * @return This builder for chained calls.
                                         */
                                        public %1$s list(
                                            final java.util.List list) {
                                          this.fieldValue.list = list;
                                          this.callSetterFor.list = true;
                                          return this;
                                        }
                                        """,
                                mockTypeName)), //
                Arguments.of( //
                        builderMetadata, //
                        Getter.builder() //
                                .methodName("getList") //
                                .propertyName("list") //
                                .propertyType(new CollectionType(List.class, String.class)) //
                                .visibility(Visibility.PRIVATE) //
                                .declaringClass(GetAndAdd.class) //
                                .build(), //
                        String.format(
                                """
                                        /**
                                         * Sets the value for the {@code list} property.
                                         * To be more precise, this will lead to {@link io.github.tobi.laa.reflective.fluent.builders.test.models.complex.GetAndAdd#getList()} being called on construction of the object.
                                         * @param list the value to set.
                                         * @return This builder for chained calls.
                                         */
                                        public %1$s list(
                                            final java.util.List list) {
                                          this.fieldValue.list = list;
                                          this.callSetterFor.list = true;
                                          return this;
                                        }
                                        """,
                                mockTypeName)),
                Arguments.of( //
                        builderMetadata, //
                        FieldAccessor.builder() //
                                .propertyName("publicFieldNoSetter") //
                                .propertyType(new SimpleType(int.class)) //
                                .visibility(Visibility.PUBLIC) //
                                .declaringClass(DirectFieldAccess.class) //
                                .build(), //
                        String.format(
                                """
                                        /**
                                         * Sets the value for the {@code publicFieldNoSetter} property.
                                         * To be more precise, this will lead to the field {@link io.github.tobi.laa.reflective.fluent.builders.test.models.complex.DirectFieldAccess#publicFieldNoSetter} being modified directly on construction of the object.
                                         * @param publicFieldNoSetter the value to set.
                                         * @return This builder for chained calls.
                                         */
                                        public %1$s publicFieldNoSetter(
                                            final int publicFieldNoSetter) {
                                          this.fieldValue.publicFieldNoSetter = publicFieldNoSetter;
                                          this.callSetterFor.publicFieldNoSetter = true;
                                          return this;
                                        }
                                        """,
                                mockTypeName)));
    }

    private static class MockType {
        // no content
    }
}
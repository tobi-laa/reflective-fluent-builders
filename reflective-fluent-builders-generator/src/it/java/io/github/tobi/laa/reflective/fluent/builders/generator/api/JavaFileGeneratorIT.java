package io.github.tobi.laa.reflective.fluent.builders.generator.api;

import io.github.tobi.laa.reflective.fluent.builders.model.*;
import io.github.tobi.laa.reflective.fluent.builders.test.ClassGraphExtension;
import io.github.tobi.laa.reflective.fluent.builders.test.IntegrationTest;
import io.github.tobi.laa.reflective.fluent.builders.test.models.complex.ClassWithGenerics;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import javax.inject.Inject;
import java.lang.reflect.TypeVariable;

import static org.assertj.core.api.Assertions.assertThat;

@IntegrationTest
class JavaFileGeneratorIT {

    @RegisterExtension
    static ClassGraphExtension classInfo = new ClassGraphExtension();

    @Inject
    private JavaFileGenerator javaFileGenerator;

    @Test
    void testGenerateJavaFile() {
        // Arrange
        final var builderMetadata = BuilderMetadata.builder() //
                .packageName("io.github.tobi.laa.reflective.fluent.builders.test.models.complex") //
                .name("ClassWithGenericsBuilder") //
                .builtType(BuilderMetadata.BuiltType.builder() //
                        .type(classInfo.get(ClassWithGenerics.class)) //
                        .accessibleNonArgsConstructor(true) //
                        .writeAccessor(Setter.builder() //
                                .methodName("setAnInt") //
                                .propertyName("anInt") //
                                .propertyType(new SimpleType(int.class)) //
                                .visibility(Visibility.PUBLIC) //
                                .declaringClass(ClassWithGenerics.class) //
                                .build()) //
                        .writeAccessor(Setter.builder() //
                                .methodName("setFloats") //
                                .propertyName("floats") //
                                .propertyType(new ArrayType(float[].class, float.class)) //
                                .visibility(Visibility.PRIVATE) //
                                .declaringClass(ClassWithGenerics.class) //
                                .build()) //
                        .writeAccessor(Setter.builder() //
                                .methodName("setT") //
                                .propertyName("t") //
                                .propertyType(new SimpleType(typeVariableT())) //
                                .visibility(Visibility.PRIVATE) //
                                .declaringClass(ClassWithGenerics.class) //
                                .build()) //
                        .build()) //
                .build();
        // Act
        final var actual = javaFileGenerator.generateJavaFile(builderMetadata);
        // Assert
        assertThat(actual).isNotNull();
        assertThat(actual.toString()).isEqualToIgnoringNewLines(
                """
                        package io.github.tobi.laa.reflective.fluent.builders.test.models.complex;

                        import java.lang.Float;
                        import java.lang.SuppressWarnings;
                        import java.util.ArrayList;
                        import java.util.List;
                        import java.util.Objects;
                        import java.util.function.Supplier;
                        import javax.annotation.processing.Generated;

                        /**
                         * Builder for {@link ClassWithGenerics}.
                         */
                        @Generated(
                            value = "io.github.tobi.laa.reflective.fluent.builders.generator.api.JavaFileGenerator",
                            date = "3333-03-13T00:00Z[UTC]"
                        )
                        public class ClassWithGenericsBuilder<T> {
                          /**
                           * This field is solely used to be able to detect generated builders via reflection at a later stage.
                           */
                          @SuppressWarnings("all")
                          private boolean ______generatedByReflectiveFluentBuildersGenerator;

                          private final Supplier<ClassWithGenerics> objectSupplier;

                          private final CallSetterFor callSetterFor = new CallSetterFor();

                          private final FieldValue fieldValue = new FieldValue();

                          /**
                           * Creates a new instance of {@link ClassWithGenerics} using the given {@code objectSupplier}.
                           * Has been set to visibility {@code protected} so that users may choose to inherit the builder.
                           */
                          protected ClassWithGenericsBuilder(final Supplier<ClassWithGenerics> objectSupplier) {
                            this.objectSupplier = Objects.requireNonNull(objectSupplier);
                          }

                          /**
                           * Creates an instance of {@link ClassWithGenericsBuilder} that will work on a new instance of {@link ClassWithGenerics} once {@link #build()} is called.
                           */
                          public static ClassWithGenericsBuilder newInstance() {
                            return new ClassWithGenericsBuilder(ClassWithGenerics::new);
                          }

                          /**
                           * Creates an instance of {@link ClassWithGenericsBuilder} that will work on an instance of {@link ClassWithGenerics} that is created initially by the given {@code supplier} once {@link #build()} is called.
                           */
                          public static ClassWithGenericsBuilder withSupplier(final Supplier<ClassWithGenerics> supplier) {
                            return new ClassWithGenericsBuilder(supplier);
                          }

                          /**
                           * Returns an inner builder for the array property {@code floats} for chained calls of adding items to it.
                           * Can be used like follows:
                           * <pre>
                           * builder.floats()
                           *        .add(item1)
                           *        .add(item2)
                           *        .and()
                           *        .build()
                           * </pre>
                           * @return The inner builder for the array property {@code floats}.
                           */
                          public ArrayFloats floats() {
                            return new ArrayFloats();
                          }

                          /**
                           * Sets the value for the {@code anInt} property.
                           * To be more precise, this will lead to {@link ClassWithGenerics#setAnInt(int)} being called on construction of the object.
                           * @param anInt the value to set.
                           * @return This builder for chained calls.
                           */
                          public ClassWithGenericsBuilder anInt(final int anInt) {
                            this.fieldValue.anInt = anInt;
                            this.callSetterFor.anInt = true;
                            return this;
                          }

                          /**
                           * Sets the value for the {@code floats} property.
                           * To be more precise, this will lead to {@link ClassWithGenerics#setFloats(float[])} being called on construction of the object.
                           * @param floats the value to set.
                           * @return This builder for chained calls.
                           */
                          public ClassWithGenericsBuilder floats(final float[] floats) {
                            this.fieldValue.floats = floats;
                            this.callSetterFor.floats = true;
                            return this;
                          }

                          /**
                           * Sets the value for the {@code t} property.
                           * To be more precise, this will lead to {@link ClassWithGenerics#setT(T)} being called on construction of the object.
                           * @param t the value to set.
                           * @return This builder for chained calls.
                           */
                          public ClassWithGenericsBuilder t(final T t) {
                            this.fieldValue.t = t;
                            this.callSetterFor.t = true;
                            return this;
                          }

                          /**
                           * Performs the actual construction of an instance for {@link ClassWithGenerics}.
                           * @return The constructed instance. Never {@code null}.
                           */
                          public ClassWithGenerics build() {
                            final ClassWithGenerics objectToBuild = this.objectSupplier.get();
                            if (this.callSetterFor.anInt) {
                              objectToBuild.setAnInt(this.fieldValue.anInt);
                            }
                            if (this.callSetterFor.floats) {
                              objectToBuild.setFloats(this.fieldValue.floats);
                            }
                            if (this.callSetterFor.t) {
                              objectToBuild.setT(this.fieldValue.t);
                            }
                            return objectToBuild;
                          }

                          private class CallSetterFor {
                            boolean anInt;

                            boolean floats;

                            boolean t;
                          }

                          private class FieldValue {
                            int anInt;

                            float[] floats;

                            T t;
                          }

                          public class ArrayFloats {
                            private List<Float> list;

                            /**
                             * Adds an item to the array property {@code floats}.
                             * @param item The item to add to the array {@code floats}.
                             * @return This builder for chained calls.
                             */
                            public ArrayFloats add(final float item) {
                              if (this.list == null) {
                                this.list = new ArrayList<>();
                              }
                              this.list.add(item);
                              ClassWithGenericsBuilder.this.callSetterFor.floats = true;
                              return this;
                            }

                            /**
                             * Returns the builder for the parent object.
                             * @return The builder for the parent object.
                             */
                            public ClassWithGenericsBuilder and() {
                              if (this.list != null) {
                                ClassWithGenericsBuilder.this.fieldValue.floats = new float[this.list.size()];
                                for (int i = 0; i < this.list.size(); i++) {
                                  ClassWithGenericsBuilder.this.fieldValue.floats[i] = this.list.get(i);
                                }
                              }
                              return ClassWithGenericsBuilder.this;
                            }
                          }
                        }""");
    }

    private TypeVariable<?> typeVariableT() {
        return ClassWithGenerics.class.getTypeParameters()[0];
    }

}

package io.github.tobi.laa.reflective.fluent.builders.service.api;

import io.github.classgraph.ClassInfo;
import io.github.tobi.laa.reflective.fluent.builders.model.*;
import io.github.tobi.laa.reflective.fluent.builders.props.api.BuildersProperties;
import io.github.tobi.laa.reflective.fluent.builders.test.ClassGraphExtension;
import io.github.tobi.laa.reflective.fluent.builders.test.InjectSpy;
import io.github.tobi.laa.reflective.fluent.builders.test.IntegrationTest;
import io.github.tobi.laa.reflective.fluent.builders.test.models.complex.*;
import io.github.tobi.laa.reflective.fluent.builders.test.models.complex.hierarchy.*;
import io.github.tobi.laa.reflective.fluent.builders.test.models.complex.hierarchy.generics.Generic;
import io.github.tobi.laa.reflective.fluent.builders.test.models.complex.hierarchy.generics.GenericChild;
import io.github.tobi.laa.reflective.fluent.builders.test.models.complex.hierarchy.generics.GenericGrandChild;
import io.github.tobi.laa.reflective.fluent.builders.test.models.complex.hierarchy.generics.GenericParent;
import io.github.tobi.laa.reflective.fluent.builders.test.models.complex.hierarchy.second.SecondSuperClassInDifferentPackage;
import io.github.tobi.laa.reflective.fluent.builders.test.models.full.Person;
import io.github.tobi.laa.reflective.fluent.builders.test.models.jaxb.PersonJaxb;
import io.github.tobi.laa.reflective.fluent.builders.test.models.jaxb.PetJaxb;
import io.github.tobi.laa.reflective.fluent.builders.test.models.simple.SimpleClass;
import io.github.tobi.laa.reflective.fluent.builders.test.models.simple.SimpleClassNoSetPrefix;
import lombok.SneakyThrows;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.assertj.core.api.recursive.comparison.RecursiveComparisonConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junitpioneer.jupiter.cartesian.ArgumentSets;
import org.junitpioneer.jupiter.cartesian.CartesianTest;

import javax.inject.Inject;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.*;
import java.util.stream.Stream;

import static io.github.tobi.laa.reflective.fluent.builders.model.Visibility.*;
import static org.apache.commons.lang3.reflect.TypeUtils.parameterize;
import static org.apache.commons.lang3.reflect.TypeUtils.wildcardType;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@IntegrationTest
class WriteAccessorServiceIT {

    @RegisterExtension
    static ClassGraphExtension classInfo = new ClassGraphExtension();

    @Inject
    private WriteAccessorService writeAccessorService;

    @InjectSpy
    private BuildersProperties properties;

    @Test
    void testDropSetterPrefixNull() {
        // Act
        final Executable dropSetterPrefix = () -> writeAccessorService.dropSetterPrefix(null);
        // Assert
        assertThrows(NullPointerException.class, dropSetterPrefix);
    }

    @ParameterizedTest
    @MethodSource
    void testDropSetterPrefix(final String setterPrefix, final String name, final String expected) {
        // Arrange
        when(properties.getSetterPrefix()).thenReturn(setterPrefix);
        // Act
        final String actual = writeAccessorService.dropSetterPrefix(name);
        // Assert
        assertEquals(expected, actual);
    }

    private static Stream<Arguments> testDropSetterPrefix() {
        return Stream.of( //
                Arguments.of("set", "set", "set"), //
                Arguments.of("set", "setAge", "age"), //
                Arguments.of("set", "withAge", "withAge"), //
                Arguments.of("set", "setSetAge", "setAge"));
    }

    @Test
    void testDropGetterPrefixNull() {
        // Act
        final Executable dropGetterPrefix = () -> writeAccessorService.dropGetterPrefix(null);
        // Assert
        assertThrows(NullPointerException.class, dropGetterPrefix);
    }

    @ParameterizedTest
    @MethodSource
    void testDropGetterPrefix(final String getterPrefix, final String name, final String expected) {
        // Arrange
        when(properties.getGetterPrefix()).thenReturn(getterPrefix);
        // Act
        final String actual = writeAccessorService.dropGetterPrefix(name);
        // Assert
        assertEquals(expected, actual);
    }

    private static Stream<Arguments> testDropGetterPrefix() {
        return Stream.of( //
                Arguments.of("get", "get", "get"), //
                Arguments.of("get", "getAge", "age"), //
                Arguments.of("get", "withAge", "withAge"), //
                Arguments.of("get", "getGetAge", "getAge"));
    }

    @Test
    void testGatherAllSettersNull() {
        // Act
        final Executable gatherAllSetters = () -> writeAccessorService.gatherAllWriteAccessors(null);
        // Assert
        assertThrows(NullPointerException.class, gatherAllSetters);
    }

    @ParameterizedTest
    @MethodSource
    void testGatherAllSetters(
            final String setterPrefix,
            final String getterPrefix,
            final boolean getAndAddEnabled,
            final boolean directFieldAccessEnabled,
            final boolean addersEnabled,
            final ClassInfo clazz,
            final Set<WriteAccessor> expected) {
        // Arrange
        doReturn(setterPrefix).when(properties).getSetterPrefix();
        doReturn(getAndAddEnabled).when(properties).isGetAndAddEnabled();
        doReturn(directFieldAccessEnabled).when(properties).isDirectFieldAccessEnabled();
        doReturn(addersEnabled).when(properties).isAddersEnabled();
        if (getAndAddEnabled) {
            doReturn(getterPrefix).when(properties).getGetterPrefix();
        }
        // Act
        final Set<WriteAccessor> actual = writeAccessorService.gatherAllWriteAccessors(clazz);
        // Assert
        assertThat(actual)
                .usingRecursiveComparison(RecursiveComparisonConfiguration.builder()
                        .withEqualsForFields(
                                (a, b) -> ((Type) a).getTypeName().equals(((Type) b).getTypeName()),
                                "propertyType.type",
                                "propertyType.typeArg",
                                "propertyType.keyType",
                                "propertyType.valueType",
                                "paramType.type",
                                "paramType.typeArg")
                        .build())
                .isEqualTo(expected);
    }

    @SneakyThrows
    private static Stream<Arguments> testGatherAllSetters() {
        final var setOfList = ClassWithCollections.class.getDeclaredField("set").getGenericType();
        return Stream.of( //
                Arguments.of("set", null, false, false, true, classInfo.get(SimpleClass.class), //
                        Set.of( //
                                Setter.builder().methodName("setAnInt").propertyName("anInt").propertyType(new SimpleType(int.class)).visibility(PUBLIC).declaringClass(SimpleClass.class).build(), //
                                Setter.builder().methodName("setAString").propertyName("aString").propertyType(new SimpleType(String.class)).visibility(PUBLIC).declaringClass(SimpleClass.class).build(), //
                                Setter.builder().methodName("setBooleanField").propertyName("booleanField").propertyType(new SimpleType(boolean.class)).visibility(PUBLIC).declaringClass(SimpleClass.class).build(), //
                                Setter.builder().methodName("setSetClass").propertyName("setClass").propertyType(new SimpleType(parameterize(Class.class, wildcardType().withUpperBounds(Object.class).build()))).visibility(PUBLIC).declaringClass(SimpleClass.class).build())), //
                Arguments.of("", null, false, false, true, classInfo.get(SimpleClassNoSetPrefix.class), //
                        Set.of( //
                                Setter.builder().methodName("anInt").propertyName("anInt").propertyType(new SimpleType(int.class)).visibility(PACKAGE_PRIVATE).declaringClass(SimpleClassNoSetPrefix.class).build(), //
                                Setter.builder().methodName("aString").propertyName("aString").propertyType(new SimpleType(String.class)).visibility(PACKAGE_PRIVATE).declaringClass(SimpleClassNoSetPrefix.class).build())), //
                Arguments.of("set", "get", false, false, true, classInfo.get(ClassWithCollections.class), //
                        Set.of( //
                                Setter.builder().methodName("setInts").propertyName("ints").propertyType(new CollectionType(parameterize(Collection.class, Integer.class), Integer.class)).visibility(PUBLIC).declaringClass(ClassWithCollections.class).build(), //
                                Setter.builder().methodName("setList").propertyName("list").propertyType(new CollectionType(List.class, Object.class)).visibility(PUBLIC).declaringClass(ClassWithCollections.class).build(), //
                                Setter.builder().methodName("setSet").propertyName("set").propertyType(new CollectionType(setOfList, List.class)).visibility(PUBLIC).declaringClass(ClassWithCollections.class).build(), //
                                Setter.builder().methodName("setDeque").propertyName("deque").propertyType(new CollectionType(parameterize(Deque.class, wildcardType().withUpperBounds(Object.class).build()), wildcardType().withUpperBounds(Object.class).build())).visibility(PUBLIC).declaringClass(ClassWithCollections.class).build(), //
                                Setter.builder().methodName("setSortedSetWild").propertyName("sortedSetWild").propertyType(new CollectionType(parameterize(SortedSet.class, wildcardType().build()), wildcardType().build())).visibility(PUBLIC).declaringClass(ClassWithCollections.class).build(), //
                                Setter.builder().methodName("setFloats").propertyName("floats").propertyType(new ArrayType(float[].class, float.class)).visibility(PUBLIC).declaringClass(ClassWithCollections.class).build(), //
                                Setter.builder().methodName("setMap").propertyName("map").propertyType(new MapType(parameterize(Map.class, String.class, Object.class), String.class, Object.class)).visibility(PUBLIC).declaringClass(ClassWithCollections.class).build(), //
                                Setter.builder().methodName("setMapTU").propertyName("mapTU").propertyType(new MapType(parameterize(Map.class, typeVariableT(), typeVariableU()), typeVariableT(), typeVariableU())).visibility(PUBLIC).declaringClass(ClassWithCollections.class).build(), //
                                Setter.builder().methodName("setMapWildObj").propertyName("mapWildObj").propertyType(new MapType(parameterize(Map.class, wildcardType().build(), Object.class), wildcardType().build(), Object.class)).visibility(PUBLIC).declaringClass(ClassWithCollections.class).build(), //
                                Setter.builder().methodName("setMapNoTypeArgs").propertyName("mapNoTypeArgs").propertyType(new MapType(Map.class, Object.class, Object.class)).visibility(PUBLIC).declaringClass(ClassWithCollections.class).build(), //
                                Setter.builder().methodName("setListWithTwoParams").propertyName("listWithTwoParams").propertyType(new CollectionType(parameterize(ListWithTwoParams.class, String.class, Integer.class), parameterize(Map.class, String.class, Integer.class))).visibility(PUBLIC).declaringClass(ClassWithCollections.class).build(), //
                                Setter.builder().methodName("setMapWithThreeParams").propertyName("mapWithThreeParams").propertyType(new MapType(parameterize(MapWithThreeParams.class, String.class, Integer.class, Boolean.class), String.class, parameterize(Map.class, Integer.class, Boolean.class))).visibility(PUBLIC).declaringClass(ClassWithCollections.class).build())), //
                Arguments.of("set", "get", false, false, true, classInfo.get(PetJaxb.class), //
                        Set.of( //
                                Setter.builder().methodName("setFullName").propertyName("fullName").propertyType(new SimpleType(String.class)).visibility(PUBLIC).declaringClass(PetJaxb.class).build(), //
                                Setter.builder().methodName("setWeight").propertyName("weight").propertyType(new SimpleType(float.class)).visibility(PUBLIC).declaringClass(PetJaxb.class).build(), //
                                Setter.builder().methodName("setOwner").propertyName("owner").propertyType(new SimpleType(PersonJaxb.class)).visibility(PUBLIC).declaringClass(PetJaxb.class).build())),
                Arguments.of("set", "get", true, false, true, classInfo.get(PetJaxb.class), //
                        Set.of( //
                                Setter.builder().methodName("setFullName").propertyName("fullName").propertyType(new SimpleType(String.class)).visibility(PUBLIC).declaringClass(PetJaxb.class).build(), //
                                Setter.builder().methodName("setWeight").propertyName("weight").propertyType(new SimpleType(float.class)).visibility(PUBLIC).declaringClass(PetJaxb.class).build(), //
                                Getter.builder().methodName("getSiblings").propertyName("siblings").propertyType(new CollectionType(parameterize(List.class, PetJaxb.class), PetJaxb.class)).visibility(PUBLIC).declaringClass(PetJaxb.class).build(), //
                                Setter.builder().methodName("setOwner").propertyName("owner").propertyType(new SimpleType(PersonJaxb.class)).visibility(PUBLIC).declaringClass(PetJaxb.class).build())),
                Arguments.of("set", "myPrefix", true, false, true, classInfo.get(PetJaxb.class), //
                        Set.of( //
                                Setter.builder().methodName("setFullName").propertyName("fullName").propertyType(new SimpleType(String.class)).visibility(PUBLIC).declaringClass(PetJaxb.class).build(), //
                                Setter.builder().methodName("setWeight").propertyName("weight").propertyType(new SimpleType(float.class)).visibility(PUBLIC).declaringClass(PetJaxb.class).build(), //
                                Setter.builder().methodName("setOwner").propertyName("owner").propertyType(new SimpleType(PersonJaxb.class)).visibility(PUBLIC).declaringClass(PetJaxb.class).build())), //
                Arguments.of("set", "get", true, false, true, classInfo.get(GetAndAdd.class), //
                        Set.of( //
                                Setter.builder().methodName("setListGetterAndSetter").propertyName("listGetterAndSetter").propertyType(new CollectionType(parameterize(List.class, String.class), String.class)).visibility(PUBLIC).declaringClass(GetAndAdd.class).build(), //
                                Getter.builder().methodName("getListNoSetter").propertyName("listNoSetter").propertyType(new CollectionType(parameterize(List.class, String.class), String.class)).visibility(PUBLIC).declaringClass(GetAndAdd.class).build(), //
                                Setter.builder().methodName("setListNoGetter").propertyName("listNoGetter").propertyType(new CollectionType(parameterize(List.class, String.class), String.class)).visibility(PUBLIC).declaringClass(GetAndAdd.class).build(), //
                                Getter.builder().methodName("getListSetterWrongType").propertyName("listSetterWrongType").propertyType(new CollectionType(parameterize(List.class, String.class), String.class)).visibility(PUBLIC).declaringClass(GetAndAdd.class).build(), //
                                Setter.builder().methodName("setListSetterWrongType").propertyName("listSetterWrongType").propertyType(new ArrayType(String[].class, String.class)).visibility(PUBLIC).declaringClass(GetAndAdd.class).build())),
                Arguments.of("set", "get", true, true, true, classInfo.get(DirectFieldAccess.class), //
                        Set.<WriteAccessor>of( //
                                FieldAccessor.builder().propertyName("packagePrivateFieldNoSetter").propertyType(new SimpleType(int.class)).visibility(PACKAGE_PRIVATE).declaringClass(DirectFieldAccess.class).build(), //
                                FieldAccessor.builder().propertyName("protectedFieldNoSetter").propertyType(new SimpleType(int.class)).visibility(PROTECTED).declaringClass(DirectFieldAccess.class).build(), //
                                FieldAccessor.builder().propertyName("publicFieldNoSetter").propertyType(new SimpleType(int.class)).visibility(PUBLIC).declaringClass(DirectFieldAccess.class).build(), //
                                Setter.builder().methodName("setPackagePrivateFieldWithSetter").propertyName("packagePrivateFieldWithSetter").propertyType(new SimpleType(int.class)).visibility(PACKAGE_PRIVATE).declaringClass(DirectFieldAccess.class).build(), //
                                Setter.builder().methodName("setProtectedFieldWithSetter").propertyName("protectedFieldWithSetter").propertyType(new SimpleType(int.class)).visibility(PROTECTED).declaringClass(DirectFieldAccess.class).build(), //
                                Setter.builder().methodName("setPublicFieldWithSetter").propertyName("publicFieldWithSetter").propertyType(new SimpleType(int.class)).visibility(PUBLIC).declaringClass(DirectFieldAccess.class).build(), //
                                Getter.builder().methodName("getPackagePrivateFieldWithGetAndAdd").propertyName("packagePrivateFieldWithGetAndAdd").propertyType(new CollectionType(parameterize(List.class, String.class), String.class)).visibility(PACKAGE_PRIVATE).declaringClass(DirectFieldAccess.class).build(), //
                                Getter.builder().methodName("getProtectedFieldWithGetAndAdd").propertyName("protectedFieldWithGetAndAdd").propertyType(new CollectionType(parameterize(List.class, String.class), String.class)).visibility(PROTECTED).declaringClass(DirectFieldAccess.class).build(), //
                                Getter.builder().methodName("getPublicFieldWithGetAndAdd").propertyName("publicFieldWithGetAndAdd").propertyType(new CollectionType(parameterize(List.class, String.class), String.class)).visibility(PUBLIC).declaringClass(DirectFieldAccess.class).build(),
                                FieldAccessor.builder().propertyName("publicFieldWithPrivateSetter").propertyType(new SimpleType(int.class)).visibility(PUBLIC).declaringClass(DirectFieldAccess.class).build(),
                                FieldAccessor.builder().propertyName("publicFinalFieldNoSetter").propertyType(new CollectionType(parameterize(List.class, String.class), String.class)).visibility(PUBLIC).isFinal(true).declaringClass(DirectFieldAccess.class).build())),
                Arguments.of("set", "get", true, true, true, classInfo.get(WithAdders.class), //
                        Set.<WriteAccessor>of( //
                                Adder.builder()
                                        .methodName("addHasAdder")
                                        .propertyName("hasAdders")
                                        .propertyType(new CollectionType(parameterize(List.class, String.class), String.class))
                                        .paramName("hasAdder")
                                        .paramType(new SimpleType(String.class))
                                        .visibility(PUBLIC)
                                        .declaringClass(WithAdders.class)
                                        .build(), //
                                Adder.builder()
                                        .methodName("addAlsoHasAdder")
                                        .propertyName("alsoHasAdders")
                                        .propertyType(new CollectionType(parameterize(List.class, parameterize(List.class, Object.class)), parameterize(List.class, Object.class)))
                                        .paramName("alsoHasAdder")
                                        .paramType(new CollectionType(parameterize(List.class, Object.class), Object.class))
                                        .visibility(PACKAGE_PRIVATE)
                                        .declaringClass(WithAdders.class)
                                        .build(), //
                                Setter.builder() //
                                        .methodName("setHasInaccessibleAdders") //
                                        .propertyName("hasInaccessibleAdders") //
                                        .propertyType(new CollectionType(parameterize(List.class, parameterize(Map.class, String.class, String.class)), parameterize(Map.class, String.class, String.class))) //
                                        .visibility(PUBLIC) //
                                        .declaringClass(WithAdders.class) //
                                        .build(), //
                                Setter.builder() //
                                        .methodName("setHasNoAdders") //
                                        .propertyName("hasNoAdders") //
                                        .propertyType(new CollectionType(parameterize(List.class, String.class), String.class)) //
                                        .visibility(PUBLIC) //
                                        .declaringClass(WithAdders.class) //
                                        .build())),
                Arguments.of("set", "get", true, true, false, classInfo.get(WithAdders.class), //
                        Set.<WriteAccessor>of( //
                                Setter.builder()
                                        .methodName("setHasAdders")
                                        .propertyName("hasAdders")
                                        .propertyType(new CollectionType(parameterize(List.class, String.class), String.class))
                                        .visibility(PUBLIC)
                                        .declaringClass(WithAdders.class)
                                        .build(), //
                                Setter.builder()
                                        .methodName("setAlsoHasAdders")
                                        .propertyName("alsoHasAdders")
                                        .propertyType(new CollectionType(parameterize(Set.class, parameterize(List.class, Object.class)), parameterize(List.class, Object.class)))
                                        .visibility(PUBLIC)
                                        .declaringClass(WithAdders.class)
                                        .build(), //
                                Setter.builder() //
                                        .methodName("setHasInaccessibleAdders") //
                                        .propertyName("hasInaccessibleAdders") //
                                        .propertyType(new CollectionType(parameterize(List.class, parameterize(Map.class, String.class, String.class)), parameterize(Map.class, String.class, String.class))) //
                                        .visibility(PUBLIC) //
                                        .declaringClass(WithAdders.class) //
                                        .build(), //
                                Setter.builder() //
                                        .methodName("setHasNoAdders") //
                                        .propertyName("hasNoAdders") //
                                        .propertyType(new CollectionType(parameterize(List.class, String.class), String.class)) //
                                        .visibility(PUBLIC) //
                                        .declaringClass(WithAdders.class) //
                                        .build())));

    }

    @ParameterizedTest
    @MethodSource
    void testGatherAllSettersForClassWithHierarchy(final ClassInfo clazz, final Set<WriteAccessor> expected) {
        // Arrange
        when(properties.getSetterPrefix()).thenReturn("set");
        // Act
        final Set<WriteAccessor> actual = writeAccessorService.gatherAllWriteAccessors(clazz);
        // Assert
        assertThat(actual)
                .usingRecursiveComparison(RecursiveComparisonConfiguration.builder()
                        .withEqualsForFields(
                                (a, b) -> ((Type) a).getTypeName().equals(((Type) b).getTypeName()),
                                "propertyType.type",
                                "propertyType.typeArg",
                                "propertyType.keyType",
                                "propertyType.valueType")
                        .build())
                .isEqualTo(expected);
    }

    private static Stream<Arguments> testGatherAllSettersForClassWithHierarchy() {
        return Stream.of( //
                Arguments.of(
                        classInfo.get(ClassWithHierarchy.class),
                        Set.of( //
                                Setter.builder().methodName("setOne").propertyName("one").propertyType(new SimpleType(int.class)).visibility(PUBLIC).declaringClass(ClassWithHierarchy.class).build(), //
                                Setter.builder().methodName("setTwo").propertyName("two").propertyType(new SimpleType(int.class)).visibility(PACKAGE_PRIVATE).declaringClass(FirstSuperClass.class).build(), //
                                Setter.builder().methodName("setThree").propertyName("three").propertyType(new SimpleType(int.class)).visibility(PUBLIC).declaringClass(AnInterface.class).build(), //
                                Setter.builder().methodName("setFour").propertyName("four").propertyType(new SimpleType(int.class)).visibility(PUBLIC).declaringClass(SecondSuperClassInDifferentPackage.class).build(), //
                                Setter.builder().methodName("setSeven").propertyName("seven").propertyType(new SimpleType(int.class)).visibility(PROTECTED).declaringClass(TopLevelSuperClass.class).build(), //
                                Setter.builder().methodName("setEight").propertyName("eight").propertyType(new SimpleType(int.class)).visibility(PUBLIC).declaringClass(AnotherInterface.class).build())), //
                Arguments.of(
                        classInfo.get(GenericChild.class),
                        Set.of( //
                                Setter.builder().methodName("setList").propertyName("list").propertyType(new CollectionType(parameterize(List.class, String.class), String.class)).visibility(PUBLIC).declaringClass(GenericChild.class).build(), //
                                Setter.builder().methodName("setMap").propertyName("map").propertyType(new MapType(parameterize(Map.class, typeVariableS(), typeVariableT()), typeVariableS(), typeVariableT())).visibility(PUBLIC).declaringClass(GenericChild.class).build(), //
                                Setter.builder().methodName("setGeneric").propertyName("generic").propertyType(new SimpleType(parameterize(Generic.class, typeVariableT()))).visibility(PUBLIC).declaringClass(GenericChild.class).build(), //
                                Setter.builder().methodName("setOtherGeneric").propertyName("otherGeneric").propertyType(new SimpleType(parameterize(Generic.class, String.class))).visibility(PUBLIC).declaringClass(GenericParent.class).build())), //
                Arguments.of(
                        classInfo.get(GenericGrandChild.class),
                        Set.of( //
                                Setter.builder().methodName("setList").propertyName("list").propertyType(new CollectionType(parameterize(List.class, String.class), String.class)).visibility(PUBLIC).declaringClass(GenericChild.class).build(), //
                                Setter.builder().methodName("setMap").propertyName("map").propertyType(new MapType(parameterize(Map.class, Long.class, Boolean.class), Long.class, Boolean.class)).visibility(PUBLIC).declaringClass(GenericGrandChild.class).build(), //
                                Setter.builder().methodName("setGeneric").propertyName("generic").propertyType(new SimpleType(parameterize(Generic.class, Boolean.class))).visibility(PUBLIC).declaringClass(GenericGrandChild.class).build(),
                                Setter.builder().methodName("setOtherGeneric").propertyName("otherGeneric").propertyType(new SimpleType(parameterize(Generic.class, String.class))).visibility(PUBLIC).declaringClass(GenericParent.class).build()))); //
    }

    private static TypeVariable<?> typeVariableS() {
        return GenericParent.class.getTypeParameters()[1];
    }

    private static TypeVariable<?> typeVariableT() {
        return ClassWithCollections.class.getTypeParameters()[0];
    }

    private static TypeVariable<?> typeVariableU() {
        return ClassWithCollections.class.getTypeParameters()[1];
    }

    @Test
    void testIsSetterNull() {
        // Arrange
        final WriteAccessor writeAccessor = null;
        // Act
        final ThrowingCallable isSetter = () -> writeAccessorService.isSetter(writeAccessor);
        // Assert
        assertThatThrownBy(isSetter).isInstanceOf(NullPointerException.class);
    }

    @ParameterizedTest
    @MethodSource
    void testIsSetter(final WriteAccessor writeAccessor, final boolean expected) {
        // Act
        final boolean actual = writeAccessorService.isSetter(writeAccessor);
        // Assert
        assertThat(actual).isEqualTo(expected);
    }

    private static Stream<Arguments> testIsSetter() {
        return Stream.of(
                Arguments.of(
                        Setter.builder().methodName("setWeight").propertyName("weight").propertyType(new SimpleType(float.class)).visibility(PUBLIC).declaringClass(PetJaxb.class).build(),
                        true),
                Arguments.of(
                        Getter.builder().methodName("getWeight").propertyName("weight").propertyType(new SimpleType(float.class)).visibility(PUBLIC).declaringClass(PetJaxb.class).build(),
                        false),
                Arguments.of(
                        Getter.builder().methodName("getSiblings").propertyName("siblings").propertyType(new CollectionType(parameterize(List.class, PetJaxb.class), PetJaxb.class)).visibility(PUBLIC).declaringClass(PetJaxb.class).build(),
                        false));
    }

    @Test
    void testIsCollectionGetterNull() {
        // Arrange
        final WriteAccessor writeAccessor = null;
        // Act
        final ThrowingCallable isSetter = () -> writeAccessorService.isCollectionGetter(writeAccessor);
        // Assert
        assertThatThrownBy(isSetter).isInstanceOf(NullPointerException.class);
    }

    @ParameterizedTest
    @MethodSource
    void testIsCollectionGetter(final WriteAccessor writeAccessor, final boolean expected) {
        // Act
        final boolean actual = writeAccessorService.isCollectionGetter(writeAccessor);
        // Assert
        assertThat(actual).isEqualTo(expected);
    }

    private static Stream<Arguments> testIsCollectionGetter() {
        return Stream.of(
                Arguments.of(
                        Setter.builder().methodName("setWeight").propertyName("weight").propertyType(new SimpleType(float.class)).visibility(PUBLIC).declaringClass(PetJaxb.class).build(),
                        false),
                Arguments.of(
                        Getter.builder().methodName("getWeight").propertyName("weight").propertyType(new SimpleType(float.class)).visibility(PUBLIC).declaringClass(PetJaxb.class).build(),
                        false),
                Arguments.of(
                        Getter.builder().methodName("getSiblings").propertyName("siblings").propertyType(new CollectionType(parameterize(List.class, PetJaxb.class), PetJaxb.class)).visibility(PUBLIC).declaringClass(PetJaxb.class).build(),
                        true));
    }

    @ParameterizedTest
    @MethodSource
    void testEquivalentAccessorsNull(final WriteAccessor first, final WriteAccessor second) {
        // Act
        final ThrowingCallable equivalentAccessors = () -> writeAccessorService.equivalentAccessors(first, second);
        // Assert
        assertThatThrownBy(equivalentAccessors).isInstanceOf(NullPointerException.class);
    }

    private static Stream<Arguments> testEquivalentAccessorsNull() {
        final var writeAccessor = Setter.builder().methodName("setWeight").propertyName("weight").propertyType(new SimpleType(float.class)).visibility(PUBLIC).declaringClass(PetJaxb.class).build();
        return Stream.of(
                Arguments.of(null, null),
                Arguments.of(null, writeAccessor),
                Arguments.of(writeAccessor, null));
    }

    @CartesianTest
    @CartesianTest.MethodFactory("testEquivalentAccessorsTrue")
    void testEquivalentAccessorsTrue(final WriteAccessor first,
                                     final WriteAccessor second) {
        // Act
        final boolean actual = writeAccessorService.equivalentAccessors(first, second);
        // Assert
        assertThat(actual).isTrue();
    }

    @SuppressWarnings("unused")
    static ArgumentSets testEquivalentAccessorsTrue() {
        return ArgumentSets
                .argumentsForFirstParameter(writeAccessorsThatAreAllEqualWithEachOther()) //
                .argumentsForNextParameter(writeAccessorsThatAreAllEqualWithEachOther());
    }

    @CartesianTest
    @CartesianTest.MethodFactory("testEquivalentAccessorsFalse")
    void testEquivalentAccessorsFalse(final WriteAccessor first,
                                      final WriteAccessor second) {
        // Act
        final boolean actual = writeAccessorService.equivalentAccessors(first, second);
        // Assert
        assertThat(actual).isFalse();
    }

    @SuppressWarnings("unused")
    static ArgumentSets testEquivalentAccessorsFalse() {
        return ArgumentSets
                .argumentsForFirstParameter(writeAccessorsThatAreAllEqualWithEachOther()) //
                .argumentsForNextParameter(writeAccessorsThatAreNotEqualWithAnyOfTherOthersAbove());
    }

    private static Stream<WriteAccessor> writeAccessorsThatAreAllEqualWithEachOther() {
        return Stream.of(
                FieldAccessor.builder()
                        .propertyName("items")
                        .propertyType(new CollectionType(parameterize(Set.class, String.class), String.class))
                        .visibility(PUBLIC)
                        .declaringClass(Person.class)
                        .build(),
                Setter.builder()
                        .methodName("setItems")
                        .propertyName("items")
                        .propertyType(new CollectionType(parameterize(Set.class, String.class), String.class))
                        .visibility(PUBLIC)
                        .declaringClass(Person.class)
                        .build(),
                Getter.builder()
                        .methodName("getItems")
                        .propertyName("items")
                        .propertyType(new CollectionType(parameterize(Set.class, String.class), String.class))
                        .visibility(PUBLIC)
                        .declaringClass(Person.class)
                        .build(),
                Adder.builder()
                        .methodName("addItem")
                        .propertyName("items")
                        .propertyType(new CollectionType(parameterize(List.class, String.class), String.class))
                        .paramType(new SimpleType(String.class))
                        .paramName("item")
                        .visibility(PUBLIC)
                        .declaringClass(Person.class)
                        .build());
    }

    private static Stream<WriteAccessor> writeAccessorsThatAreNotEqualWithAnyOfTherOthersAbove() {
        return Stream.of(
                FieldAccessor.builder()
                        .propertyName("items")
                        .propertyType(new CollectionType(parameterize(List.class, Object.class), Object.class))
                        .visibility(PUBLIC)
                        .declaringClass(Person.class)
                        .build(),
                Setter.builder()
                        .methodName("setOtherItems")
                        .propertyName("otherItems")
                        .propertyType(new CollectionType(parameterize(Set.class, String.class), String.class))
                        .visibility(PUBLIC)
                        .declaringClass(Person.class)
                        .build(),
                Getter.builder()
                        .methodName("getItems")
                        .propertyName("items")
                        .propertyType(new MapType(parameterize(Map.class, String.class, String.class), String.class, String.class))
                        .visibility(PUBLIC)
                        .declaringClass(Person.class)
                        .build(),
                Adder.builder()
                        .methodName("addItem")
                        .propertyName("items")
                        .propertyType(new CollectionType(parameterize(List.class, Number.class), Number.class))
                        .paramType(new SimpleType(Number.class))
                        .paramName("item")
                        .visibility(PUBLIC)
                        .declaringClass(Person.class)
                        .build());
    }
}
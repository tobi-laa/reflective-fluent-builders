package io.github.tobi.laa.reflective.fluent.builders.service.api;

import io.github.classgraph.ClassInfo;
import io.github.tobi.laa.reflective.fluent.builders.model.*;
import io.github.tobi.laa.reflective.fluent.builders.props.api.BuildersProperties;
import io.github.tobi.laa.reflective.fluent.builders.test.ClassGraphExtension;
import io.github.tobi.laa.reflective.fluent.builders.test.InjectSpy;
import io.github.tobi.laa.reflective.fluent.builders.test.IntegrationTest;
import io.github.tobi.laa.reflective.fluent.builders.test.models.complex.ClassWithCollections;
import io.github.tobi.laa.reflective.fluent.builders.test.models.complex.GetAndAdd;
import io.github.tobi.laa.reflective.fluent.builders.test.models.complex.ListWithTwoParams;
import io.github.tobi.laa.reflective.fluent.builders.test.models.complex.MapWithThreeParams;
import io.github.tobi.laa.reflective.fluent.builders.test.models.complex.hierarchy.*;
import io.github.tobi.laa.reflective.fluent.builders.test.models.complex.hierarchy.generics.Generic;
import io.github.tobi.laa.reflective.fluent.builders.test.models.complex.hierarchy.generics.GenericChild;
import io.github.tobi.laa.reflective.fluent.builders.test.models.complex.hierarchy.generics.GenericGrandChild;
import io.github.tobi.laa.reflective.fluent.builders.test.models.complex.hierarchy.generics.GenericParent;
import io.github.tobi.laa.reflective.fluent.builders.test.models.complex.hierarchy.second.SecondSuperClassInDifferentPackage;
import io.github.tobi.laa.reflective.fluent.builders.test.models.jaxb.PersonJaxb;
import io.github.tobi.laa.reflective.fluent.builders.test.models.jaxb.PetJaxb;
import io.github.tobi.laa.reflective.fluent.builders.test.models.simple.SimpleClass;
import io.github.tobi.laa.reflective.fluent.builders.test.models.simple.SimpleClassNoSetPrefix;
import lombok.SneakyThrows;
import org.assertj.core.api.recursive.comparison.RecursiveComparisonConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import javax.inject.Inject;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.*;
import java.util.stream.Stream;

import static io.github.tobi.laa.reflective.fluent.builders.model.Visibility.*;
import static org.apache.commons.lang3.reflect.TypeUtils.parameterize;
import static org.apache.commons.lang3.reflect.TypeUtils.wildcardType;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
    void testGatherAllSetters(final String setterPrefix, final String getterPrefix, final boolean getAndAddEnabled, final ClassInfo clazz, final Set<WriteAccessor> expected) {
        // Arrange
        when(properties.getSetterPrefix()).thenReturn(setterPrefix);
        when(properties.isGetAndAddEnabled()).thenReturn(getAndAddEnabled);
        if (getAndAddEnabled) {
            when(properties.getGetterPrefix()).thenReturn(getterPrefix);
        }
        // Act
        final Set<WriteAccessor> actual = writeAccessorService.gatherAllWriteAccessors(clazz);
        // Assert
        assertThat(actual)
                .usingRecursiveComparison(RecursiveComparisonConfiguration.builder()
                        .withEqualsForFields(
                                (a, b) -> ((Type) a).getTypeName().equals(((Type) b).getTypeName()),
                                "propertyType", "paramTypeArg", "keyType", "valueType")
                        .build())
                .isEqualTo(expected);
    }

    @SneakyThrows
    private static Stream<Arguments> testGatherAllSetters() {
        final var setOfList = ClassWithCollections.class.getDeclaredField("set").getGenericType();
        return Stream.of( //
                Arguments.of("set", null, false, classInfo.get(SimpleClass.class), //
                        Set.of( //
                                SimpleSetter.builder().methodName("setAnInt").propertyName("anInt").propertyType(int.class).visibility(PUBLIC).declaringClass(SimpleClass.class).build(), //
                                SimpleSetter.builder().methodName("setAString").propertyName("aString").propertyType(String.class).visibility(PUBLIC).declaringClass(SimpleClass.class).build(), //
                                SimpleSetter.builder().methodName("setBooleanField").propertyName("booleanField").propertyType(boolean.class).visibility(PUBLIC).declaringClass(SimpleClass.class).build(), //
                                SimpleSetter.builder().methodName("setSetClass").propertyName("setClass").propertyType(parameterize(Class.class, wildcardType().withUpperBounds(Object.class).build())).visibility(PUBLIC).declaringClass(SimpleClass.class).build())), //
                Arguments.of("", null, false, classInfo.get(SimpleClassNoSetPrefix.class), //
                        Set.of( //
                                SimpleSetter.builder().methodName("anInt").propertyName("anInt").propertyType(int.class).visibility(PACKAGE_PRIVATE).declaringClass(SimpleClassNoSetPrefix.class).build(), //
                                SimpleSetter.builder().methodName("aString").propertyName("aString").propertyType(String.class).visibility(PACKAGE_PRIVATE).declaringClass(SimpleClassNoSetPrefix.class).build())), //
                Arguments.of("set", "get", false, classInfo.get(ClassWithCollections.class), //
                        Set.of( //
                                CollectionSetter.builder().methodName("setInts").propertyName("ints").propertyType(parameterize(Collection.class, Integer.class)).paramTypeArg(Integer.class).visibility(PUBLIC).declaringClass(ClassWithCollections.class).build(), //
                                CollectionSetter.builder().methodName("setList").propertyName("list").propertyType(List.class).paramTypeArg(Object.class).visibility(PUBLIC).declaringClass(ClassWithCollections.class).build(), //
                                CollectionSetter.builder().methodName("setSet").propertyName("set").propertyType(setOfList).paramTypeArg(List.class).visibility(PUBLIC).declaringClass(ClassWithCollections.class).build(), //
                                CollectionSetter.builder().methodName("setDeque").propertyName("deque").propertyType(parameterize(Deque.class, wildcardType().withUpperBounds(Object.class).build())).paramTypeArg(wildcardType().withUpperBounds(Object.class).build()).visibility(PUBLIC).declaringClass(ClassWithCollections.class).build(), //
                                CollectionSetter.builder().methodName("setSortedSetWild").propertyName("sortedSetWild").propertyType(parameterize(SortedSet.class, wildcardType().build())).paramTypeArg(wildcardType().build()).visibility(PUBLIC).declaringClass(ClassWithCollections.class).build(), //
                                ArraySetter.builder().methodName("setFloats").propertyName("floats").propertyType(float[].class).paramComponentType(float.class).visibility(PUBLIC).declaringClass(ClassWithCollections.class).build(), //
                                MapSetter.builder().methodName("setMap").propertyName("map").propertyType(parameterize(Map.class, String.class, Object.class)).keyType(String.class).valueType(Object.class).visibility(PUBLIC).declaringClass(ClassWithCollections.class).build(), //
                                MapSetter.builder().methodName("setMapTU").propertyName("mapTU").propertyType(parameterize(Map.class, typeVariableT(), typeVariableU())).keyType(typeVariableT()).valueType(typeVariableU()).visibility(PUBLIC).declaringClass(ClassWithCollections.class).build(), //
                                MapSetter.builder().methodName("setMapWildObj").propertyName("mapWildObj").propertyType(parameterize(Map.class, wildcardType().build(), Object.class)).keyType(wildcardType().build()).valueType(Object.class).visibility(PUBLIC).declaringClass(ClassWithCollections.class).build(), //
                                MapSetter.builder().methodName("setMapNoTypeArgs").propertyName("mapNoTypeArgs").propertyType(Map.class).keyType(Object.class).valueType(Object.class).visibility(PUBLIC).declaringClass(ClassWithCollections.class).build(), //
                                CollectionSetter.builder().methodName("setListWithTwoParams").propertyName("listWithTwoParams").propertyType(parameterize(ListWithTwoParams.class, String.class, Integer.class)).paramTypeArg(parameterize(Map.class, String.class, Integer.class)).visibility(PUBLIC).declaringClass(ClassWithCollections.class).build(), //
                                MapSetter.builder().methodName("setMapWithThreeParams").propertyName("mapWithThreeParams").propertyType(parameterize(MapWithThreeParams.class, String.class, Integer.class, Boolean.class)).keyType(String.class).valueType(parameterize(Map.class, Integer.class, Boolean.class)).visibility(PUBLIC).declaringClass(ClassWithCollections.class).build())), //
                Arguments.of("set", "get", false, classInfo.get(PetJaxb.class), //
                        Set.of( //
                                SimpleSetter.builder().methodName("setFullName").propertyName("fullName").propertyType(String.class).visibility(PUBLIC).declaringClass(PetJaxb.class).build(), //
                                SimpleSetter.builder().methodName("setWeight").propertyName("weight").propertyType(float.class).visibility(PUBLIC).declaringClass(PetJaxb.class).build(), //
                                SimpleSetter.builder().methodName("setOwner").propertyName("owner").propertyType(PersonJaxb.class).visibility(PUBLIC).declaringClass(PetJaxb.class).build())),
                Arguments.of("set", "get", true, classInfo.get(PetJaxb.class), //
                        Set.of( //
                                SimpleSetter.builder().methodName("setFullName").propertyName("fullName").propertyType(String.class).visibility(PUBLIC).declaringClass(PetJaxb.class).build(), //
                                SimpleSetter.builder().methodName("setWeight").propertyName("weight").propertyType(float.class).visibility(PUBLIC).declaringClass(PetJaxb.class).build(), //
                                CollectionGetAndAdder.builder().methodName("getSiblings").propertyName("siblings").propertyType(parameterize(List.class, PetJaxb.class)).paramTypeArg(PetJaxb.class).visibility(PUBLIC).declaringClass(PetJaxb.class).build(), //
                                SimpleSetter.builder().methodName("setOwner").propertyName("owner").propertyType(PersonJaxb.class).visibility(PUBLIC).declaringClass(PetJaxb.class).build())),
                Arguments.of("set", "myPrefix", true, classInfo.get(PetJaxb.class), //
                        Set.of( //
                                SimpleSetter.builder().methodName("setFullName").propertyName("fullName").propertyType(String.class).visibility(PUBLIC).declaringClass(PetJaxb.class).build(), //
                                SimpleSetter.builder().methodName("setWeight").propertyName("weight").propertyType(float.class).visibility(PUBLIC).declaringClass(PetJaxb.class).build(), //
                                SimpleSetter.builder().methodName("setOwner").propertyName("owner").propertyType(PersonJaxb.class).visibility(PUBLIC).declaringClass(PetJaxb.class).build())), //
                Arguments.of("set", "get", true, classInfo.get(GetAndAdd.class), //
                        Set.of( //
                                CollectionSetter.builder().methodName("setListGetterAndSetter").propertyName("listGetterAndSetter").propertyType(parameterize(List.class, String.class)).paramTypeArg(String.class).visibility(PUBLIC).declaringClass(GetAndAdd.class).build(), //
                                CollectionGetAndAdder.builder().methodName("getListNoSetter").propertyName("listNoSetter").propertyType(parameterize(List.class, String.class)).paramTypeArg(String.class).visibility(PUBLIC).declaringClass(GetAndAdd.class).build(), //
                                CollectionSetter.builder().methodName("setListNoGetter").propertyName("listNoGetter").propertyType(parameterize(List.class, String.class)).paramTypeArg(String.class).visibility(PUBLIC).declaringClass(GetAndAdd.class).build(), //
                                CollectionGetAndAdder.builder().methodName("getListSetterWrongType").propertyName("listSetterWrongType").propertyType(parameterize(List.class, String.class)).paramTypeArg(String.class).visibility(PUBLIC).declaringClass(GetAndAdd.class).build(), //
                                ArraySetter.builder().methodName("setListSetterWrongType").propertyName("listSetterWrongType").propertyType(String[].class).paramComponentType(String.class).visibility(PUBLIC).declaringClass(GetAndAdd.class).build())));
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
                                "propertyType", "paramTypeArg", "keyType", "valueType")
                        .build())
                .isEqualTo(expected);
    }

    private static Stream<Arguments> testGatherAllSettersForClassWithHierarchy() {
        return Stream.of( //
                Arguments.of(
                        classInfo.get(ClassWithHierarchy.class),
                        Set.of( //
                                SimpleSetter.builder().methodName("setOne").propertyName("one").propertyType(int.class).visibility(PUBLIC).declaringClass(ClassWithHierarchy.class).build(), //
                                SimpleSetter.builder().methodName("setTwo").propertyName("two").propertyType(int.class).visibility(PACKAGE_PRIVATE).declaringClass(FirstSuperClass.class).build(), //
                                SimpleSetter.builder().methodName("setThree").propertyName("three").propertyType(int.class).visibility(PUBLIC).declaringClass(AnInterface.class).build(), //
                                SimpleSetter.builder().methodName("setFour").propertyName("four").propertyType(int.class).visibility(PUBLIC).declaringClass(SecondSuperClassInDifferentPackage.class).build(), //
                                SimpleSetter.builder().methodName("setSeven").propertyName("seven").propertyType(int.class).visibility(PROTECTED).declaringClass(TopLevelSuperClass.class).build(), //
                                SimpleSetter.builder().methodName("setEight").propertyName("eight").propertyType(int.class).visibility(PUBLIC).declaringClass(AnotherInterface.class).build())), //
                Arguments.of(
                        classInfo.get(GenericChild.class),
                        Set.of( //
                                CollectionSetter.builder().methodName("setList").propertyName("list").propertyType(parameterize(List.class, String.class)).paramTypeArg(String.class).visibility(PUBLIC).declaringClass(GenericChild.class).build(), //
                                MapSetter.builder().methodName("setMap").propertyName("map").propertyType(parameterize(Map.class, typeVariableS(), typeVariableT())).keyType(typeVariableS()).valueType(typeVariableT()).visibility(PUBLIC).declaringClass(GenericChild.class).build(), //
                                SimpleSetter.builder().methodName("setGeneric").propertyName("generic").propertyType(parameterize(Generic.class, typeVariableT())).visibility(PUBLIC).declaringClass(GenericChild.class).build(), //
                                SimpleSetter.builder().methodName("setOtherGeneric").propertyName("otherGeneric").propertyType(parameterize(Generic.class, String.class)).visibility(PUBLIC).declaringClass(GenericParent.class).build())), //
                Arguments.of(
                        classInfo.get(GenericGrandChild.class),
                        Set.of( //
                                CollectionSetter.builder().methodName("setList").propertyName("list").propertyType(parameterize(List.class, String.class)).paramTypeArg(String.class).visibility(PUBLIC).declaringClass(GenericChild.class).build(), //
                                MapSetter.builder().methodName("setMap").propertyName("map").propertyType(parameterize(Map.class, Long.class, Boolean.class)).keyType(Long.class).valueType(Boolean.class).visibility(PUBLIC).declaringClass(GenericGrandChild.class).build(), //
                                SimpleSetter.builder().methodName("setGeneric").propertyName("generic").propertyType(parameterize(Generic.class, Boolean.class)).visibility(PUBLIC).declaringClass(GenericGrandChild.class).build(),
                                SimpleSetter.builder().methodName("setOtherGeneric").propertyName("otherGeneric").propertyType(parameterize(Generic.class, String.class)).visibility(PUBLIC).declaringClass(GenericParent.class).build()))); //
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
}
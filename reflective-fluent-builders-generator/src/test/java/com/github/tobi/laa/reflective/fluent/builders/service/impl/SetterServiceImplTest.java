package com.github.tobi.laa.reflective.fluent.builders.service.impl;

import com.github.tobi.laa.reflective.fluent.builders.model.*;
import com.github.tobi.laa.reflective.fluent.builders.props.api.BuildersProperties;
import com.github.tobi.laa.reflective.fluent.builders.service.api.ClassService;
import com.github.tobi.laa.reflective.fluent.builders.service.api.VisibilityService;
import com.github.tobi.laa.reflective.fluent.builders.test.models.complex.ClassWithCollections;
import com.github.tobi.laa.reflective.fluent.builders.test.models.complex.GetAndAdd;
import com.github.tobi.laa.reflective.fluent.builders.test.models.complex.hierarchy.*;
import com.github.tobi.laa.reflective.fluent.builders.test.models.jaxb.PersonJaxb;
import com.github.tobi.laa.reflective.fluent.builders.test.models.jaxb.PetJaxb;
import com.github.tobi.laa.reflective.fluent.builders.test.models.simple.SimpleClass;
import com.github.tobi.laa.reflective.fluent.builders.test.models.simple.SimpleClassNoSetPrefix;
import com.google.common.collect.ImmutableSet;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.TypeUtils;
import org.assertj.core.api.recursive.comparison.RecursiveComparisonConfiguration;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SetterServiceImplTest {

    @InjectMocks
    private SetterServiceImpl setterService;

    @Mock
    private VisibilityService visibilityService;

    @Mock
    private ClassService classService;

    @Mock
    private BuildersProperties properties;

    @Test
    void testDropSetterPrefixNull() {
        // Act
        final Executable dropSetterPrefix = () -> setterService.dropSetterPrefix(null);
        // Assert
        assertThrows(NullPointerException.class, dropSetterPrefix);
    }

    @ParameterizedTest
    @MethodSource
    void testDropSetterPrefix(final String setterPrefix, final String name, final String expected) {
        // Arrange
        when(properties.getSetterPrefix()).thenReturn(setterPrefix);
        // Act
        final String actual = setterService.dropSetterPrefix(name);
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
        final Executable dropGetterPrefix = () -> setterService.dropGetterPrefix(null);
        // Assert
        assertThrows(NullPointerException.class, dropGetterPrefix);
    }

    @ParameterizedTest
    @MethodSource
    void testDropGetterPrefix(final String getterPrefix, final String name, final String expected) {
        // Arrange
        when(properties.getGetterPrefix()).thenReturn(getterPrefix);
        // Act
        final String actual = setterService.dropGetterPrefix(name);
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
        final Executable gatherAllSetters = () -> setterService.gatherAllSetters(null);
        // Assert
        assertThrows(NullPointerException.class, gatherAllSetters);
    }

    @ParameterizedTest
    @MethodSource
    void testGatherAllSetters(final String setterPrefix, final String getterPrefix, final boolean getAndAddEnabled, final Class<?> clazz, final Visibility mockVisibility, final Set<Setter> expected) {
        // Arrange
        when(properties.getSetterPrefix()).thenReturn(setterPrefix);
        when(properties.isGetAndAddEnabled()).thenReturn(getAndAddEnabled);
        if (getAndAddEnabled) {
            when(properties.getGetterPrefix()).thenReturn(getterPrefix);
        }
        when(visibilityService.toVisibility(anyInt())).thenReturn(mockVisibility);
        when(classService.collectFullClassHierarchy(clazz)).thenReturn(Collections.singleton(clazz));
        // Act
        final Set<Setter> actual = setterService.gatherAllSetters(clazz);
        // Assert
        assertThat(actual)
                .usingRecursiveComparison(RecursiveComparisonConfiguration.builder()
                        .withEqualsForFields(
                                (a, b) -> ((Type) a).getTypeName().equals(((Type) b).getTypeName()),
                                "paramTypeArg", "keyType", "valueType")
                        .build())
                .isEqualTo(expected);
        verify(visibilityService, times(expected.size())).toVisibility(anyInt());
    }

    private static Stream<Arguments> testGatherAllSetters() {
        return Stream.of( //
                Arguments.of("set", null, false, SimpleClass.class, Visibility.PUBLIC, //
                        ImmutableSet.of( //
                                SimpleSetter.builder().methodName("setAnInt").paramName("anInt").paramType(int.class).visibility(Visibility.PUBLIC).build(), //
                                SimpleSetter.builder().methodName("setAString").paramName("aString").paramType(String.class).visibility(Visibility.PUBLIC).build(), //
                                SimpleSetter.builder().methodName("setBooleanField").paramName("booleanField").paramType(boolean.class).visibility(Visibility.PUBLIC).build(), //
                                SimpleSetter.builder().methodName("setSetClass").paramName("setClass").paramType(Class.class).visibility(Visibility.PUBLIC).build())), //
                Arguments.of("", null, false, SimpleClassNoSetPrefix.class, Visibility.PACKAGE_PRIVATE, //
                        ImmutableSet.of( //
                                SimpleSetter.builder().methodName("anInt").paramName("anInt").paramType(int.class).visibility(Visibility.PACKAGE_PRIVATE).build(), //
                                SimpleSetter.builder().methodName("aString").paramName("aString").paramType(String.class).visibility(Visibility.PACKAGE_PRIVATE).build())), //
                Arguments.of("set", "get", false, ClassWithCollections.class, Visibility.PRIVATE, //
                        ImmutableSet.of( //
                                CollectionSetter.builder().methodName("setInts").paramName("ints").paramType(Collection.class).paramTypeArg(Integer.class).visibility(Visibility.PRIVATE).build(), //
                                CollectionSetter.builder().methodName("setList").paramName("list").paramType(List.class).paramTypeArg(Object.class).visibility(Visibility.PRIVATE).build(), //
                                CollectionSetter.builder().methodName("setSet").paramName("set").paramType(Set.class).paramTypeArg(List.class).visibility(Visibility.PRIVATE).build(), //
                                CollectionSetter.builder().methodName("setDeque").paramName("deque").paramType(Deque.class).paramTypeArg(TypeUtils.wildcardType().withUpperBounds(Object.class).build()).visibility(Visibility.PRIVATE).build(), //
                                CollectionSetter.builder().methodName("setSortedSetWild").paramName("sortedSetWild").paramType(SortedSet.class).paramTypeArg(TypeUtils.wildcardType().build()).visibility(Visibility.PRIVATE).build(), //
                                ArraySetter.builder().methodName("setFloats").paramName("floats").paramType(float[].class).paramComponentType(float.class).visibility(Visibility.PRIVATE).build(), //
                                MapSetter.builder().methodName("setMap").paramName("map").paramType(Map.class).keyType(String.class).valueType(Object.class).visibility(Visibility.PRIVATE).build(), //
                                MapSetter.builder().methodName("setMapTU").paramName("mapTU").paramType(Map.class).keyType(typeVariableT()).valueType(typeVariableU()).visibility(Visibility.PRIVATE).build(), //
                                MapSetter.builder().methodName("setMapWildObj").paramName("mapWildObj").paramType(Map.class).keyType(TypeUtils.wildcardType().build()).valueType(Object.class).visibility(Visibility.PRIVATE).build(), //
                                MapSetter.builder().methodName("setMapNoTypeArgs").paramName("mapNoTypeArgs").paramType(Map.class).keyType(Object.class).valueType(Object.class).visibility(Visibility.PRIVATE).build())), //
                Arguments.of("set", "get", false, PetJaxb.class, Visibility.PRIVATE, //
                        Set.of( //
                                SimpleSetter.builder().methodName("setFullName").paramName("fullName").paramType(String.class).visibility(Visibility.PRIVATE).build(), //
                                SimpleSetter.builder().methodName("setWeight").paramName("weight").paramType(float.class).visibility(Visibility.PRIVATE).build(), //
                                SimpleSetter.builder().methodName("setOwner").paramName("owner").paramType(PersonJaxb.class).visibility(Visibility.PRIVATE).build())),
                Arguments.of("set", "get", true, PetJaxb.class, Visibility.PRIVATE, //
                        Set.of( //
                                SimpleSetter.builder().methodName("setFullName").paramName("fullName").paramType(String.class).visibility(Visibility.PRIVATE).build(), //
                                SimpleSetter.builder().methodName("setWeight").paramName("weight").paramType(float.class).visibility(Visibility.PRIVATE).build(), //
                                CollectionGetAndAdder.builder().methodName("getSiblings").paramName("siblings").paramType(List.class).paramTypeArg(PetJaxb.class).visibility(Visibility.PRIVATE).build(), //
                                SimpleSetter.builder().methodName("setOwner").paramName("owner").paramType(PersonJaxb.class).visibility(Visibility.PRIVATE).build())),
                Arguments.of("set", "myPrefix", true, PetJaxb.class, Visibility.PRIVATE, //
                        Set.of( //
                                SimpleSetter.builder().methodName("setFullName").paramName("fullName").paramType(String.class).visibility(Visibility.PRIVATE).build(), //
                                SimpleSetter.builder().methodName("setWeight").paramName("weight").paramType(float.class).visibility(Visibility.PRIVATE).build(), //
                                SimpleSetter.builder().methodName("setOwner").paramName("owner").paramType(PersonJaxb.class).visibility(Visibility.PRIVATE).build())), //
                Arguments.of("set", "get", true, GetAndAdd.class, Visibility.PUBLIC, //
                        Set.of( //
                                CollectionSetter.builder().methodName("setListGetterAndSetter").paramName("listGetterAndSetter").paramType(List.class).paramTypeArg(String.class).visibility(Visibility.PUBLIC).build(), //
                                CollectionGetAndAdder.builder().methodName("getListNoSetter").paramName("listNoSetter").paramType(List.class).paramTypeArg(String.class).visibility(Visibility.PUBLIC).build(), //
                                CollectionSetter.builder().methodName("setListNoGetter").paramName("listNoGetter").paramType(List.class).paramTypeArg(String.class).visibility(Visibility.PUBLIC).build(), //
                                CollectionGetAndAdder.builder().methodName("getListSetterWrongType").paramName("listSetterWrongType").paramType(List.class).paramTypeArg(String.class).visibility(Visibility.PUBLIC).build(), //
                                ArraySetter.builder().methodName("setListSetterWrongType").paramName("listSetterWrongType").paramType(String[].class).paramComponentType(String.class).visibility(Visibility.PUBLIC).build())));
    }

    @Test
    void testGatherAllSettersForClassWithHierarchy() {
        // Arrange
        when(properties.getSetterPrefix()).thenReturn("set");
        when(visibilityService.toVisibility(anyInt())).thenReturn(Visibility.PROTECTED);
        when(classService.collectFullClassHierarchy(any())).thenReturn(ImmutableSet.of(ClassWithHierarchy.class, FirstSuperClass.class, TopLevelSuperClass.class, AnInterface.class, AnotherInterface.class));
        // Act
        final Set<Setter> actual = setterService.gatherAllSetters(ClassWithHierarchy.class);
        // Assert
        assertThat(actual)
                .usingRecursiveComparison(RecursiveComparisonConfiguration.builder()
                        .withEqualsForType((a, b) -> true, WildcardType.class)
                        .withEqualsForType((a, b) -> a.getTypeName().equals(b.getTypeName()), TypeVariable.class)
                        .build())
                .isEqualTo(Stream.of("setOne", "setTwo", "setThree", "setFour", "setFive") //
                        .map(name -> SimpleSetter.builder().methodName(name).paramName(StringUtils.uncapitalize(name.substring(3))).paramType(int.class).visibility(Visibility.PROTECTED).build()) //
                        .collect(Collectors.toSet()));
        verify(visibilityService, times(5)).toVisibility(anyInt());
    }

    private static TypeVariable<?> typeVariableT() {
        return ClassWithCollections.class.getTypeParameters()[0];
    }

    private static TypeVariable<?> typeVariableU() {
        return ClassWithCollections.class.getTypeParameters()[1];
    }
}
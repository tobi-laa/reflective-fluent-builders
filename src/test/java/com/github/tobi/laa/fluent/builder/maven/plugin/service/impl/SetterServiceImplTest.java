package com.github.tobi.laa.fluent.builder.maven.plugin.service.impl;

import static org.assertj.core.api.Assertions.*;

import static com.github.tobi.laa.fluent.builder.maven.plugin.model.Visibility.*;
import static org.mockito.Mockito.*;

import com.github.tobi.laa.fluent.builder.maven.plugin.model.*;
import com.github.tobi.laa.fluent.builder.maven.plugin.service.api.VisibilityService;
import lombok.AccessLevel;
import lombok.Data;
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

import javax.lang.model.type.ArrayType;
import javax.lang.model.type.TypeMirror;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class SetterServiceImplTest {

    @Mock
    private VisibilityService visibilityService;

    @Test
    void gatherAllSettersNull() {
        // Arrange
        final SetterServiceImpl setterService = new SetterServiceImpl(visibilityService, "");
        // Act
        final Executable gatherAllSetters = () -> setterService.gatherAllSetters(null);
        // Assert
        assertThrows(NullPointerException.class, gatherAllSetters);
    }

    @ParameterizedTest
    @MethodSource
    void gatherAllSetters(final String setterPrefix, final Class<?> clazz, final Visibility mockVisibility, final Set<Setter> expected) {
        // Arrange
        final SetterServiceImpl setterService = new SetterServiceImpl(visibilityService, setterPrefix);
        when(visibilityService.toVisibility(anyInt())).thenReturn(mockVisibility);
        // Act
        final Set<Setter> actual = setterService.gatherAllSetters(clazz);
        // Assert
        assertThat(actual)
                .usingRecursiveComparison(RecursiveComparisonConfiguration.builder()
                        .withEqualsForType((a, b) -> true, WildcardType.class)
                        .withEqualsForType((a, b) -> a.getTypeName().equals(b.getTypeName()), TypeVariable.class)
                        .build())
                .isEqualTo(expected);
        verify(visibilityService, times(expected.size())).toVisibility(anyInt());
    }

    private static Stream<Arguments> gatherAllSetters() {
        return Stream.of( //
                Arguments.of("set", SimpleClass.class, PUBLIC, //
                        Set.of( //
                                SimpleSetter.builder().methodName("setAnInt").paramName("anInt").paramType(int.class).visibility(PUBLIC).build(), //
                                SimpleSetter.builder().methodName("setAString").paramName("aString").paramType(String.class).visibility(PUBLIC).build(), //
                                SimpleSetter.builder().methodName("setBooleanField").paramName("booleanField").paramType(boolean.class).visibility(PUBLIC).build(), //
                                SimpleSetter.builder().methodName("setSetClass").paramName("setClass").paramType(Class.class).visibility(PUBLIC).build())), //
                Arguments.of("", SimpleClassNoSetPrefix.class, PACKAGE_PRIVATE, //
                        Set.of( //
                                SimpleSetter.builder().methodName("anInt").paramName("anInt").paramType(int.class).visibility(PACKAGE_PRIVATE).build(), //
                                SimpleSetter.builder().methodName("aString").paramName("aString").paramType(String.class).visibility(PACKAGE_PRIVATE).build())), //
                Arguments.of("set", ClassWithCollections.class, PRIVATE, //
                        Set.of( //
                                CollectionSetter.builder().methodName("setInts").paramName("ints").paramType(Collection.class).paramTypeArg(Integer.class).visibility(PRIVATE).build(), //
                                CollectionSetter.builder().methodName("setList").paramName("list").paramType(List.class).paramTypeArg(Object.class).visibility(PRIVATE).build(),
                                CollectionSetter.builder().methodName("setSet").paramName("set").paramType(Set.class).paramTypeArg(List.class).visibility(PRIVATE).build(),
                                CollectionSetter.builder().methodName("setDeque").paramName("deque").paramType(Deque.class).paramTypeArg(TypeUtils.wildcardType().build()).visibility(PRIVATE).build(),
                                ArraySetter.builder().methodName("setFloats").paramName("floats").paramType(float[].class).paramComponentType(float.class).visibility(PRIVATE).build(),
                                MapSetter.builder().methodName("setMap").paramName("map").paramType(Map.class).keyType(String.class).valueType(Object.class).visibility(PRIVATE).build(),
                                MapSetter.builder().methodName("setMapWildT").paramName("mapWildT").paramType(Map.class).keyType(TypeUtils.wildcardType().build()).valueType(typeVariableT()).visibility(PRIVATE).build(),
                                MapSetter.builder().methodName("setMapNoTypeArgs").paramName("mapNoTypeArgs").paramType(Map.class).keyType(Object.class).valueType(Object.class).visibility(PRIVATE).build())), //
                Arguments.of("set", ClassWithHierarchy.class, PROTECTED, //
                        Stream.of("setOne", "setTwo", "setThree", "setFour", "setFive") //
                                .map(name -> SimpleSetter.builder().methodName(name).paramName(StringUtils.uncapitalize(name.substring(3))).paramType(int.class).visibility(PROTECTED).build()) //
                                .collect(Collectors.toSet())) //
        );
    }

    private static TypeVariable typeVariableT() {
        return ClassWithCollections.class.getTypeParameters()[0];
    }

    @lombok.Setter
    class SimpleClass {
        int anInt;
        String aString;
        boolean booleanField;
        Class<?> setClass;

        void anInt(final int anInt) {
            this.anInt = anInt;
        }

        void aString(final String aString) {
            this.aString = aString;
        }
    }

    class SimpleClassNoSetPrefix {
        int anInt;
        String aString;

        void anInt(final int anInt) {
            this.anInt = anInt;
        }

        void aString(final String aString) {
            this.aString = aString;
        }
    }

    @lombok.Setter
    class ClassWithCollections<T> {
        Collection<Integer> ints;
        List list;
        java.util.Set<List> set;
        Deque<?> deque;
        float[] floats;
        Map<String, Object> map;
        Map<?, T> mapWildT;
        Map mapNoTypeArgs;
    }

    @lombok.Setter
    class ClassWithHierarchy extends FirstSuperClass implements AnInterface {
        int one;
    }

    @lombok.Setter
    class FirstSuperClass extends TopLevelSuperClass {
        int two;
    }

    abstract class TopLevelSuperClass implements AnotherInterface {
        @lombok.Setter
        int three;
    }

    interface AnInterface {
        default void setFour(final int four) {}
    }

    interface AnotherInterface {
        default void setFive(final int five) {}
    }
}
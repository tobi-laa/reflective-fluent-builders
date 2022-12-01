package com.github.tobi.laa.fluent.builder.maven.plugin.service.impl;

import static com.github.tobi.laa.fluent.builder.maven.plugin.model.Visibility.*;
import static org.mockito.Mockito.*;

import com.github.tobi.laa.fluent.builder.maven.plugin.model.*;
import com.github.tobi.laa.fluent.builder.maven.plugin.service.api.VisibilityService;
import lombok.AccessLevel;
import lombok.Data;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
        assertEquals(expected, actual);
        verify(visibilityService, times(expected.size())).toVisibility(anyInt());
    }

    private static Stream<Arguments> gatherAllSetters() {
        return Stream.of( //
                Arguments.of("set", SimpleClass.class, PUBLIC, //
                        Set.of( //
                                SimpleSetter.builder().methodName("setAnInt").paramType(int.class).visibility(PUBLIC).build(), //
                                SimpleSetter.builder().methodName("setAString").paramType(String.class).visibility(PUBLIC).build(), //
                                SimpleSetter.builder().methodName("setBooleanField").paramType(boolean.class).visibility(PUBLIC).build(), //
                                SimpleSetter.builder().methodName("setSetClass").paramType(Class.class).visibility(PUBLIC).build())), //
                Arguments.of("a", SimpleClass.class, PACKAGE_PRIVATE, //
                        Set.of( //
                                SimpleSetter.builder().methodName("anInt").paramType(int.class).visibility(PACKAGE_PRIVATE).build(), //
                                SimpleSetter.builder().methodName("aString").paramType(String.class).visibility(PACKAGE_PRIVATE).build())), //
                Arguments.of("set", ClassWithCollections.class, PRIVATE, //
                        Set.of( //
                                CollectionSetter.builder().methodName("setInts").paramType(Collection.class).paramTypeArg(Integer.class).visibility(PRIVATE).build(), //
                                CollectionSetter.builder().methodName("setStrings").paramType(List.class).paramTypeArg(String.class).visibility(PRIVATE).build(),
                                CollectionSetter.builder().methodName("setSet").paramType(Set.class).paramTypeArg(List.class).visibility(PRIVATE).build(),
                                ArraySetter.builder().methodName("setFloats").paramType(float[].class).paramComponentType(float.class).visibility(PRIVATE).build(),
                                MapSetter.builder().methodName("setMap").paramType(Map.class).keyType(String.class).valueType(Object.class).visibility(PRIVATE).build())), //
                Arguments.of("set", ClassWithHierarchy.class, PROTECTED, //
                        Stream.of("setOne", "setTwo", "setThree", "setFour", "setFive") //
                                .map(name -> SimpleSetter.builder().methodName(name).paramType(int.class).visibility(PROTECTED).build()) //
                                .collect(Collectors.toSet())) //
        );
    }

    class SimpleClass {
        @lombok.Setter
        int anInt;
        @lombok.Setter
        String aString;
        @lombok.Setter
        boolean booleanField;
        @lombok.Setter
        Class<?> setClass;

        void anInt(final int anInt) {
            this.anInt = anInt;
        }

        void aString(final String aString) {
            this.aString = aString;
        }
    }

    class ClassWithCollections {
        @lombok.Setter
        Collection<Integer> ints;
        @lombok.Setter
        List<String> strings;
        @lombok.Setter
        Set<List<?>> set;
        @lombok.Setter
        float[] floats;
        @lombok.Setter
        Map<String, Object> map;
    }

    class ClassWithHierarchy extends FirstSuperClass implements AnInterface {
        @lombok.Setter
        int one;
    }

    class FirstSuperClass extends TopLevelSuperClass {
        @lombok.Setter
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
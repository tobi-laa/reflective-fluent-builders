package io.github.tobi.laa.reflective.fluent.builders.mapper.impl;

import io.github.classgraph.ClassInfo;
import io.github.tobi.laa.reflective.fluent.builders.model.JavaType;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static io.github.tobi.laa.reflective.fluent.builders.model.JavaType.CLASS;
import static io.github.tobi.laa.reflective.fluent.builders.model.JavaType.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

class JavaTypeMapperImplTest {

    private final JavaTypeMapperImpl mapper = new JavaTypeMapperImpl();

    private ClassInfo givenClassInfo;

    private ThrowingCallable map;

    private JavaType actualJavaType;

    @BeforeEach
    void resetTestData() {
        givenClassInfo = null;
        map = null;
        actualJavaType = null;
    }

    @Test
    void classInfoNull_whenCallingMap_shouldThrowException() {
        givenClassInfoNull();
        whenCallingMap();
        thenNullPointerExceptionShouldBeThrown();
    }

    @Test
    void classInfoAbstract_whenCallingMap_shouldReturnAbstractClass() {
        givenClassInfo().isAbstract(true);
        whenCallingMap();
        thenJavaTypeReturnedShouldBe(ABSTRACT_CLASS);
    }

    @Test
    void classInfoAnonymousInnerClass_whenCallingMap_shouldReturnAnonymousClass() {
        givenClassInfo().anonymousInnerClass(true);
        whenCallingMap();
        thenJavaTypeReturnedShouldBe(ANONYMOUS_CLASS);
    }

    @Test
    void classInfoInterface_whenCallingMap_shouldReturnInterface() {
        givenClassInfo().isInterface(true);
        whenCallingMap();
        thenJavaTypeReturnedShouldBe(INTERFACE);
    }

    @Test
    void classInfoRecord_whenCallingMap_shouldReturnRecord() {
        givenClassInfo().isRecord(true);
        whenCallingMap();
        thenJavaTypeReturnedShouldBe(RECORD);
    }

    @Test
    void classInfoEnum_whenCallingMap_shouldReturnEnum() {
        givenClassInfo().isEnum(true);
        whenCallingMap();
        thenJavaTypeReturnedShouldBe(ENUM);
    }

    @ParameterizedTest
    @ValueSource(classes = {byte.class, short.class, int.class, long.class, char.class, float.class, double.class, boolean.class, void.class})
    void classInfoNamePrimitive_whenCallingMap_shouldReturnPrimitive(final Class<?> clazz) {
        givenClassInfo().name(clazz.getName());
        whenCallingMap();
        thenJavaTypeReturnedShouldBe(PRIMITIVE);
    }

    @ParameterizedTest
    @ValueSource(classes = {String.class, Object.class})
    void classInfoNameNotPrimitive_whenCallingMap_shouldReturnPrimitive(final Class<?> clazz) {
        givenClassInfo().name(clazz.getName());
        whenCallingMap();
        thenJavaTypeReturnedShouldBe(CLASS);
    }

    private void givenClassInfoNull() {
        this.givenClassInfo = null;
    }

    private ClassInfoStubbing givenClassInfo() {
        this.givenClassInfo = mock(ClassInfo.class);
        return new ClassInfoStubbing();
    }

    private void whenCallingMap() {
        map = () -> actualJavaType = mapper.map(givenClassInfo);
    }

    private void thenNullPointerExceptionShouldBeThrown() {
        assertThatThrownBy(map).isExactlyInstanceOf(NullPointerException.class);
    }

    private void thenJavaTypeReturnedShouldBe(final JavaType expected) {
        assertThatCode(map).doesNotThrowAnyException();
        assertThat(actualJavaType).isEqualTo(expected);
    }

    private class ClassInfoStubbing {

        ClassInfoStubbing isAbstract(final boolean isAbstract) {
            doReturn(isAbstract).when(givenClassInfo).isAbstract();
            return this;
        }

        ClassInfoStubbing anonymousInnerClass(final boolean anonymousInnerClass) {
            doReturn(anonymousInnerClass).when(givenClassInfo).isAnonymousInnerClass();
            return this;
        }

        ClassInfoStubbing isInterface(final boolean isInterface) {
            doReturn(isInterface).when(givenClassInfo).isInterface();
            return this;
        }

        ClassInfoStubbing isRecord(final boolean isRecord) {
            doReturn(isRecord).when(givenClassInfo).isRecord();
            return this;
        }

        ClassInfoStubbing isEnum(final boolean isEnum) {
            doReturn(isEnum).when(givenClassInfo).isEnum();
            return this;
        }

        ClassInfoStubbing name(final String name) {
            doReturn(name).when(givenClassInfo).getName();
            return this;
        }
    }
}
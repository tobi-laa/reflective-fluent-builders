package io.github.tobi.laa.reflective.fluent.builders.test;

import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassGraphException;
import io.github.classgraph.ClassInfoList;
import io.github.classgraph.ScanResult;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.platform.testkit.engine.EngineTestKit;
import org.mockito.invocation.InvocationOnMock;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.platform.engine.discovery.DiscoverySelectors.selectClass;
import static org.junit.platform.testkit.engine.EventConditions.event;
import static org.junit.platform.testkit.engine.EventConditions.finishedWithFailure;
import static org.junit.platform.testkit.engine.TestExecutionResultConditions.message;
import static org.mockito.Mockito.*;

class ClassGraphExtensionTest {

    @Test
    void testHappyPath() {
        // Arrange
        EngineTestKit
                .engine("junit-jupiter")
                .selectors(selectClass(ClassGraphExtensionAnnotatedTestClass.class))
                // Act
                .execute()
                // Assert
                .allEvents()
                .assertThatEvents()
                .doNotHave(event(finishedWithFailure()));
    }

    @SuppressWarnings("resource")
    @Test
    void testClassGraphException() {
        // Arrange
        final var cause = classGraphException("Thrown in unit test");
        try (final var ignored = mockConstruction(
                ClassGraph.class,
                withSettings().defaultAnswer(InvocationOnMock::getMock),
                (mock, ctx) -> doThrow(cause).when(mock).scan())) {
            EngineTestKit
                    .engine("junit-jupiter")
                    .selectors(selectClass(ClassGraphExtensionAnnotatedTestClass.class))
                    // Act
                    .execute()
                    // Assert
                    .allEvents()
                    .assertThatEvents()
                    .haveAtLeastOne(event(finishedWithFailure(message("Thrown in unit test"))));
        }
    }

    @SuppressWarnings("resource")
    @Test
    void testExceptionOnClose() {
        // Arrange
        final var cause = new IOException("Thrown in unit test");
        final var scanResult = mock(ScanResult.class);
        doThrow(cause).when(scanResult).close();
        doReturn(mock(ClassInfoList.class)).when(scanResult).getAllClasses();
        try (final var ignored = mockConstruction(
                ClassGraph.class,
                withSettings().defaultAnswer(InvocationOnMock::getMock),
                (mock, ctx) -> doReturn(scanResult).when(mock).scan())) {
            EngineTestKit
                    .engine("junit-jupiter")
                    .selectors(selectClass(ClassGraphExtensionAnnotatedTestClass.class))
                    // Act
                    .execute()
                    // Assert
                    .allEvents()
                    .assertThatEvents()
                    .haveAtLeastOne(event(finishedWithFailure(message("Thrown in unit test"))));
        }
    }

    @SneakyThrows
    private ClassGraphException classGraphException(final String message) {
        final var constructor = ClassGraphException.class.getDeclaredConstructor(String.class);
        constructor.setAccessible(true);
        return constructor.newInstance(message);
    }

    @SuppressWarnings("all")
    static class ClassGraphExtensionAnnotatedTestClass {

        @RegisterExtension
        static ClassGraphExtension classInfo = new ClassGraphExtension();

        @Test
        void knownClass_shouldReturnClass() {
            assertThat(classInfo.get(getClass())).isNotNull();
        }

        @Test
        void knownClassByFullyQualifiedName_shouldReturnClass() {
            assertThat(classInfo.get(getClass().getName())).isNotNull();
        }

        @Test
        void unknownClass_shouldThrow() {
            assertThatThrownBy(() -> classInfo.get("unknown.Class"))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("No ClassInfo for unknown.Class has been found!");
        }
    }
}
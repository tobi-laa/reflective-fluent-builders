package io.github.tobi.laa.reflective.fluent.builders.model;

import io.github.tobi.laa.reflective.fluent.builders.test.models.simple.SimpleClass;
import lombok.experimental.SuperBuilder;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class AbstractWriteAccessorTest {

    @ParameterizedTest
    @MethodSource
    void testCompareTo(final AbstractWriteAccessor a, final AbstractWriteAccessor b, final int expected) {
        // Act
        final int actual = a.compareTo(b);
        // Assert
        assertThat(actual).isEqualTo(expected);
    }

    private static Stream<Arguments> testCompareTo() {
        final TestAccessor accessor = TestAccessor.builder() //
                .propertyType(new MapType(Map.class, Object.class, Object.class)) //
                .propertyName("aName") //
                .visibility(Visibility.PRIVATE) //
                .declaringClass(SimpleClass.class) //
                .build();
        return Stream.of( //
                Arguments.of(accessor, accessor, 0), //
                Arguments.of(
                        accessor,
                        accessor.withPropertyName("anotherName"),
                        -32), //
                Arguments.of(
                        accessor,
                        accessor.toBuilder().propertyType(new SimpleType(String.class)).build(),
                        9), //
                Arguments.of( //
                        accessor, //
                        AnotherTestAccessor.builder() //
                                .propertyType(new MapType(Map.class, Object.class, Object.class)) //
                                .propertyName("aName") //
                                .visibility(Visibility.PRIVATE) //
                                .declaringClass(SimpleClass.class) //
                                .build(), //
                        19));
    }

    @SuperBuilder(toBuilder = true)
    private static class TestAccessor extends AbstractWriteAccessor {

        @Override
        public TestAccessor withPropertyName(final String propertyName) {
            return toBuilder().propertyName(propertyName).build();
        }

        @Override
        public boolean equals(Object o) {
            return this == o;
        }
    }

    @SuperBuilder
    private static class AnotherTestAccessor extends AbstractWriteAccessor {

        @Override
        public AnotherTestAccessor withPropertyName(final String paramName) {
            return this;
        }
    }
}

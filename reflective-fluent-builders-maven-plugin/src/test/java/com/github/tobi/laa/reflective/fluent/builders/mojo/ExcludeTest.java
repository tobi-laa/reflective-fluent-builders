package com.github.tobi.laa.reflective.fluent.builders.mojo;

import com.github.tobi.laa.reflective.fluent.builders.test.models.complex.ClassWithCollections;
import com.github.tobi.laa.reflective.fluent.builders.test.models.complex.ClassWithGenerics;
import com.github.tobi.laa.reflective.fluent.builders.test.models.full.Pet;
import com.github.tobi.laa.reflective.fluent.builders.test.models.simple.Simple;
import com.github.tobi.laa.reflective.fluent.builders.test.models.simple.SimpleClass;
import com.github.tobi.laa.reflective.fluent.builders.test.models.simple.SimpleClassNoSetPrefix;
import org.assertj.core.api.ThrowableAssert.ThrowingCallable;
import org.junit.jupiter.api.Test;

import java.util.function.Predicate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ExcludeTest {

    @Test
    void testToPredicateIllegalStateException() {
        // Arrange
        final var exclude = new Exclude(null, null, null, null);
        // Act
        final ThrowingCallable toPredicate = exclude::toPredicate;
        // Assert
        assertThatThrownBy(toPredicate)
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContainingAll("initialized", "packageName", "packageRegex", "className", "classRegex");
    }

    @Test
    void testToPredicateForExcludeWithPackageName() {
        // Arrange
        final var exclude = new Exclude(Simple.class.getPackageName(), null, null, null);
        // Act
        final Predicate<Class<?>> predicate = exclude.toPredicate();
        // Assert
        assertThat(predicate).accepts(SimpleClass.class, SimpleClassNoSetPrefix.class);
        assertThat(predicate).rejects(ClassWithCollections.class);
    }

    @Test
    void testToPredicateForExcludeWithPackageRegex() {
        // Arrange
        final var exclude = new Exclude(null, "test\\.models\\.(simple|complex)", null, null);
        // Act
        final Predicate<Class<?>> predicate = exclude.toPredicate();
        // Assert
        assertThat(predicate).accepts(SimpleClass.class, SimpleClassNoSetPrefix.class, ClassWithCollections.class);
        assertThat(predicate).rejects(Pet.class);
    }

    @Test
    void testToPredicateForExcludeWithClassName() {
        // Arrange
        final var exclude = new Exclude(null, null, SimpleClass.class.getName(), null);
        // Act
        final Predicate<Class<?>> predicate = exclude.toPredicate();
        // Assert
        assertThat(predicate).accepts(SimpleClass.class);
        assertThat(predicate).rejects(SimpleClassNoSetPrefix.class, ClassWithCollections.class, Pet.class);
    }

    @Test
    void testToPredicateForExcludeWithClassRegex() {
        // Arrange
        final var exclude = new Exclude(null, null, null, "test\\.models\\..*(SimpleClass|WithCollections)");
        // Act
        final Predicate<Class<?>> predicate = exclude.toPredicate();
        // Assert
        assertThat(predicate).accepts(SimpleClass.class, SimpleClassNoSetPrefix.class, ClassWithCollections.class);
        assertThat(predicate).rejects(Pet.class, ClassWithGenerics.class);
    }
}
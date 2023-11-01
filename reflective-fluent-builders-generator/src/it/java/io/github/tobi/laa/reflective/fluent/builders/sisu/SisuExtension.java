package io.github.tobi.laa.reflective.fluent.builders.sisu;

import com.google.inject.Guice;
import com.google.inject.Injector;
import lombok.SneakyThrows;
import org.eclipse.sisu.space.SpaceModule;
import org.eclipse.sisu.space.URLClassSpace;
import org.eclipse.sisu.wire.WireModule;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.api.extension.ParameterResolver;
import org.junit.jupiter.api.extension.TestInstancePostProcessor;

import javax.inject.Inject;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

/**
 * <p>
 * Junit Jupiter {@link org.junit.jupiter.api.extension.ExtendWith extension} to easily inject components into an
 * integration test.
 * </p>
 */
public class SisuExtension implements TestInstancePostProcessor, ParameterResolver {

    private static final AtomicReference<Injector> INJECTOR = new AtomicReference<>();

    private static Injector getInjectorLazily() {
        return INJECTOR.updateAndGet(SisuExtension::getInjectorLazily);
    }

    private static Injector getInjectorLazily(final Injector oldInjector) {
        if (oldInjector == null) {
            final var classloader = SisuExtension.class.getClassLoader();
            return Guice.createInjector(new WireModule(new SpaceModule(new URLClassSpace(classloader))));
        } else {
            return oldInjector;
        }
    }

    @Override
    public boolean supportsParameter(final ParameterContext parameterContext, final ExtensionContext extensionContext) {
        return parameterContext.getParameter().isAnnotationPresent(Inject.class);
    }

    @Override
    public Object resolveParameter(final ParameterContext parameterContext, final ExtensionContext extensionContext) {
        return getInjectorLazily().getInstance(parameterContext.getParameter().getType());
    }

    @Override
    public void postProcessTestInstance(final Object testInstance, final ExtensionContext extensionContext) {
        final Set<Field> fields = getFieldsToInject(extensionContext.getRequiredTestClass());
        fields.forEach(field -> injectToField(testInstance, field));
    }

    private Set<Field> getFieldsToInject(final Class<?> testClass) {
        return Arrays.stream(testClass.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Inject.class))
                .collect(Collectors.toSet());
    }

    @SneakyThrows
    private void injectToField(final Object testInstance, final Field field) {
        field.setAccessible(true);
        field.set(testInstance, getInjectorLazily().getInstance(field.getType()));
    }
}

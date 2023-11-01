package io.github.tobi.laa.reflective.fluent.builders.sisu;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.eclipse.sisu.space.SpaceModule;
import org.eclipse.sisu.space.URLClassSpace;
import org.eclipse.sisu.wire.WireModule;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestInstanceFactory;
import org.junit.jupiter.api.extension.TestInstanceFactoryContext;

import java.util.concurrent.atomic.AtomicReference;

/**
 * <p>
 * Junit Jupiter {@link org.junit.jupiter.api.extension.ExtendWith extension} to easily inject components into an
 * integration test.
 * </p>
 */
class SisuExtension implements TestInstanceFactory {

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
    public Object createTestInstance(final TestInstanceFactoryContext testInstanceFactoryContext, final ExtensionContext extensionContext) {
        return getInjectorLazily().getInstance(testInstanceFactoryContext.getTestClass());
    }
}

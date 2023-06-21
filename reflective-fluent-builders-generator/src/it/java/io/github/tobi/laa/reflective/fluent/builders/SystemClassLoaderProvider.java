package io.github.tobi.laa.reflective.fluent.builders;

import javax.inject.Named;
import javax.inject.Provider;
import javax.inject.Singleton;

@Named
@Singleton
@SuppressWarnings("unused")
class SystemClassLoaderProvider implements Provider<ClassLoader> {

    @Override
    public ClassLoader get() {
        return ClassLoader.getSystemClassLoader();
    }
}

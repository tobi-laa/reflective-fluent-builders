package io.github.tobi.laa.reflective.fluent.builders.sisu;

import com.google.inject.Binder;
import com.google.inject.Module;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ScanResult;
import io.github.tobi.laa.reflective.fluent.builders.Marker;
import org.eclipse.sisu.EagerSingleton;

import javax.inject.Named;

/**
 * <p>
 * Binds all test classes annoated with {@link SisuTest}.
 * </p>
 *
 * @see SisuExtension
 */
@Named
@EagerSingleton
@SuppressWarnings("unused")
class SisuTestModule implements Module {

    @Override
    public void configure(final Binder binder) {
        try (final ScanResult scanResult = new ClassGraph()
                .enableAllInfo()
                .acceptPackages(Marker.class.getPackageName())
                .scan()) {
            //
            scanResult
                    .getClassesWithAnnotation(SisuTest.class)
                    .stream()
                    .map(ClassInfo::loadClass)
                    .forEach(binder::bind);
        }
    }
}

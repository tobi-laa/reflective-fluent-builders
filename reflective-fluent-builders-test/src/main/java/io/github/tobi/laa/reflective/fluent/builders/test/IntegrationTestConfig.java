package io.github.tobi.laa.reflective.fluent.builders.test;

import io.github.tobi.laa.reflective.fluent.builders.Marker;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import javax.inject.Provider;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Objects;
import java.util.Set;

/**
 * <p>
 * Imported by {@link IntegrationTest @IntegrationTest}.
 * </p>
 */
@Configuration
@ComponentScan(basePackageClasses = Marker.class)
@Import(IntegrationTestConfig.JavaxInjectRegistrar.class)
@RequiredArgsConstructor
class IntegrationTestConfig {

    /**
     * <p>
     * Provides a {@link Clock} to be injected via DI.
     * </p>
     *
     * @return the {@link Clock} to be injected via DI.
     */
    @Bean
    Provider<Clock> fixedClockProvider() {
        return this::fixedClock;
    }

    /**
     * <p>
     * Provides a {@link Clock} to be injected via DI.
     * </p>
     *
     * @return the {@link Clock} to be injected via DI.
     */
    @Bean
    Clock fixedClock() {
        return Clock.fixed(Instant.parse("3333-03-13T00:00:00.00Z"), ZoneId.of("UTC"));
    }

    /**
     * <p>
     * Provides a {@link ClassLoader} to be injected via DI.
     * </p>
     *
     * @return the {@link ClassLoader} to be injected via DI.
     */
    @Bean
    Provider<ClassLoader> classLoaderProvider() {
        return this::classLoader;
    }

    /**
     * <p>
     * Provides a {@link ClassLoader} to be injected via DI.
     * </p>
     *
     * @return the {@link ClassLoader} to be injected via DI.
     */
    @Bean
    ClassLoader classLoader() {
        return ClassLoader.getSystemClassLoader();
    }

    /**
     * <p>
     * Configures Spring to recognize javax.inject.@Inject as an autowiring annotation.
     * This is necessary because Spring Boot 4 removed default support for JSR-330 annotations.
     * </p>
     *
     * @return the configured AutowiredAnnotationBeanPostProcessor
     */
    @Bean
    static AutowiredAnnotationBeanPostProcessor autowiredAnnotationBeanPostProcessor() {
        AutowiredAnnotationBeanPostProcessor processor = new AutowiredAnnotationBeanPostProcessor();
        processor.setAutowiredAnnotationTypes(Set.of(
                javax.inject.Inject.class,
                org.springframework.beans.factory.annotation.Autowired.class,
                org.springframework.beans.factory.annotation.Value.class
        ));
        return processor;
    }

    /**
     * <p>
     * Registrar to scan and register beans annotated with javax.inject annotations.
     * </p>
     */
    static class JavaxInjectRegistrar implements ImportBeanDefinitionRegistrar {
        @Override
        public void registerBeanDefinitions(@NonNull AnnotationMetadata importingClassMetadata, @NonNull BeanDefinitionRegistry registry) {
            ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
            scanner.addIncludeFilter(new AnnotationTypeFilter(javax.inject.Named.class));
            scanner.addIncludeFilter(new AnnotationTypeFilter(javax.inject.Singleton.class));

            for (BeanDefinition bd : scanner.findCandidateComponents(Marker.class.getPackageName())) {
                GenericBeanDefinition beanDefinition = new GenericBeanDefinition();
                beanDefinition.setBeanClassName(bd.getBeanClassName());

                var beanName = Objects.requireNonNull(bd.getBeanClassName());
                int lastDot = beanName.lastIndexOf('.');
                if (lastDot > 0) {
                    beanName = beanName.substring(lastDot + 1);
                }
                beanName = Character.toLowerCase(beanName.charAt(0)) + beanName.substring(1);

                registry.registerBeanDefinition(beanName, beanDefinition);
            }
        }
    }
}

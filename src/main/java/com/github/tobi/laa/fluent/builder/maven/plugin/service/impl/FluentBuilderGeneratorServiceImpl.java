package com.github.tobi.laa.fluent.builder.maven.plugin.service.impl;

import com.github.tobi.laa.fluent.builder.maven.plugin.model.*;
import com.github.tobi.laa.fluent.builder.maven.plugin.service.api.FluentBuilderGeneratorService;
import com.github.tobi.laa.fluent.builder.maven.plugin.service.api.SetterService;
import com.squareup.javapoet.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 *     Standard implementation of {@link FluentBuilderGeneratorService}.
 * </p>
 */
@RequiredArgsConstructor
public class FluentBuilderGeneratorServiceImpl implements FluentBuilderGeneratorService {

    @lombok.NonNull
    private final SetterService setterService;

    @Override
    public void generateSourceCode(final Class<?> clazz) {
        Objects.requireNonNull(clazz);
        final ClassName builderName = ClassName.get(clazz.getPackageName(), clazz.getSimpleName() + "Builder");

        final Set<Setter> setters = setterService.gatherAllSetters(clazz);
        final List<MethodSpec> fluentSetters = avoidNameCollisions( setters)
                .stream()
                .map(setter -> generateFluentSetter(setter, builderName))
                .collect(Collectors.toList());

        TypeSpec builder = TypeSpec.classBuilder(builderName)
                .addModifiers(Modifier.PUBLIC)
                .addMethods(fluentSetters)
                .build();

        JavaFile javaFile = JavaFile.builder(builderName.packageName(), builder)
                .build();

        try {
            javaFile.writeTo(System.out);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Set<Setter> avoidNameCollisions(final Set<Setter> setters) {
        final Set<Setter> noNameCollisions = new HashSet<>();
        for (final var setter : setters) {
            if (noNameCollisions.stream().map(Setter::getParamName).noneMatch(setter.getParamName()::equals)) {
                noNameCollisions.add(setter);
            } else {
                for (int i = 0; true; i++) {
                    final var paramName = setter.getParamName() + i;
                    if (noNameCollisions.stream().map(Setter::getParamName).noneMatch(paramName::equals)) {
                        noNameCollisions.add(setter.withParamName(paramName));
                        break;
                    }
                }
            }
        }
        return noNameCollisions;
    }

    private MethodSpec generateFluentSetter(final Setter setter, final ClassName builderName) {
        System.out.println(setter.getMethodName());
        final var name = setter.getMethodName().replaceFirst("^set", "");
        final TypeName typeName;
        if (setter.getClass() == CollectionSetter.class) {
            final Class<?> parCl = (Class<?>) setter.getParamType();
            ClassName typeName1 = ClassName.get(parCl);
            final Class<?> parCl2 = (Class<?>) CollectionSetter.class.cast(setter).getParamTypeArg();
            ClassName typeName2 = ClassName.get(parCl2);
            typeName = ParameterizedTypeName.get(typeName1, typeName2);
        } else {
            typeName = TypeName.get(setter.getParamType());
        }
        return MethodSpec.methodBuilder(name)
                .addModifiers(Modifier.PUBLIC)
                .returns(builderName)
                .addParameter(typeName, setter.getParamName(), Modifier.FINAL)
                .addStatement("this.$L = $L", setter.getParamName(), setter.getParamName())
                .addStatement("callSetterFor.$L = $L", setter.getParamName(), true)
                .build();
    }

    public static void main(String[] args) {
        final var viServ = new VisibilityServiceImpl();
        final var setServ = new SetterServiceImpl(viServ, "set");
        final var flueBui = new FluentBuilderGeneratorServiceImpl(setServ);
        flueBui.generateSourceCode(Person.class);
        final List<Object> foo = new ArrayList<>();
        new Person().setNoType(foo);
    }

    @Data
    public static class Person {
        private String name;
        private int age;
        private boolean married;
        private List<Pet> pets;
        private List noType;
        private java.util.Set<Pet> set1;

        public void setMarried(final String married) {
            this.married = Boolean.parseBoolean(married);
        }

        public void setMarried(final boolean married) {
            this.married = married;
        }
    }

    public static class Pet {

    }
}

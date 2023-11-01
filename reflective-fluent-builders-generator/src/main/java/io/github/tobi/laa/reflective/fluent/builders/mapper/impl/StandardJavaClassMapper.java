package io.github.tobi.laa.reflective.fluent.builders.mapper.impl;

import io.github.classgraph.ClassInfo;
import io.github.tobi.laa.reflective.fluent.builders.mapper.api.JavaClassMapper;
import io.github.tobi.laa.reflective.fluent.builders.mapper.api.JavaTypeMapper;
import io.github.tobi.laa.reflective.fluent.builders.model.JavaClass;
import io.github.tobi.laa.reflective.fluent.builders.service.api.VisibilityService;
import org.mapstruct.*;

import java.io.File;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.Optional;

/**
 * <p>
 * Implements {@link JavaClassMapper} via MapStruct.
 * </p>
 */
@Mapper(config = Config.class, uses = {VisibilityService.class, JavaTypeMapper.class, ClassSupplierMapper.class})
abstract class StandardJavaClassMapper implements JavaClassMapper {

    @BeforeMapping
    void requireNonNull(final ClassInfo classInfo) {
        Objects.requireNonNull(classInfo);
    }

    @Mapping(target = "visibility", source = "modifiers")
    @Mapping(target = "sourceLocation", source = "sourceFile", conditionQualifiedBy = LocalClassFile.class)
    @Mapping(target = "type", source = ".")
    @Mapping(target = "isStatic", source = "static")
    @Mapping(target = "classLocation", source = ".")
    @Mapping(target = "classSupplier", source = "name")
    @Mapping(target = "superclass")
    public abstract JavaClass map(final ClassInfo classInfo);

    Path classLocation(final ClassInfo clazz) {
        return Optional.ofNullable(clazz)
                .map(ClassInfo::getClasspathElementFile)
                .map(File::toPath)
                .map(path -> resolveClassFileIfNecessary(path, clazz))
                .orElse(null);
    }

    private Path resolveClassFileIfNecessary(final Path path, final ClassInfo clazz) {
        if (Files.isDirectory(path)) {
            Path classFile = path;
            for (final String subdir : clazz.getPackageName().split("\\.")) {
                classFile = classFile.resolve(subdir);
            }
            classFile = classFile.resolve(clazz.getSimpleName() + ".class");
            return classFile;
        } else {
            return path;
        }
    }

    @Condition
    @LocalClassFile
    boolean localClassFile(final ClassInfo classInfo) {
        return classInfo.getClasspathElementFile() != null && classInfo.getClasspathElementFile().isDirectory();
    }

    @Qualifier
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.CLASS)
    @interface LocalClassFile {
        // no content
    }
}

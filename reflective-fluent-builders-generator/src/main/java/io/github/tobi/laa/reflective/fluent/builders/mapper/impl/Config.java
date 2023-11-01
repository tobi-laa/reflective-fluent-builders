package io.github.tobi.laa.reflective.fluent.builders.mapper.impl;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.MapperConfig;
import org.mapstruct.ReportingPolicy;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

/**
 * <p>
 * Global config to be used by all MapStruct-generated {@link org.mapstruct.Mapper mappers}.
 * </p>
 */
@MapperConfig(
        componentModel = "jsr330",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        nullValuePropertyMappingStrategy = IGNORE,
        nullValueCheckStrategy = ALWAYS,
        uses = PathMapper.class)
interface Config {
    // no config
}

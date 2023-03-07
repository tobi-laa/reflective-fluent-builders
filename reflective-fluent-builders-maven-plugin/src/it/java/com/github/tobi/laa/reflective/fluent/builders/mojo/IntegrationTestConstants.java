package com.github.tobi.laa.reflective.fluent.builders.mojo;


import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * <p>
 * Various constants shared across integration tests.
 * </p>
 */
class IntegrationTestConstants {

    /**
     * <p>
     * The directory containing the expected builder Java files, i.e. the files against which to compare the results
     * when doing assertions in integration tests. These are builders that are generated with the default configuration,
     * i.e. without custom excludes, getter/setter prefixes and so forth.
     * </p>
     */
    static final Path EXPECTED_DEFAULT_BUILDERS_ROOT_DIR = Paths.get("src", "it", "resources", "expected-builders", "default-config");

    /**
     * <p>
     * The location of the maven cache to be shared across integration tests whenever it is not explicitly necessary to
     * re-download all dependencies (which should be most cases).
     * </p>
     *
     * @see com.soebes.itf.jupiter.extension.MavenRepository#value()
     */
    static final String MAVEN_SHARED_LOCAL_CACHE = "../../sharedMavenCache/.m2/repository";
}

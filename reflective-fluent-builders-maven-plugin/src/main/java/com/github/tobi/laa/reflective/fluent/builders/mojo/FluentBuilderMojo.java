package com.github.tobi.laa.reflective.fluent.builders.mojo;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

@Mojo(name = "fluent-builder", defaultPhase = LifecyclePhase.GENERATE_SOURCES)
public class FluentBuilderMojo extends AbstractMojo {

    @Parameter

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        // noop
    }
}

package be.cloudway.gramba.nativeimage;


import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


@Mojo(name = "build", defaultPhase = LifecyclePhase.PACKAGE)
public class NativeImageMain
        extends AbstractMojo {

    @Parameter(defaultValue = "${project.build.directory}", property = "outputDir", required = true)
    private File outputDirectory;

    @Parameter(defaultValue = "${project.basedir}", property = "basedir", required = true)
    private File baseDir;

    @Parameter(defaultValue = "function.jar", property = "jarName", readonly = true)
    private String jarName;

    @Parameter(property = "injectionFiles", readonly = true)
    private List<String> injectionFiles = new ArrayList<>();

    @Parameter(property = "delayedClasses", readonly = true)
    private List<String> delayedClasses = new ArrayList<>();

    @Parameter(property = "testFiles", readonly = true)
    private List<String> testFiles = new ArrayList<>();

    @Parameter(defaultValue = "false", property = "skipBuild", readonly = true)
    private boolean skipBuild;

    @Parameter(defaultValue = "true", property = "createZip", readonly = true)
    private boolean createZip;

    @Parameter(property = "envVariables", readonly = true)
    private List<String> envVariables = new ArrayList<>();

    @Parameter(property = "additionalOptions", readonly = true)
    private List<String> additionalOptions = new ArrayList<>();

    // "oracle/graalvm-ce:1.0.0-rc13"
    @Parameter(property = "dockerImage", defaultValue = "sirmomster/gramba-imager", readonly = true)
    private String dockerImage;

    public void execute()
            throws MojoExecutionException {
        new NativeImageHandler(getLog(), this).run();
    }

    public File getOutputDirectory() {
        return outputDirectory;
    }

    public File getBaseDir() {
        return baseDir;
    }

    public String getJarName() {
        return jarName;
    }

    public List<String> getInjectionFiles() {
        return injectionFiles;
    }

    public List<String> getDelayedClasses() {
        return delayedClasses;
    }

    public List<String> getTestFiles() {
        return testFiles;
    }

    public boolean isSkipBuild() {
        return skipBuild;
    }

    public boolean isCreateZip() {
        return createZip;
    }

    public List<String> getEnvVariables() {
        return envVariables;
    }

    public List<String> getAdditionalOptions() {
        return additionalOptions;
    }

    public String getDockerImage() {
        return dockerImage;
    }
}


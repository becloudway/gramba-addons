package be.cloudway.easy.reflection;

import org.apache.maven.artifact.DependencyResolutionRequiredException;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.plugins.annotations.ResolutionScope;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Mojo(name = "reflect", defaultPhase = LifecyclePhase.PACKAGE, requiresDependencyResolution = ResolutionScope.RUNTIME_PLUS_SYSTEM)
public class EasyReflection
        extends AbstractMojo {

    @Parameter(property = "ConfigurationPackages")
    private List<String> configurationPackages;

    @Parameter(defaultValue = "${project.build.directory}", property = "outputDir", required = true)
    private File outputDirectory;

    @Parameter(defaultValue = "${project.build.sourceDirectory}", property = "inputDir", readonly = true)
    private File sourcesDirectory;

    @Parameter(defaultValue = "${project}", required = true, readonly = true)
    private MavenProject project;

    private ConfigurationHandler configurationHandler;
    private ProxyConfigurationHandler proxyConfigurationHandler = new ProxyConfigurationHandler();
    private ReflectionConfigurationHandler reflectionConfigurationHandler = new ReflectionConfigurationHandler();

    public void execute()
            throws MojoExecutionException, MojoFailureException {

        if (configurationPackages == null) {
            configurationPackages = new ArrayList<>();
        }

        extendClassPath();

        configurationHandler = new ConfigurationHandler();
        List<Object> configurations = configurationHandler.loadConfiguration(configurationPackages);

        getLog().info("Found configuration: " + configurations.size());

        // handle reflection
        reflectionConfigurationHandler.handle(configurations, outputDirectory);

        // handle proxies
        proxyConfigurationHandler.handle(configurations, outputDirectory);

        // handle other stuff?
    }

    public void extendClassPath () {
        try {
            Set<URL> urls = new HashSet<>();
            List<String> elements = project.getRuntimeClasspathElements();

            for (String element : elements) {
                System.out.println(element);
                urls.add(new File(element).toURI().toURL());
            }

            ClassLoader contextClassLoader = URLClassLoader.newInstance(
                    urls.toArray(new URL[0]),
                    Thread.currentThread().getContextClassLoader());

            Thread.currentThread().setContextClassLoader(contextClassLoader);

        } catch (DependencyResolutionRequiredException | MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

}

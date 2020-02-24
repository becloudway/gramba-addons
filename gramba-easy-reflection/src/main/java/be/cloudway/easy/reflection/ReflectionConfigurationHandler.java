package be.cloudway.easy.reflection;

import be.cloudway.easy.reflection.dependency.configuration.ScanForReflectionInterface;
import be.cloudway.easy.reflection.dependency.configuration.reflection.ReflectionConfigurationInterface;
import be.cloudway.easy.reflection.helpers.ReflectionJsonFileWriter;
import be.cloudway.easy.reflection.model.ReflectedJson;
import be.cloudway.gramba.annotations.Reflected;
import org.apache.maven.plugin.MojoExecutionException;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.io.File;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ReflectionConfigurationHandler {
    public void handle (List<Object> grambaConfigurations, File outputDirectory) {
        List<String> packages = grambaConfigurations.stream()
                .filter(v -> v instanceof ScanForReflectionInterface &&  ((ScanForReflectionInterface) v).scanPackages() != null)
                .flatMap(v -> ((ScanForReflectionInterface) v).scanPackages().stream())
                .distinct()
                .collect(Collectors.toList());

        List<ReflectedJson> output = createReflectionEntries(packages);
        output.addAll(manualConfigurationEntries(grambaConfigurations));

        try {
            ReflectionJsonFileWriter.writeFile(outputDirectory, output, "reflections.json");
        } catch (MojoExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public List<ReflectedJson> createReflectionEntries (List<String> packages) {
        ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
        packages.forEach(v -> {
            configurationBuilder.addUrls(ClasspathHelper.forPackage(v));
        });

        Reflections reflections = new Reflections(configurationBuilder);

        Set<Class<?>> reflectedClasses = reflections.getTypesAnnotatedWith(Reflected.class);

        return reflectedClasses.stream().map(v -> new ReflectedJson(v.getName())).collect(Collectors.toList());
    }

    public List<ReflectedJson> manualConfigurationEntries (List<Object> grambaConfigurations) {
        return grambaConfigurations.stream()
                .filter(v -> v instanceof ReflectionConfigurationInterface
                        && ((ReflectionConfigurationInterface) v).reflectionConfiguration() != null)
                .flatMap(v -> ((ReflectionConfigurationInterface) v).reflectionConfiguration().stream())
                .collect(Collectors.toList());
    }
}

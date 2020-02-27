package be.cloudway.easy.reflection;

import be.cloudway.easy.reflection.dependency.configuration.ScanForReflectionInterface;
import be.cloudway.easy.reflection.dependency.configuration.reflection.PackageScanConfiguration;
import be.cloudway.easy.reflection.dependency.configuration.reflection.ReflectionConfigurationInterface;
import be.cloudway.easy.reflection.helpers.ReflectionJsonFileWriter;
import be.cloudway.easy.reflection.model.ReflectedJson;
import be.cloudway.gramba.annotations.Reflected;
import org.apache.maven.plugin.MojoExecutionException;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ReflectionConfigurationHandler {
    public void handle (List<Object> grambaConfigurations, File outputDirectory) {
        List<PackageScanConfiguration> packageScanConfigurations = grambaConfigurations.stream()
                .filter(v -> v instanceof ScanForReflectionInterface &&  ((ScanForReflectionInterface) v).scanPackages(new ArrayList<>()) != null)
                .flatMap(v -> ((ScanForReflectionInterface) v).scanPackages(new ArrayList<>()).stream())
                .distinct()
                .collect(Collectors.toList());

        List<ReflectedJson> output = createReflectionEntries(packageScanConfigurations);
        output.addAll(manualConfigurationEntries(grambaConfigurations));

        try {
            ReflectionJsonFileWriter.writeFile(outputDirectory, output, "reflections.json");
        } catch (MojoExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public List<ReflectedJson> createReflectionEntry (PackageScanConfiguration configuration) {

        ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
        configurationBuilder.addUrls(ClasspathHelper.forPackage(configuration.getPackageName()));


        Set<Class<?>> reflectedClasses;

        if (configuration.isIgnoreAnnotation()) {
            configurationBuilder.setScanners(new SubTypesScanner(false), new ResourcesScanner())
                .setInputsFilter(v -> v != null && v.startsWith(configuration.getPackageName()));

            Reflections reflections = new Reflections(configurationBuilder);
            reflectedClasses = reflections.getSubTypesOf(Object.class);
        } else {
            Reflections reflections = new Reflections(configurationBuilder);
            reflectedClasses = reflections
                    .getTypesAnnotatedWith(configuration.getLookForAnnotation());
        }

        return reflectedClasses.stream().map(v -> new ReflectedJson(v.getName())).collect(Collectors.toList());
    }

    public List<ReflectedJson> createReflectionEntries (List<PackageScanConfiguration> packages) {
        return packages.stream().flatMap(v -> this.createReflectionEntry(v).stream()).collect(Collectors.toList());
    }

    public List<ReflectedJson> manualConfigurationEntries (List<Object> grambaConfigurations) {
        return grambaConfigurations.stream()
                .filter(v -> v instanceof ReflectionConfigurationInterface
                        && ((ReflectionConfigurationInterface) v).reflectionConfiguration() != null)
                .flatMap(v -> ((ReflectionConfigurationInterface) v).reflectionConfiguration().stream())
                .collect(Collectors.toList());
    }
}

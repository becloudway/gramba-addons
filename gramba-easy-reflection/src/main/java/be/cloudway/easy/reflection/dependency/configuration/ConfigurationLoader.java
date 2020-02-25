package be.cloudway.easy.reflection.dependency.configuration;

import be.cloudway.gramba.annotations.GrambaConfigurationTarget;
import org.apache.maven.plugin.MojoFailureException;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ConfigurationLoader {
    private String pack;
    private List<Object> configurationClasses = new ArrayList<>();

    public ConfigurationLoader(String pack) {
        this.pack = pack;
    }

    public ConfigurationLoader () {
        this.pack = "be.cloudway.gramba.configuration";
    }

    public void load () throws MojoFailureException {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

        ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
        configurationBuilder.addUrls(ClasspathHelper.forPackage(this.pack));

        if (this.pack == null) {
            configurationBuilder.addUrls(ClasspathHelper.forClassLoader(classLoader));
        }

        Reflections reflections = new Reflections(configurationBuilder);

        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(GrambaConfigurationTarget.class);

        System.out.println("Found configuration files: " + classes.size() + " for package: " + pack);

        for (Class<?> v : classes) {
            try {

                Constructor<?> ctor = v.getConstructors()[0];
                ctor.setAccessible(true);

                configurationClasses.add(ctor.newInstance());

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }

    public List<Object> getConfigurationClasses() {
        return configurationClasses;
    }
}

package be.cloudway.gramba.configuration.gramba;

import be.cloudway.easy.reflection.dependency.configuration.GrambaConfiguration;
import be.cloudway.easy.reflection.dependency.configuration.reflection.model.ReflectionConfigurationBuilder;
import be.cloudway.easy.reflection.model.ReflectedJson;

import java.util.List;

public class Configuration implements GrambaConfiguration {
    @Override
    public List<ReflectedJson> reflectionConfiguration() {
        return new ReflectionConfigurationBuilder()
            .prefix("be.cloudway.gramba.runtime.web")
                .reflect("ErrorResponse")
            .prefix("java.util")
                .reflectWithProperties("LinkedHashMap")
                    .addMethod("<init>")
                .next()
                .reflectWithProperties("ArrayList")
                    .addMethod("<init>")
                .next()
                .reflect(Boolean[].class)
                .reflect(String[].class)
                .reflect(Integer[].class)
                .reflect(Double[].class)
                .reflect(Long[].class)
                .reflect(Float[].class)
                .reflect(Boolean.class)
                .reflect(String.class)
                .reflect(Integer.class)
                .reflect(Double.class)
                .reflect(Long.class)
                .reflect(Float.class)
            .build();
    }

    @Override
    public List<List<String>> proxyConfiguration() {
        return null;
    }

    @Override
    public List<String> scanPackages() {
        return null;
    }
}

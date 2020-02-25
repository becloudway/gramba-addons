package be.cloudway.gramba.configuration.mariadb;

import be.cloudway.easy.reflection.dependency.configuration.GrambaConfiguration;
import be.cloudway.easy.reflection.dependency.configuration.reflection.model.ReflectionConfigurationBuilder;
import be.cloudway.easy.reflection.model.ReflectedJson;

import java.util.List;

public class Configuration implements GrambaConfiguration {
    @Override
    public List<ReflectedJson> reflectionConfiguration() {
        return new ReflectionConfigurationBuilder()
            .prefix("org.mariadb.jdbc.internal.util")
                .reflectWithProperties("Options")
                    .addField("user")
                    .addField("password")
                .next()
                .reflect(java.sql.Date.class)
                .reflect(java.sql.Date[].class)
                .reflect(java.sql.Timestamp.class)
                .reflect(java.sql.Timestamp[].class)
                .reflect(java.sql.Time.class)
                .reflect(java.sql.Time[].class)
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

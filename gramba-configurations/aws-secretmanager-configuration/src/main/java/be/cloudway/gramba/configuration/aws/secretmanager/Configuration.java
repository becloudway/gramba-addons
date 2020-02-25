package be.cloudway.gramba.configuration.aws.secretmanager;

import be.cloudway.easy.reflection.dependency.configuration.GrambaConfiguration;
import be.cloudway.easy.reflection.dependency.configuration.reflection.model.ReflectionConfigurationBuilder;
import be.cloudway.easy.reflection.model.ReflectedJson;

import java.util.List;

public class Configuration implements GrambaConfiguration {

    @Override
    public List<ReflectedJson> reflectionConfiguration() {
        return new ReflectionConfigurationBuilder()
                .prefix("com.amazonaws.services.secretsmanager.model")
                .reflect("AWSSecretsManagerException")
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

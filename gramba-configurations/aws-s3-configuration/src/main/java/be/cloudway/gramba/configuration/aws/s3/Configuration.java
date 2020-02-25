package be.cloudway.gramba.configuration.aws.s3;

import be.cloudway.easy.reflection.dependency.configuration.GrambaConfiguration;
import be.cloudway.easy.reflection.dependency.configuration.reflection.model.ReflectionConfigurationBuilder;
import be.cloudway.easy.reflection.model.ReflectedJson;
import com.amazonaws.services.s3.internal.AWSS3V4Signer;

import java.util.List;

public class Configuration implements GrambaConfiguration {
    @Override
    public List<ReflectedJson> reflectionConfiguration() {
        return new ReflectionConfigurationBuilder()
                .reflect(AWSS3V4Signer.class)
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

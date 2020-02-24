package be.cloudway.gramba.configuration.aws.ses;

import be.cloudway.easy.reflection.dependency.configuration.reflection.model.ReflectionConfigurationBuilder;
import be.cloudway.easy.reflection.model.ReflectedJson;
import be.cloudway.easy.reflection.dependency.configuration.GrambaConfiguration;
import com.amazonaws.services.simpleemail.model.MessageRejectedException;
import com.sun.org.apache.xerces.internal.jaxp.DocumentBuilderFactoryImpl;

import java.util.List;

public class Configuration implements GrambaConfiguration {
    @Override
    public List<ReflectedJson> reflectionConfiguration() {
        return new ReflectionConfigurationBuilder()
                .reflectWithProperties(DocumentBuilderFactoryImpl.class)
                    .addNoArg()
                .next()
                .reflectWithProperties("com.sun.xml.internal.stream.XMLInputFactoryImpl")
                    .addNoArg()
                .next()
                .reflect(MessageRejectedException.class)
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

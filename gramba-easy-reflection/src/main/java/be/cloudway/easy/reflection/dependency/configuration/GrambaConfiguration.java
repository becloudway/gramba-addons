package be.cloudway.easy.reflection.dependency.configuration;

import be.cloudway.easy.reflection.dependency.configuration.proxy.ProxyConfigurationInterface;
import be.cloudway.easy.reflection.dependency.configuration.reflection.ReflectionConfigurationInterface;

public interface GrambaConfiguration extends ProxyConfigurationInterface,
        ReflectionConfigurationInterface, ScanForReflectionInterface {
}

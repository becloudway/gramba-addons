package be.cloudway.easy.reflection;

import be.cloudway.easy.reflection.dependency.configuration.ConfigurationLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ConfigurationHandler {

    private final ConfigurationLoader defaultConfigurationLoader = new ConfigurationLoader();
    private List<ConfigurationLoader> configurationLoaders;

    private List<Object> grambaConfigurations = new ArrayList<>();


    public List<Object> loadConfiguration (List<String> configurationPackages) {
        configurationLoaders = configurationPackages.stream()
                .map(ConfigurationLoader::new)
                .collect(Collectors.toList());

        configurationLoaders.add(defaultConfigurationLoader);

        configurationLoaders.forEach(v -> {
            try {
                v.load();
                this.grambaConfigurations.addAll(v.getConfigurationClasses());
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });

        return grambaConfigurations;
    }

    public List<Object> getGrambaConfigurations() {
        return grambaConfigurations;
    }
}

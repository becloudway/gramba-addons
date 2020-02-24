package be.cloudway.easy.reflection;

import be.cloudway.easy.reflection.dependency.configuration.proxy.ProxyConfigurationInterface;
import be.cloudway.easy.reflection.helpers.ReflectionJsonFileWriter;
import be.cloudway.easy.reflection.dependency.configuration.GrambaConfiguration;
import org.apache.maven.plugin.MojoExecutionException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ProxyConfigurationHandler {

    private List<List<String>> proxyConfigurationList = new ArrayList<>();

    public void handle (List<Object> configurationList, File outputDirectory) throws MojoExecutionException {
        proxyConfigurationList.clear();

        configurationList.forEach(this::handleEntry);

        ReflectionJsonFileWriter.writeFile(outputDirectory, proxyConfigurationList, "proxies.json");
    }

    private void handleEntry (Object configuration) {
        if (!(configuration instanceof ProxyConfigurationInterface)) {
            return;
        }

        ProxyConfigurationInterface proxyConfigurationInterface = (ProxyConfigurationInterface) configuration;

        System.out.println(proxyConfigurationInterface.getClass().getName());
        List<List<String>> proxyConfiguration = proxyConfigurationInterface.proxyConfiguration();
        if (proxyConfiguration == null) return;

        proxyConfigurationList.addAll(proxyConfiguration);
    }
}

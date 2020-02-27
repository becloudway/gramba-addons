package be.cloudway.easy.reflection.dependency.configuration;

import be.cloudway.easy.reflection.dependency.configuration.reflection.PackageScanConfiguration;

import java.util.List;

public interface ScanForReflectionInterface {
    List<PackageScanConfiguration> scanPackages(List<PackageScanConfiguration> packageToScan);
}

package be.cloudway.gramba.test.model;

import be.cloudway.easy.reflection.dependency.configuration.ScanForReflectionInterface;
import be.cloudway.gramba.annotations.GrambaConfigurationTarget;

import java.util.Collections;
import java.util.List;

@GrambaConfigurationTarget
public class TestConfiguration implements ScanForReflectionInterface {
    @Override
    public List<String> scanPackages() {
        return Collections.singletonList(
                "be.cloudway.gramba.test.model"
        );
    }
}
package be.cloudway.easy.reflection.dependency.configuration.reflection;

import be.cloudway.gramba.annotations.Reflected;

import java.lang.annotation.Annotation;

public class PackageScanConfiguration {
    private boolean ignoreAnnotation = false;
    private Class<? extends Annotation> lookForAnnotation = Reflected.class;
    private String packageName = "";

    public PackageScanConfiguration(String packageName, boolean ignoreAnnotation) {
        this(packageName);
        this.ignoreAnnotation = ignoreAnnotation;
    }

    public PackageScanConfiguration(String packageName, Class<? extends Annotation> lookForAnnotation) {
        this(packageName);
        this.lookForAnnotation = lookForAnnotation;
    }

    public PackageScanConfiguration(String packageName) {
        this.packageName = packageName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public boolean isIgnoreAnnotation() {
        return ignoreAnnotation;
    }

    public void setIgnoreAnnotation(boolean ignoreAnnotation) {
        this.ignoreAnnotation = ignoreAnnotation;
    }

    public Class<? extends Annotation> getLookForAnnotation() {
        return lookForAnnotation;
    }

    public void setLookForAnnotation(Class<? extends Annotation> lookForAnnotation) {
        this.lookForAnnotation = lookForAnnotation;
    }
}

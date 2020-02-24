package be.cloudway.easy.reflection.dependency.configuration.reflection.model;

import be.cloudway.easy.reflection.model.ReflectedJson;

import java.util.ArrayList;
import java.util.List;

public class ReflectionConfigurationBuilder {
    private List<ReflectedJson> reflectedJsonPropertiesList = new ArrayList<>();
    private String prefix = "";

    public ReflectionConfigurationBuilder () {

    }

    public ReflectionConfigurationBuilder reflect(String name) {
        reflectedJsonPropertiesList.add(new ReflectedJson(getFullName(name)));

        return this;
    }

    public ReflectionConfigurationBuilder reflect (Class c) {
        reflectedJsonPropertiesList.add(new ReflectedJson(c.getName()));

        return this;
    }

    private String getFullName (String name) {
        if (prefix != null && !prefix.isEmpty()) {
            return prefix + "." + name;
        }

        return name;
    }

    public ExtendedReflectionConfigurationBuilder reflectWithProperties(String name) {
        return new ExtendedReflectionConfigurationBuilder(this, getFullName(name));
    }

    public ExtendedReflectionConfigurationBuilder reflectWithProperties(Class c) {
        return new ExtendedReflectionConfigurationBuilder(this, c.getName());
    }

    public ReflectionConfigurationBuilder add (ReflectedJson reflectedJson) {
        reflectedJsonPropertiesList.add(reflectedJson);

        return this;
    }


    public List<ReflectedJson> build () {
        return reflectedJsonPropertiesList;
    }

    public ReflectionConfigurationBuilder prefix (String prefix) {
        this.prefix = prefix;

        return this;
    }
}

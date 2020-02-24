package be.cloudway.easy.reflection.dependency.configuration.reflection.model;

import be.cloudway.easy.reflection.model.ReflectedField;
import be.cloudway.easy.reflection.model.ExtendedReflectedJson;
import be.cloudway.easy.reflection.model.ReflectedMethod;

import java.util.ArrayList;
import java.util.List;

public class ExtendedReflectionConfigurationBuilder {
    private ReflectionConfigurationBuilder reflectionConfigurationBuilder;
    private ExtendedReflectedJson reflectedJson;

    private List<ReflectedField> reflectedFieldList = new ArrayList<>();
    private List<ReflectedMethod> reflectedMethodList = new ArrayList<>();

    public ExtendedReflectionConfigurationBuilder(ReflectionConfigurationBuilder reflectionConfigurationBuilder, String fullClassName) {
        this.reflectionConfigurationBuilder = reflectionConfigurationBuilder;

        reflectedJson = new ExtendedReflectedJson();
        reflectedJson.setName(fullClassName);
    }

    public ExtendedReflectionConfigurationBuilder addField (String fieldName) {
        reflectedFieldList.add(new ReflectedField(fieldName));

        return this;
    }

    public ExtendedReflectionConfigurationBuilder addMethod (String methodName) {
        reflectedMethodList.add(new ReflectedMethod(methodName));

        return this;
    }

    public ExtendedReflectionConfigurationBuilder addNoArg () {
        return addMethod("<init>");
    }

    public ReflectionConfigurationBuilder next () {
        reflectedJson.setFields(reflectedFieldList);
        reflectedJson.setMethods(reflectedMethodList);

        return this.reflectionConfigurationBuilder.add(reflectedJson);
    }

}

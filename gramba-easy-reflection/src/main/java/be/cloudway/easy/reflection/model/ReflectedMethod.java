package be.cloudway.easy.reflection.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReflectedMethod {
    private String name;
    private List<String> parameterTypes = new ArrayList<>();

    public ReflectedMethod (String name) {
        this.name = name;
    }

    public ReflectedMethod () {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(List<String> parameterTypes) {
        this.parameterTypes = parameterTypes;
    }
}

package be.cloudway.easy.reflection.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExtendedReflectedJson extends ReflectedJson {
    private List<ReflectedField> fields;
    private List<ReflectedMethod> methods;

    public List<ReflectedField> getFields() {
        return fields;
    }

    public void setFields(List<ReflectedField> fields) {
        this.fields = fields;
    }

    public List<ReflectedMethod> getMethods() {
        return methods;
    }

    public void setMethods(List<ReflectedMethod> methods) {
        this.methods = methods;
    }
}

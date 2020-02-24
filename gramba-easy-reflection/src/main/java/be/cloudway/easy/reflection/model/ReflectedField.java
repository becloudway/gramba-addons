package be.cloudway.easy.reflection.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReflectedField {
    private String name;
    private boolean allowWrite;

    public ReflectedField(String name) {
        this.name = name;
        this.allowWrite = true;
    }

    public ReflectedField() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAllowWrite() {
        return allowWrite;
    }

    public void setAllowWrite(boolean allowWrite) {
        this.allowWrite = allowWrite;
    }
}
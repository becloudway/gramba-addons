package be.cloudway.easy.reflection.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReflectedJson {
    private String name;
    private boolean allDeclaredConstructors = true;
    private boolean allPublicConstructors = true;
    private boolean allDeclaredMethods = true;
    private boolean allPublicMethods = true;
    private boolean allDeclaredClasses = true;
    private boolean allPublicClasses = true;

    public ReflectedJson() {
    }

    public ReflectedJson(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAllDeclaredConstructors() {
        return allDeclaredConstructors;
    }

    public void setAllDeclaredConstructors(boolean allDeclaredConstructors) {
        this.allDeclaredConstructors = allDeclaredConstructors;
    }

    public boolean isAllPublicConstructors() {
        return allPublicConstructors;
    }

    public void setAllPublicConstructors(boolean allPublicConstructors) {
        this.allPublicConstructors = allPublicConstructors;
    }

    public boolean isAllDeclaredMethods() {
        return allDeclaredMethods;
    }

    public void setAllDeclaredMethods(boolean allDeclaredMethods) {
        this.allDeclaredMethods = allDeclaredMethods;
    }

    public boolean isAllPublicMethods() {
        return allPublicMethods;
    }

    public void setAllPublicMethods(boolean allPublicMethods) {
        this.allPublicMethods = allPublicMethods;
    }

    public boolean isAllDeclaredClasses() {
        return allDeclaredClasses;
    }

    public void setAllDeclaredClasses(boolean allDeclaredClasses) {
        this.allDeclaredClasses = allDeclaredClasses;
    }

    public boolean isAllPublicClasses() {
        return allPublicClasses;
    }

    public void setAllPublicClasses(boolean allPublicClasses) {
        this.allPublicClasses = allPublicClasses;
    }
}

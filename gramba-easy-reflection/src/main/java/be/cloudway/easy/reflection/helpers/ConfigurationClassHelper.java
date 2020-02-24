package be.cloudway.easy.reflection.helpers;

import java.util.Arrays;
import java.util.List;

public class ConfigurationClassHelper {
    public static Boolean doesImplement (Class v, Class interfaceClass) {
        List interfaces = Arrays.asList(v.getInterfaces());
        return !interfaces.contains(interfaceClass);
    }
}

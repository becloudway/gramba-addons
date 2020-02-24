package be.cloudway.gramba.nativeimage.commands;

import be.cloudway.gramba.nativeimage.builder.CommandStringBuilder;

public class BaseCommand {
    public static String getCommand() {
        return new CommandStringBuilder().append("native-image")
            .append("--allow-incomplete-classpath")
            .append("--no-server")
            .append("--enable-http")
            .append("--enable-https")
            .append("--enable-url-protocols=http,https")
            .append("--enable-all-security-services")
            .append("-H:+ReportUnsupportedElementsAtRuntime")
            .append("-H:-AllowVMInspection")
        .build();
    }
}

package be.cloudway.gramba.nativeimage.commands;

import be.cloudway.gramba.nativeimage.builder.CommandStringBuilder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class OptionalCommands {
    public static String getCommand(List<String> delayedClasses, List<String> injectionFiles, File outputDirectory) {
        CommandStringBuilder commandStringBuilder = new CommandStringBuilder().appendOptional(
                () -> "--delay-class-initialization-to-runtime=" + String.join(",", delayedClasses),
                () -> delayedClasses != null && delayedClasses.size() > 0
        );

        if (injectionFiles == null) injectionFiles = new ArrayList<>();
        final List<String> finalInjectionFiles = injectionFiles;

        if (new File(outputDirectory.getAbsolutePath() + "/reflections.json").exists()) {
            injectionFiles.add(0, "/working/target/reflections.json");
        }

        return commandStringBuilder
                .appendOptional(
                        "-H:ReflectionConfigurationFiles=" + String.join(",/working/", injectionFiles)
                        , () -> finalInjectionFiles.size() > 0
                ).build();
    }
}

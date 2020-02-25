package be.cloudway.gramba.nativeimage.commands;

import be.cloudway.gramba.nativeimage.builder.CommandStringBuilder;
import org.apache.maven.plugin.logging.Log;

import java.io.File;
import java.util.List;

public class BuildCommand {
    private final Log log;
    private final CommandStringBuilder commandStringBuilder = new CommandStringBuilder();

    public BuildCommand(Log logger) {
        log = logger;
    }



    public String getCommand (List<String> delayedClasses, List<String> injectionFiles, File outputDirectory,
                              List<String> additionalOptions, String jarName, List<String> testFiles, boolean skipBuild) {
        if (!skipBuild) {
            commandStringBuilder.append(BaseCommand.getCommand());
            getAdditionalOptions(additionalOptions);
            commandStringBuilder.append(OptionalCommands.getCommand(delayedClasses, injectionFiles, outputDirectory));
            getBuildAndCopyCommands(jarName);
        }

        commandStringBuilder
                .append(TestingCommand.getCommand(testFiles, skipBuild));

        return commandStringBuilder.build();
    }

    public void getAdditionalOptions (List<String> additionalOptions) {
        if (additionalOptions != null && additionalOptions.size() > 0) {
            commandStringBuilder.append(" ");
            commandStringBuilder.append(String.join(" ", additionalOptions));
        }
    }


    public void getBuildAndCopyCommands (String jarname) {
        commandStringBuilder.appendNoSpace(" -jar /working/target/").appendNoSpace(jarname).append(".jar && echo '-- BUILD SUCCESSFUL --'");
        commandStringBuilder.close().appendNoSpace("mv ./").append(jarname).append("/working/target/function");
        commandStringBuilder.close().append("chmod 755 /working/target/function");
    }
}

package be.cloudway.gramba.nativeimage.commands;

import be.cloudway.gramba.nativeimage.builder.CommandStringBuilder;

import java.util.List;

public class TestingCommand {
    public static String getCommand (List<String> testFiles, boolean skipBuild) {
        CommandStringBuilder commandStringBuilder = new CommandStringBuilder();

        if (testFiles != null && testFiles.size() > 0) {
            if (!skipBuild)
                commandStringBuilder.close();
            else commandStringBuilder.append("echo '-- BUILD SUCCESSFUL --'").close();

            commandStringBuilder.append("cd").append("/runtime").close();

            commandStringBuilder.append("/working/target/function")
                    .append("-Djavax.net.ssl.trustStore=/runtime/cacerts")
                    .append("-Djavax.net.ssl.trustStorePassword=changeit")
                    .append("-Djava.library.path=/runtime/")
                    .append("-t")
                    .append("-m")
                    .appendNoSpace("/working/")
                    .append(testFiles.get(0));

            if (testFiles.size() > 1) {
                for (int i = 1; i < testFiles.size(); i++) {
                    commandStringBuilder.and()
                            .append("/working/target/function")
                            .append("-Djavax.net.ssl.trustStore=/runtime/cacerts")
                            .append("-Djavax.net.ssl.trustStorePassword=changeit")
                            .append("-Djava.library.path=/runtime/")
                            .append("-t")
                            .append("-m")
                            .appendNoSpace("/working/")
                            .append(testFiles.get(i));}
            }

            commandStringBuilder.and().append("echo '-- TESTS SUCCESSFUL --'");
        } else {
            commandStringBuilder.close().append("echo '-- NO TESTS --'");
        }

        return commandStringBuilder.build();
    }
}

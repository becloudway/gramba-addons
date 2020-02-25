package be.cloudway.gramba.nativeimage;

import be.cloudway.gramba.nativeimage.commands.BuildCommand;
import be.cloudway.gramba.nativeimage.helper.DockerHelper;
import be.cloudway.gramba.nativeimage.helper.ZipHelper;
import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.LogStream;
import com.spotify.docker.client.messages.ExecCreation;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;

import java.io.File;

public class NativeImageHandler {
    private BuildCommand commandBuilder;
    private DockerHelper dockerHelper;

    private final NativeImageMain m;

    private Log log;

    public NativeImageHandler (Log log, NativeImageMain main) {
        this.log = log;
        this.commandBuilder = new BuildCommand(log);
        dockerHelper = new DockerHelper(log);

        this.m = main;
    }


    private void zip (boolean createZip, File baseDir) throws MojoFailureException {
        if (!createZip) {
            return;
        }

        log.info("Creating function.zip file");
        ZipHelper.toFunctionZip(baseDir.getAbsolutePath() + "/target/function", baseDir + "/target/function.zip");
    }

    private void validateOutput (String output) throws MojoFailureException {
        if (!output.contains("-- BUILD SUCCESSFUL --")) {
            throw new MojoFailureException("Build failed!");
        }

        if (!(output.contains("-- TESTS SUCCESSFUL --") || output.contains("-- NO TESTS --"))) {
            throw new MojoFailureException("Tests failed!");
        }
    }

    private String getCommand () {
        return commandBuilder.getCommand(m.getDelayedClasses(), m.getInjectionFiles(), m.getOutputDirectory(), m.getAdditionalOptions(),
                m.getJarName(), m.getTestFiles(), m.isSkipBuild());
    }

    public void run () throws MojoExecutionException {
        DockerClient docker = null;
        String id = null;

        try {
            docker = DefaultDockerClient.fromEnv().build();
            id = dockerHelper.createContainer(docker, m.getBaseDir(), m.getEnvVariables(), m.getDockerImage());

            log.info("Starting container");
            docker.startContainer(id);

            log.info("Starting build of " + m.getJarName() + " this can take some time!");

            String buildCommand = getCommand();
            log.info("Running the following command --- " + buildCommand);

            String[] command = {"sh", "-c", buildCommand};
            ExecCreation execCreation = docker.execCreate(
                    id, command, DockerClient.ExecCreateParam.attachStdout(),
                    DockerClient.ExecCreateParam.attachStderr());

            LogStream output = docker.execStart(execCreation.id());
            String execOutput = output.readFully();

            log.info(execOutput);

            validateOutput(execOutput);
            zip(m.isCreateZip(), m.getBaseDir());


        } catch (Exception e) {
            e.printStackTrace();
            throw new MojoExecutionException(e.getMessage());
        } finally {
            if (id != null) {
                try {
                    docker.killContainer(id);
                    docker.removeContainer(id);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new MojoExecutionException(e.getMessage());
                } finally {
                    docker.close();
                    log.info("Finished creating and zipping of native-image");
                }
            }
        }
    }
}

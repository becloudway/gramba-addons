package be.cloudway.gramba.nativeimage;

import be.cloudway.gramba.nativeimage.commands.BuildCommand;
import be.cloudway.gramba.nativeimage.helper.DockerHelper;
import be.cloudway.gramba.nativeimage.helper.TeeOutputStream;
import be.cloudway.gramba.nativeimage.helper.ZipHelper;
import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.ExecCreateCmdResponse;
import com.github.dockerjava.api.model.PullResponseItem;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.command.ExecStartResultCallback;
import com.github.dockerjava.core.command.PullImageResultCallback;
import org.apache.commons.lang.SystemUtils;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;

import java.io.ByteArrayOutputStream;
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
        String localDockerHost = SystemUtils.IS_OS_WINDOWS ? "tcp://localhost:2375" : "unix:///var/run/docker.sock";
        DockerClient dockerClient = DockerClientBuilder.getInstance(localDockerHost).build();

        log.info("Pulling docker image: " + m.getDockerImage() + ":" + m.getDockerImageTag());
        try {
            dockerClient.pullImageCmd(m.getDockerImage()).withTag(m.getDockerImageTag())
                    .exec(new PullImageResultCallback(){
                        @Override
                        public void onNext(PullResponseItem item) {
                            super.onNext(item);
                        } })
                    .awaitCompletion();
            log.info("Finished pulling docker image");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        log.info("Creating docker container");
        String containerId = dockerHelper.createContainer(dockerClient, m.getBaseDir(), m.getEnvVariables(), m.getDockerImage());

        log.info("Starting container");
        dockerClient.startContainerCmd(containerId)
                .exec();

        String buildCommand = getCommand();

        String[] command = {"sh", "-c", buildCommand};

        ExecCreateCmdResponse execCreateCmdResponse = dockerClient.execCreateCmd(containerId)
                .withAttachStdout(true).withCmd(command).exec();

        log.info("Running the following command: " + buildCommand);
        ByteArrayOutputStream stdout = new ByteArrayOutputStream();
        TeeOutputStream tee = new TeeOutputStream(stdout, System.out);

        try {
            ExecStartResultCallback a = dockerClient.execStartCmd(execCreateCmdResponse.getId()).withDetach(false).withTty(true)
                    .exec(new ExecStartResultCallback(tee, System.err)).awaitCompletion();

            validateOutput(stdout.toString());
            zip(m.isCreateZip(), m.getBaseDir());

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                stdout.flush();
                stdout.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            dockerClient.stopContainerCmd(containerId).exec();
            dockerClient.removeContainerCmd(containerId).exec();
        }


    }
}

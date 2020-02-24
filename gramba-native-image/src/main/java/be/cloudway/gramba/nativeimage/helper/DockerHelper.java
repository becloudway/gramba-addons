package be.cloudway.gramba.nativeimage.helper;

import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.messages.ContainerConfig;
import com.spotify.docker.client.messages.ContainerCreation;
import com.spotify.docker.client.messages.HostConfig;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class DockerHelper {
    private Log log;

    public DockerHelper (Log log) {
        this.log = log;
    }

    public List<String> copiedEnvVariables (List<String> envVariables) {
        List<String> newEnvVariables = new ArrayList<>();

        envVariables.forEach(v -> {
            if (v.split("=").length == 2) {
                newEnvVariables.add(v);
            } else {
                String toCopy = System.getenv(v);
                newEnvVariables.add(v + "=" + toCopy);
            }
        });

        return newEnvVariables;
    }



    public String createContainer (DockerClient docker, File baseDir, List<String> envVariables, String dockerImage) throws MojoExecutionException {
        final HostConfig.Bind bind = HostConfig.Bind.from(baseDir.getAbsolutePath()).to("/working").build();

        log.info("Mounting folder: " + baseDir.getAbsolutePath() + "to /working");
        final HostConfig hostConfig = HostConfig.builder().appendBinds(bind).build();

        log.info("Creating container configuration");
        final ContainerConfig containerConfig = ContainerConfig.builder()
                .hostConfig(hostConfig)
                .image(dockerImage)
                .addVolume("graal")
                .env(copiedEnvVariables(envVariables))
                .cmd("sh", "-c", "while :; do sleep 1; done")
                .build();

        final ContainerCreation containerCreation;
        try {
            log.info("Docker creating container configuration");
            containerCreation = docker.createContainer(containerConfig);
        } catch (Exception e) {
            docker.close();
            throw new MojoExecutionException(e.getMessage());
        }

        String id = containerCreation.id();
        log.info("Created container with id: " + id);

        return id;
    }
}

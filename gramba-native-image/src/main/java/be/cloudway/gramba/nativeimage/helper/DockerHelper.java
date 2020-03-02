package be.cloudway.gramba.nativeimage.helper;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.model.Bind;
import com.github.dockerjava.api.model.Volume;
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
        Volume volume1 = new Volume("/working");

        CreateContainerResponse createContainerResponse = docker.createContainerCmd(dockerImage)
                .withVolumes(volume1)
                .withBinds(new Bind(baseDir.getAbsolutePath(), volume1))
                .withEnv(copiedEnvVariables(envVariables))
                .withCmd("sh", "-c", "while :; do sleep 1; done")
                .exec();

        return createContainerResponse.getId();
    }
}

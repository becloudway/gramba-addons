package be.cloudway.easy.reflection.helpers;

import be.cloudway.easy.reflection.model.ReflectedJson;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.maven.plugin.MojoExecutionException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class ReflectionJsonFileWriter {
    private static ObjectMapper objectMapper = new ObjectMapper();

    public static void writeFile(File outputDirectory, Object reflectedJsons, String fileName) throws MojoExecutionException {
        File f = outputDirectory;
        String output = "";

        if (!f.exists()) {
            f.mkdirs();
        }

        File touch = new File(f, fileName);


        FileWriter w = null;
        try {
            output = objectMapper.writeValueAsString(reflectedJsons);

            w = new FileWriter(touch);

            w.write(output);
        } catch (Exception e) {
            throw new MojoExecutionException("Error creating file " + touch, e);
        } finally {
            if (w != null) {
                try {
                    w.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }
    }
}

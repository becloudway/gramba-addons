package be.cloudway.gramba.nativeimage.helper;

import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.maven.plugin.MojoFailureException;

import java.io.*;

public class ZipHelper {
    public static void toFunctionZip(String toZipFile, String outputFileName) {
        try {
            File f1 = new File(toZipFile);

            final OutputStream out = new FileOutputStream(outputFileName);
            ArchiveOutputStream os = new ArchiveStreamFactory().createArchiveOutputStream(ArchiveStreamFactory.ZIP, out);

            ZipArchiveEntry zipArchiveEntry = new ZipArchiveEntry(f1.getName());
            zipArchiveEntry.setUnixMode(0755);

            os.putArchiveEntry(zipArchiveEntry);
            IOUtils.copy(new FileInputStream(f1), os);

            os.closeArchiveEntry();
            os.close();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
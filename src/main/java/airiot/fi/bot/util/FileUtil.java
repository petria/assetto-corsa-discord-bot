package airiot.fi.bot.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.*;

/**
 * Date: 11/26/13
 * Time: 8:55 AM
 *
 * @author Petri Airio <petri.j.airio@gmail.com>
 */
@Component
@Slf4j
public class FileUtil {

    private static final int COPY_BUF_SIZE = 1024;

    public FileUtil() {

    }

    public String extractPath(String filename) {
        int idx = filename.lastIndexOf(File.separatorChar);
        String dir = filename.substring(0, idx + 1);
        return dir;

    }

    public String getTmpDirectory() throws IOException {
        File tmpFile = File.createTempFile("___", "___");
        String path = tmpFile.getCanonicalPath();
        log.info("Deleting tmpFile {}", tmpFile.delete());
        return extractPath(path);
    }

    public String copyBytesToTmpFile(String prefix, byte[] content) throws IOException {
        File tmpFile = File.createTempFile(prefix, "");
        OutputStream out = new FileOutputStream(tmpFile);
        BufferedWriter bw = new BufferedWriter(new FileWriter(tmpFile));
        out.write(content);
        out.close();
        String tmpResourcePath = tmpFile.getAbsolutePath();
        log.info("byte[] copied resource to {}", tmpResourcePath);
        return tmpResourcePath;
    }

    public String copyBytesToTmpFile(byte[] content) throws IOException {
        return copyBytesToTmpFile("bytes", content);
    }

    public String copyResourceToFile(String resource, File target, StringBuilder... contents) throws IOException {
        InputStream inputStream = this.getClass().getResourceAsStream(resource);
        if (inputStream == null) {
            log.error("Resource not found: {}", resource);
            return null;
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        BufferedWriter bw = new BufferedWriter(new FileWriter(target));
        String line;
        while (true) {
            line = br.readLine();
            if (line == null) {
                break;
            }
            bw.write(line + "\n");
            if (contents != null && contents.length > 0) {
                contents[0].append(line);
                contents[0].append("\n");
            }
        }
        br.close();
        bw.flush();
        bw.close();

        String tmpResourcePath = target.getAbsolutePath();

        log.info("Copied resource {} to {}", resource, tmpResourcePath);
        return tmpResourcePath;
    }

    public String copyResourceToTmpFile(String resource, StringBuilder... contents) throws IOException {
        File tmpFile = File.createTempFile(resource, "");
        return copyResourceToFile(resource, tmpFile, contents);
    }

    public boolean deleteTmpFile(String tmpFile) {
        File f = new File(tmpFile);
        if (f.exists()) {
            log.info("Deleting file: {}", tmpFile);
            return f.delete();
        }
        return false;
    }

    public int copyFile(String fromFile, String toFile) {
        int copied = 0;
        try {
            File f1 = new File(fromFile);
            File f2 = new File(toFile);
            InputStream in = new FileInputStream(f1);

            //For Overwrite the file.
            OutputStream out = new FileOutputStream(f2);

            byte[] buf = new byte[COPY_BUF_SIZE];
            int len;
            while ((len = in.read(buf)) > 0) {
                copied += len;
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
        } catch (Exception ex) {
            log.error("Copy File failed '{}' -> '{}'", fromFile, toFile);
            return -1;
        }
        log.info("Copied '{}' -> '{}' size: " + copied, fromFile, toFile);
        return copied;
    }

}

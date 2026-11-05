package homework.copy;

import java.io.*;

public class FileCopyWithBuffer {

    public static long copyFile(String source, String dest) throws IOException {

        final int BUFFER_SIZE = 8192;

        long startTime = System.currentTimeMillis();

        try (BufferedInputStream bufferedInputStream =
                     new BufferedInputStream(new FileInputStream(source), BUFFER_SIZE);
             BufferedOutputStream bufferedOutputStream =
                     new BufferedOutputStream(new FileOutputStream(dest), BUFFER_SIZE)) {

            byte[] buffer = new byte[BUFFER_SIZE];

            int data;

            while ((data = bufferedInputStream.read(buffer)) != -1) {
                bufferedOutputStream.write(buffer, 0, data);
            }
        }
        return System.currentTimeMillis() - startTime;
    }
}

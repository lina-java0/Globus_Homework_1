package homework.copy;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileCopyNoBuffer {

    public static long copyFile(String source, String dest) throws IOException {

        long startTime = System.currentTimeMillis();

        try (FileInputStream inputStream = new FileInputStream(source);
             FileOutputStream outputStream = new FileOutputStream(dest)) {

            int data;

            while ((data = inputStream.read()) != -1) {
                outputStream.write(data);
            }
        }
        return System.currentTimeMillis() - startTime;
    }
}

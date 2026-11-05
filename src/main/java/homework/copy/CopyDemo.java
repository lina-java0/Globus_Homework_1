package homework.copy;

import java.io.IOException;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

public class CopyDemo {

    private static final String SOURCE =
            "src/main/resources/copy/test_100mb.bin";

    private static final String DEST_NO_BUFFER =
            "src/main/resources/copy/copy_no_buffer.bin";

    private static final String DEST_WITH_BUFFER =
            "src/main/resources/copy/copy_with_buffer.bin";

    public static void main(String[] args) throws UnsupportedEncodingException {

        System.setOut(new PrintStream(System.out, true, "UTF-8"));

        System.out.println("Сравнение копирования 100 МБ");

        try {
            // 1. Запускаем побайтовое чтение
            System.out.println("\n1. Копирование без буфера:");
            long timeNoBuffer = FileCopyNoBuffer.copyFile(SOURCE, DEST_NO_BUFFER);
            System.out.println("Время: " + timeNoBuffer + " мс");

            // 2. Запускаем копирование с буфером
            System.out.println("\n2. Копирование с буфером:");
            long timeWithBuffer = FileCopyWithBuffer.copyFile(SOURCE, DEST_WITH_BUFFER);
            System.out.println("Время: " + timeWithBuffer + " мс");

            double ratio = (double) timeNoBuffer / timeWithBuffer;
            System.out.printf("\nУскорение в %.2f раз%n", ratio);

        } catch (IOException e) { System.out.println("Ошибка: " + e.getMessage()); }
    }
}

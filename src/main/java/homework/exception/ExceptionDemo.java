package homework.exception;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

public class ExceptionDemo {

    public static void main(String[] args) throws UnsupportedEncodingException {

        System.setOut(new PrintStream(System.out, true, "UTF-8"));

        // Ресурсы создаются в порядке: Session (3), File (2), Connection (1)
        try (FakeSession session = new FakeSession("Session-3");
             FakeFile file = new FakeFile("File-2");
             FakeConnection connection = new FakeConnection("Connection-1")) {

            session.process();
            file.read();
            connection.doSomeWork(); // Здесь вылетит основное исключение
            // Ресурсы закрываются в порядке, обратном их объявлению в блоке try
        } catch (Exception ex) {
            System.out.println("\nИсключение поймано");
            System.out.println("Основное исключение: " + ex);

            Throwable[] suppressed = ex.getSuppressed();

            System.out.println("\nКоличество подавленных ошибок: " + suppressed.length);
            for (Throwable sup : suppressed) {
                System.out.println("Подавленная ошибка: " + sup);
            }
        }
    }
}

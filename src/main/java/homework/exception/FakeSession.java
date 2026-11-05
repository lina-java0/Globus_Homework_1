package homework.exception;

public class FakeSession implements AutoCloseable {

    private final String name;

    public FakeSession(String name) {
        this.name = name;
        System.out.println("Ресурс " + name + " захвачен");
    }

    public void process() {
        System.out.println("Сессия " + name + " обрабатывает данные");
    }

    @Override
    public void close() {
        System.out.println("Закрытие " + name);
        throw new RuntimeException("Ошибка во время закрытия " + name);
    }
}

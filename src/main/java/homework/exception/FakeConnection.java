package homework.exception;

public class FakeConnection implements AutoCloseable {

    private final String name;

    public FakeConnection(String name) {
        this.name = name;
        System.out.println("Ресурс " + name + " захвачен");
    }

    public void doSomeWork() {
        System.out.println("Соединение " + name + " выполняет работу");
        throw new IllegalStateException("Ошибка внутри блока: " + name);
    }

    @Override
    public void close() {
        System.out.println("Закрытие " + name);
        throw new RuntimeException("Ошибка во время закрытия " + name);
    }
}

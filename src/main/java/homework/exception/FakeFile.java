package homework.exception;

public class FakeFile implements AutoCloseable {

    private final String name;

    public FakeFile(String name) {
        this.name = name;
        System.out.println("Ресурс " + name + " захвачен");
    }

    public void read() {
        System.out.println("Файл " + name + " в режиме чтения");
    }

    @Override
    public void close() {
        System.out.println("Закрытие " + name);
    }
}

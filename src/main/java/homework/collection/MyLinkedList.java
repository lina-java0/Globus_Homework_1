package homework.collection;

import java.util.Iterator;
import java.util.NoSuchElementException;

// Реализация двусвязного списка (аналог LinkedList)
public class MyLinkedList<T> implements Iterable<T> {

    private Node<T> head;
    private Node<T> tail;
    private int size;

    // Получение размера
    public int size() {
        return size;
    }

    // Проверка, пуст ли список
    public boolean isEmpty() {
        return size == 0;
    }

    // Вставка в начало
    public void addFirst(T element) {
        add(0, element);
    }

    // Вставка в конец (дополнительный метод с понятным названием для пользователя)
    public void addLast(T element) {
        add(element);
    }

    // Добавление элемента без индекса в конец
    public boolean add(T element) {
        // используем другой перегруженный метод,
        // где индекс == size
        add(size, element);
        return true;
    }

    // Добавление элемента по индексу
    public void add(int index, T element) {
        checkPositionIndexForAdd(index);
        Node<T> newNode = new Node<>(element);

        // Случай 1: вставка в самый конец (index == size)
        if (index == size) {
            // Если вставка идет в конец, то ссылка previous
            // новой ноды указывает на хвост
            newNode.previous = tail;

            // Если список пуст,
            if (tail == null) {
                // то новая нода станет головой;
                head = newNode;
            } else {
                // если список не пуст, то вставляем новую ноду после хвоста
                tail.next = newNode;
            }
            // хвостом становится новая нода
            tail = newNode;
        }
        // Случай 2: вставка в начало или середину
        else {
            // Берем текущую ноду, на место которой хотим вставить новую ноду
            Node<T> current = getNode(index);

            // Переписываем ссылки новой ноды:
            // next указывает на ноду, которую мы сместили с ее места
            // previous указывает на ноду, которая была previous
            // у ноды, которую мы сместили
            newNode.next = current;
            newNode.previous = current.previous;

            // Если же элемент, который мы смещаем, был головой,
            if (current.previous == null) {
                // то голова теперь наша новая нода;
                head = newNode;
            } else {
                // если нет, то переписываем ссылки
                current.previous.next = newNode;
            }
            current.previous = newNode;
        }
        // увеличиваем размер списка
        size++;
    }

    // Получение элемента по индексу
    public T get(int index) {
        return getNode(index).data;
    }

    // Удаление элемента по индексу
    public T remove(int index) {
        // Получаем ноду, претендующую на удаление
        Node<T> current = getNode(index);

        // Если удаляем голову,
        if (current.previous == null) {
            // то голова теперь next;
            head = current.next;
        } else {
            // в противном случае, сшиваем предыдущую ноду со следующей,
            // перепрыгивая через удаляемую
            current.previous.next = current.next;
        }
        // Если удаляем хвост,
        if (current.next == null) {
            // то хвост теперь previous;
            tail = current.previous;
        } else {
            // в противном случае, сшиваем следующую ноду с предыдущей
            current.next.previous = current.previous;
        }
        // уменьшаем размер списка
        size--;

        T data = current.data;
        // Чистим ссылки в удаленной ноде, чтобы сборщик мусора точно её прибрал
        current.data = null;
        current.previous = null;
        current.next = null;

        // Возвращаем удаленное значение
        return data;
    }

    // Очистка списка
    public void clear() {
        Node<T> current = head;

        // Пробегаемся по всему списку и обнуляем всё подряд
        while (current != null) {
            Node<T> next = current.next;
            current.data = null;
            current.previous = null;
            current.next = null;
            current = next;
        }

        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Node<T> current = head;

            @Override
            public boolean hasNext() {
                return current != null;
            }

            @Override
            public T next() {
                if (current == null) {
                    throw new NoSuchElementException();
                }
                T data = current.data;
                current = current.next;
                return data;
            }
        };
    }

    // Оптимизированный поиск узла: идем от головы или хвоста в зависимости от индекса
    private Node<T> getNode(int index) {
        checkPositionIndex(index);

        // Если индекс в первой половине списка — идем от головы
        if (index < size / 2) {
            Node<T> current = head;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            return current;
        }

        // Если во второй — идем от хвоста, так быстрее
        Node<T> current = tail;
        for (int i = size - 1; i > index; i--) {
            current = current.previous;
        }
        return current;
    }

    // Проверка допустимых значений индекса
    private void checkPositionIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    // Проверка допустимых значений индекса для метода add
    private void checkPositionIndexForAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
    }

    // Класс для создания ноды в списке
    private static class Node<T> {
        private T data;
        private Node<T> previous;
        private Node<T> next;

        private Node(T data) {
            this.data = data;
        }
    }
}
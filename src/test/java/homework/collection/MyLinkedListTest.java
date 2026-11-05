package homework.collection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MyLinkedListTest {

    private MyLinkedList<Integer> list;

    @BeforeEach
    void setUp() {
        list = new MyLinkedList<>();
    }

    @Test
    void newListShouldBeEmpty() {
        assertTrue(list.isEmpty());
        assertEquals(0, list.size());
    }

    @Test
    void shouldAddAndGetElements() {
        list.add(10);
        list.add(20);
        list.add(30);

        assertEquals(3, list.size());
        assertEquals(10, list.get(0));
        assertEquals(20, list.get(1));
        assertEquals(30, list.get(2));
    }

    @Test
    void shouldAddFirstElement() {
        list.addFirst(20);
        list.addFirst(10);

        assertEquals(2, list.size());
        assertEquals(10, list.get(0));
        assertEquals(20, list.get(1));
    }

    @Test
    void shouldAddLastElement() {
        list.addLast(20);
        list.addLast(10);

        assertEquals(2, list.size());
        assertEquals(20, list.get(0));
        assertEquals(10, list.get(1));
    }

    @Test
    void shouldInsertAtBeginningMiddleAndEnd() {
        list.add(10);
        list.add(30);

        list.add(1, 20);
        list.add(0, 0);
        list.add(list.size(), 40);

        assertEquals(5, list.size());
        assertEquals(0, list.get(0));
        assertEquals(10, list.get(1));
        assertEquals(20, list.get(2));
        assertEquals(30, list.get(3));
        assertEquals(40, list.get(4));
    }

    @Test
    void shouldRemoveFirstMiddleAndLastElements() {
        list.add(10);
        list.add(20);
        list.add(30);
        list.add(40);

        assertEquals(10, list.remove(0));
        assertEquals(30, list.remove(1));
        assertEquals(40, list.remove(1));

        assertEquals(1, list.size());
        assertEquals(20, list.get(0));
    }

    @Test
    void shouldRemoveOnlyElement() {
        list.add(10);

        assertEquals(10, list.remove(0));
        assertTrue(list.isEmpty());
        assertEquals(0, list.size());

        list.add(20);

        assertEquals(20, list.get(0));
    }

    @Test
    void shouldClearAndAllowReuse() {
        list.add(1);
        list.add(2);

        list.clear();

        assertTrue(list.isEmpty());
        assertEquals(0, list.size());

        list.add(3);

        assertEquals(3, list.get(0));
    }

    @Test
    void shouldHandleAlternatingAddRemove() {
        list.add(1);
        list.add(2);
        list.remove(0);
        list.addFirst(3);
        list.remove(1);
        list.addLast(4);

        assertEquals(2, list.size());
        assertEquals(3, list.get(0));
        assertEquals(4, list.get(1));
    }

    @Test
    void shouldHandleManyElements() {
        int count = 10_000;
        for (int i = 0; i < count; i++) {
            list.add(i);
        }

        assertEquals(count, list.size());
        assertEquals(0, list.get(0));
        assertEquals(5000, list.get(5000));
        assertEquals(9999, list.get(9999));

        for (int i = 0; i < count; i++) {
            assertEquals(i, list.remove(0));
        }
        assertTrue(list.isEmpty());
    }

    @Test
    void shouldThrowForInvalidGetAndRemoveIndexes() {
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(0));
        assertThrows(IndexOutOfBoundsException.class, () -> list.remove(0));

        list.add(1);

        assertThrows(IndexOutOfBoundsException.class, () -> list.get(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> list.get(1));
        assertThrows(IndexOutOfBoundsException.class, () -> list.remove(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> list.remove(1));
    }

    @Test
    void shouldThrowForInvalidInsertIndexes() {
        assertThrows(IndexOutOfBoundsException.class, () -> list.add(-1, 1));
        assertThrows(IndexOutOfBoundsException.class, () -> list.add(1, 1));

        list.add(10);

        assertThrows(IndexOutOfBoundsException.class, () -> list.add(-1, 20));
        assertThrows(IndexOutOfBoundsException.class, () -> list.add(2, 20));
    }

    @Test
    void shouldAllowDuplicateObjects() {
        list.add(10);
        list.add(10);

        assertEquals(2, list.size());
        assertEquals(10, list.get(0));
        assertEquals(10, list.get(1));
    }
}
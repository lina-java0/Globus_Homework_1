package homework.money;

import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MoneyDemo {

    public static void main(String[] args) throws UnsupportedEncodingException {

        System.setOut(new PrintStream(System.out, true, "UTF-8"));

        System.out.println("\nДемонстрация бага: broken hashCode при правильном equals\n");

        // Создаем два объекта, которые равны по бизнес-логике
        BrokenMoney bm1 = new BrokenMoney(
                new BigDecimal("100.00"),
                Currency.CNY
        );
        BrokenMoney bm2 = new BrokenMoney(
                new BigDecimal("100.00"),
                Currency.CNY
        );

        // Проверяем контракт equals
        System.out.println("bm1.equals(bm2): " + bm1.equals(bm2)); // true
        System.out.println("bm1.hashCode() == bm2.hashCode(): "
                + (bm1.hashCode() == bm2.hashCode())); // false

        // 1. Демонстрация в HashSet
        System.out.println("\nHashSet");

        Set<BrokenMoney> brokenWallet = new HashSet<>();

        System.out.println("Добавляем bm1: " + brokenWallet.add(bm1)); // true
        System.out.println("Пытаемся добавить bm2 (равный bm1 по equals): "
                + brokenWallet.add(bm2)); // true

        // Баг: HashSet должен был схлопнуть дубликат, но не смог.
        // Из-за неправильного hashCode равные объекты могут попасть
        // в разные корзины, поэтому HashSet может не обнаружить дубликат
        System.out.println("Размер кошелька (ожидалось 1, по факту): "
                + brokenWallet.size()); // 2
        System.out.println("Содержимое кошелька: " + brokenWallet);

        // 2. Демонстрация в HashMap (потеря данных)
        System.out.println("\nHashMap");

        Map<BrokenMoney, String> brokenMap = new HashMap<>();

        // Кладем данные по ключу bm1
        brokenMap.put(bm1, "Деньги в юанях");

        // Пытаемся достать данные по равному ключу bm2
        String brokenRetrieved = brokenMap.get(bm2);

        System.out.println("Пытаемся достать значение по равному ключу bm2: "
                + brokenRetrieved); // null

        // Баг: HashMap не нашла значение.
        // Из-за несовпадения hashCode HashMap может не найти ключ,
        // даже если equals возвращает true
        System.out.println("Данные в Map фактически есть (по ключу bm1): "
                + brokenMap.get(bm1)); // "Деньги в юанях"

        System.out.println("\nДемонстрация работающей программы: hashCode и equals реализованы правильно\n");

        // Создаем два объекта, которые равны по бизнес-логике
        Money m1 = new Money(
                new BigDecimal("100.00"),
                Currency.RUB
        );

        Money m2 = new Money(
                new BigDecimal("100.00"),
                Currency.RUB
        );

        // Проверяем контракт equals
        System.out.println("m1.equals(m2): " + m1.equals(m2)); // true
        System.out.println("m1.hashCode() == m2.hashCode(): "
                + (m1.hashCode() == m2.hashCode())); // true
        System.out.println();

        // 1. Демонстрация в HashSet (правильная реализация)
        System.out.println("HashSet");

        Set<Money> workWallet = new HashSet<>();

        System.out.println("Добавляем m1: " + workWallet.add(m1)); // true
        System.out.println("Пытаемся добавить m2 (равный m1 по equals): "
                + workWallet.add(m2)); // false

        // HashSet cхлопнул дубликат
        System.out.println("Размер кошелька (ожидалось 1, по факту): " + workWallet.size()); // 1
        System.out.println("Содержимое кошелька: " + workWallet);
        System.out.println();

        // 2. Демонстрация в HashMap (правильная реализация)
        System.out.println("HashMap");

        Map<Money, String> workMap = new HashMap<>();

        // Кладем данные по ключу m1
        workMap.put(m1, "Деньги в рублях");

        // Получаем данные по равному ключу m2
        String workRetrieved = workMap.get(m2);

        System.out.println("Пытаемся достать значение по равному ключу m2: "
                + workRetrieved); // "Деньги в рублях"
        System.out.println("Данные в Map по ключу m1: "
                + workMap.get(m1)); // "Деньги в рублях"
    }
}
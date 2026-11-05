package homework.money;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

// Сломанный кошелек
// Завела отдельный класс, чтобы показать работу
// работающего и сломанного кошелька вместе
public final class BrokenMoney {

    // Требуемые поля по заданию
    private final BigDecimal amount;
    private final Currency currency;

    // Конструктор класса
    public BrokenMoney(BigDecimal amount, Currency currency) {
        this.amount = normalizeAmount(amount);
        this.currency = Objects.requireNonNull(currency, "Валюта не должна быть null");
    }

    // Переопределенный equals (пример брала из презентации)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BrokenMoney brokenMoney)) return false;

        // == безопасен только для enum Currency
        return Objects.equals(amount, brokenMoney.amount)
                && currency == brokenMoney.currency;
    }

    // Переопределенный неверно hashCode
    // hashCode зависит от адреса объекта, а не от его данных
    @Override
    public int hashCode() {
        return System.identityHashCode(this);
    }

    // toString для красоты вывода
    @Override
    public String toString() {
        return "Сумма: " + amount + " . " + "Валюта: " + currency;
    }

    // Нормализация вида суммы + проверка на введенную сумму:
    // 1. Сумма не должна быть null
    // 2. Введенная сумма не должна иметь более 2 цифр после запятой
    // 3. Приводим масштаб к 2 знакам (например, 100 -> 100.00) для единообразия
    private static BigDecimal normalizeAmount(BigDecimal amount) {
        BigDecimal nonNullAmount = Objects.requireNonNull(amount, "Amount must not be null");
        BigDecimal normalized = nonNullAmount.stripTrailingZeros();

        if (normalized.scale() > 2) {
            throw new ArithmeticException(
                    "Сумма должна иметь только 2 знака после запятой, но вы ввели: " + nonNullAmount);
        }

        return normalized.setScale(2, RoundingMode.UNNECESSARY);
    }
}

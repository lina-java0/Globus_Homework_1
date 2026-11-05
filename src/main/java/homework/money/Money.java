package homework.money;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

// Работающий кошелек
public final class Money {

    // Требуемые поля по заданию
    private final BigDecimal amount;
    private final Currency currency;

    // Конструктор класса
    public Money(BigDecimal amount, Currency currency) {
        this.amount = normalizeAmount(amount);
        this.currency = Objects.requireNonNull(currency, "Валюта не должна быть null");
    }

    // Переопределенный equals (пример брала из презентации)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Money money)) return false;

        // == безопасен только для enum Currency
        return Objects.equals(amount, money.amount)
                && currency == money.currency;
    }

    // Переопределенный hashCode (пример брала из презентации)
    @Override
    public int hashCode() {
        return Objects.hash(amount, currency);
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
        BigDecimal nonNullAmount = Objects.requireNonNull(amount, "Сумма не должна быть null");
        BigDecimal normalized = nonNullAmount.stripTrailingZeros();

        if (normalized.scale() > 2) {
            throw new ArithmeticException(
                    "Сумма должна иметь только 2 знака после запятой, но вы ввели: " + nonNullAmount);
        }

        return normalized.setScale(2, RoundingMode.UNNECESSARY);
    }
}

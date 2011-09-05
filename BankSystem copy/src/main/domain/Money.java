package main.domain;

import java.math.BigDecimal;

public class Money {
    public static final String errorMessageForDifferentCurrencyOperation = "Money does not support operations on different currency";
    private final BigDecimal value;
    private final Currency currency;

    public Money(BigDecimal value, Currency currency) {
        this.value = value;
        this.currency = currency;
    }

    @Override
    public String toString() {
        return "Money{" +
                "value=" + value +
                ", currency=" + currency +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Money)) {
            return false;
        }

        Money money = (Money) o;

        if (currency != null ? !currency.equals(money.currency) : money.currency != null) {
            return false;
        }
        if (value != null ? !value.equals(money.value) : money.value != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = value != null ? value.hashCode() : 0;
        result = 31 * result + (currency != null ? currency.hashCode() : 0);
        return result;
    }

    public Money convert(BigDecimal conversionRate, Currency toCurrency) {
        return new Money(value.multiply(conversionRate), toCurrency);
    }

    public Money add(Money rhs) {
        if (!currency.equals(rhs.currency)) {
            throw new RuntimeException(errorMessageForDifferentCurrencyOperation);
        }
        return new Money(this.value.add(rhs.value), currency);
    }

    public Money subtract(Money rhs) {
        if (!currency.equals(rhs.currency)) {
            throw new RuntimeException(errorMessageForDifferentCurrencyOperation);
        }
        return new Money(this.value.subtract(rhs.value), currency);
    }

    public boolean greaterThanEqualTo(Money rhs) {
        if (!currency.equals(rhs.currency)) {
            throw new RuntimeException(errorMessageForDifferentCurrencyOperation);
        }
        return this.value.compareTo(rhs.value) >= 0;
    }


    public boolean isSameCurrency(Currency currency) {
        return this.currency.equals(currency);
    }
}

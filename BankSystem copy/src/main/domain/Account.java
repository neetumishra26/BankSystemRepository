package main.domain;

public class Account {
    private Money money;
    private String accountId;
    private Currency inCurrency;

    public Account(String accountId, Currency inCurrency, Money money) {
        if (!money.isSameCurrency(inCurrency)) {
            throw new RuntimeException("Cannot create account with balance in different currency");
        }
        this.inCurrency = inCurrency;
        this.money = money;
        this.accountId = accountId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Account)) {
            return false;
        }

        Account account = (Account) o;

        if (accountId != null ? !accountId.equals(account.accountId) : account.accountId != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return accountId != null ? accountId.hashCode() : 0;
    }

    public boolean isConversionRequiredForTransactionIn(Currency currency) {
        if (this.inCurrency.equals(currency)) {
            return false;
        }
        return true;
    }

    public void deposit(Money amountToDeposit) {
        this.money = this.money.add(amountToDeposit);

    }

    public void withdraw(Money amountToWithdraw) throws InsufficientFundsException {
        if (!this.money.greaterThanEqualTo(amountToWithdraw)) {
            throw new InsufficientFundsException();
        }
        this.money = this.money.subtract(amountToWithdraw);
    }

    public Money getBalance() {
        return money;
    }

}

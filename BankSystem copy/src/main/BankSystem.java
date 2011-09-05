package main;

import main.ForeignExchangeManager.ForeignExchangeServiceImpl;
import main.Repositories.AccountRepository;
import main.UserInterface.ConsoleInterfaceImpl;
import main.domain.Account;
import main.domain.Currency;
import main.domain.InsufficientFundsException;
import main.domain.Money;

import java.math.BigDecimal;

public class BankSystem {
    AccountRepository accountRepository;
    ForeignExchangeServiceImpl foreignExchangeServiceImpl;
    Menu menu;

    public BankSystem(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public BankSystem(AccountRepository accountRepository, ForeignExchangeServiceImpl foreignExchangeServiceImpl) {
        this.accountRepository = accountRepository;
        this.foreignExchangeServiceImpl = foreignExchangeServiceImpl;
    }

    public BankSystem(AccountRepository accountRepository, ForeignExchangeServiceImpl foreignExchangeServiceMock, ConsoleInterfaceImpl consoleInterface) {
        this.accountRepository = accountRepository;
        this.foreignExchangeServiceImpl = foreignExchangeServiceMock;
        menu = new Menu(consoleInterface);
    }

    public BankSystem(AccountRepository accountRepository, ForeignExchangeServiceImpl foreignExchangeServiceMock, Menu menu) {
        this.accountRepository = accountRepository;
        this.foreignExchangeServiceImpl = foreignExchangeServiceMock;
        this.menu = menu;
    }

    public void transfer(String fromAccountId, String toAccountId, Money money) throws InsufficientFundsException {
        Account toAccount = accountRepository.load(toAccountId);
        Account fromAccount = accountRepository.load(fromAccountId);
        Money moneyInINR;
        Money moneyInCAD;

        System.out.println("1");
        fromAccount.withdraw(money);
        System.out.println("2");
        if(!money.isSameCurrency(Currency.INR))
        {
            BigDecimal conversionRate = foreignExchangeServiceImpl.conversionRate(Currency.USD , Currency.INR);
            moneyInINR = money.convert( conversionRate,Currency.INR);
        }
        else {
            moneyInINR = money;
        }

        if(toAccount.isConversionRequiredForTransactionIn(Currency.INR)){
            BigDecimal conversionRate = foreignExchangeServiceImpl.conversionRate(Currency.INR , Currency.CAD);
            moneyInCAD = moneyInINR.convert(conversionRate , Currency.CAD);
            toAccount.deposit(moneyInCAD);
        }
        else {
            toAccount.deposit(moneyInINR);
        }

        accountRepository.update(toAccount);
        accountRepository.update(fromAccount);
    }


    public void transferByTakingUserInput() throws InsufficientFundsException {
        String[] detailsOfAccount = menu.performSelection();
        Currency currency = new Currency(detailsOfAccount[3]);
        //Money money = new Money(new BigDecimal(10), currency);
        Money money = new Money(new BigDecimal(detailsOfAccount[2]), currency);
        System.out.println(money.toString());
        transfer(detailsOfAccount[0] , detailsOfAccount[1], money);
    }
}

package test;

import main.BankSystem;
import main.ForeignExchangeManager.ForeignExchangeService;
import main.ForeignExchangeManager.ForeignExchangeServiceImpl;
import main.Menu;
import main.Repositories.AccountRepository;
import main.UserInterface.ConsoleInterfaceImpl;
import main.domain.Account;
import main.domain.Currency;
import main.domain.InsufficientFundsException;
import main.domain.Money;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class BankSystemTest {
    AccountRepository accountRepository;
    BankSystem bankSystem;
    Currency currency ;
    Currency currencyOfToAccount ;
    Currency currencyOfFromAccount ;
    Money moneyInAccount;
    Money moneyInToAccount;
    Money moneyInFromAccount;
    ForeignExchangeServiceImpl foreignExchangeServiceMock;
    ConsoleInterfaceImpl consoleInterface;
    Menu menu;

    @Before
    public void setUp()
    {
        accountRepository = mock(AccountRepository.class);
        foreignExchangeServiceMock = mock(ForeignExchangeServiceImpl.class);
        consoleInterface = mock(ConsoleInterfaceImpl.class);
        menu = new StubbedMenu();

        //bankSystem = new BankSystem(accountRepository , foreignExchangeServiceMock, consoleInterface);
        bankSystem = new BankSystem(accountRepository , foreignExchangeServiceMock, menu);

        currency = Currency.INR;
        currencyOfToAccount = Currency.CAD;
        currencyOfFromAccount = Currency.USD;
        moneyInAccount = new Money(new BigDecimal(1234),currency);
        moneyInToAccount = new Money(new BigDecimal(1234),currencyOfToAccount);
        moneyInFromAccount = new Money(new BigDecimal(1234),currencyOfFromAccount);
    }

    @Test
    public void testShouldTransfer() throws InsufficientFundsException {
        Account someToAccount = new Account("123" , currency, moneyInAccount );
        Account someFromAccount = new Account("456" , currency, moneyInAccount );

        when(accountRepository.load("123")).thenReturn(someToAccount);
        when(accountRepository.load("456")).thenReturn(someFromAccount);

        Money moneyToTranfer = new Money(new BigDecimal(20), Currency.INR);
        this.bankSystem.transfer("456" , "123", moneyToTranfer);

        verify(accountRepository).load("123");
        verify(accountRepository).load("456");

        verify(accountRepository).update(someToAccount); //verification to the behaviour of the code
        verify(accountRepository).update(someFromAccount);

        assertEquals(someToAccount.getBalance(), new Money(new BigDecimal(1254), Currency.INR));
        assertEquals(someFromAccount.getBalance(), new Money(new BigDecimal(1214), Currency.INR));
    }

    @Test
    public void testShouldTransferBetweenDifferenForeignExchange() throws InsufficientFundsException {
        Account someToAccount = new Account("123" , currencyOfToAccount, moneyInToAccount );
        Account someFromAccount = new Account("456" , currencyOfFromAccount, moneyInFromAccount );
        Money moneyToTranfer = new Money(new BigDecimal(10), currencyOfFromAccount);

        when(accountRepository.load("123")).thenReturn(someToAccount);
        when(accountRepository.load("456")).thenReturn(someFromAccount);
        when(foreignExchangeServiceMock.conversionRate(currencyOfFromAccount, Currency.INR)).thenReturn(new BigDecimal("47"));
        when(foreignExchangeServiceMock.conversionRate(Currency.INR , currencyOfToAccount)).thenReturn(new BigDecimal("0.02"));

        this.bankSystem.transfer("456", "123", moneyToTranfer);

        verify(accountRepository).load("123");
        verify(accountRepository).load("456");

        verify(foreignExchangeServiceMock).conversionRate(currencyOfFromAccount , currency);
        verify(foreignExchangeServiceMock).conversionRate(currency, currencyOfToAccount);
        verify(accountRepository).update(someToAccount);
        verify(accountRepository).update(someFromAccount);

        assertTrue("Not Equal","Money{value=1243.40, currency=Currency{currencyCode='CAD'}}".equals(someToAccount.getBalance().toString()));
        //assertEquals(someToAccount.getBalance(), new Money(new BigDecimal(1243.40), currencyOfToAccount));
        assertEquals(someFromAccount.getBalance(), new Money(new BigDecimal(1224), currencyOfFromAccount));
    }

    @Test
    public void testCanTakeUserInputForTransferingfund() throws InsufficientFundsException{
        Account someToAccount = new Account("123" , currency, moneyInAccount );
        Account someFromAccount = new Account("456" , currency, moneyInAccount );

        when(accountRepository.load("123")).thenReturn(someToAccount);
        when(accountRepository.load("456")).thenReturn(someFromAccount);
        when(consoleInterface.getOptionNumber()).thenReturn(1);

        this.bankSystem.transferByTakingUserInput();

        verify(accountRepository).load("456");
        verify(accountRepository).load("123");
        verify(accountRepository).update(someToAccount);
        verify(accountRepository).update(someFromAccount);

        assertEquals(someFromAccount.getBalance(), new Money(new BigDecimal(1224), currency));
        assertEquals(someToAccount.getBalance(), new Money(new BigDecimal(1244), currency));
    }

}

package test.domain;

import junit.framework.Assert;
import main.domain.*;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

public class AccountTest {
    public static final String accountId = "232424";
    private Account account;
    private Money accountStartingBalance;

    @Before
    public void setUp() {
        accountStartingBalance = new Money(new BigDecimal(23), Currency.CAD);
        account = new Account(accountId, Currency.CAD, accountStartingBalance);
    }


    @Test
    public void testEquals() {
        Assert.assertFalse(account.equals(null));
        Assert.assertFalse(account.equals(new Object()));
        Assert.assertFalse(account.equals(new Account("1111", Currency.INR, new Money(new BigDecimal(12), Currency.INR))));
        Assert.assertTrue(account.equals(new Account(accountId, Currency.CAD, accountStartingBalance)));
        Assert.assertTrue(account.equals(account));
    }

    @Test
    public void testIsConversionRequiredForTransactionIn() {
        Assert.assertEquals(true, account.isConversionRequiredForTransactionIn(Currency.INR));
    }

    @Test
    public void testWithdraw() throws Exception {
        account.withdraw(new Money(new BigDecimal(10), Currency.CAD));
        Money remainingBalance = account.getBalance();
        Money expectedBalance = new Money(new BigDecimal(13), Currency.CAD);
        Assert.assertEquals(expectedBalance, remainingBalance);

    }

    @Test(expected = InsufficientFundsException.class)
    public void testShouldThrowExceptionForWithdrawalOFMoreThanBalance() throws Exception {
        account.withdraw(new Money(new BigDecimal(50), Currency.CAD));
    }


    @Test
    public void testDeposit() {
        account.deposit(new Money(new BigDecimal(12), Currency.CAD));
        Money remainingBalance = account.getBalance();
        Money expectedBalance = new Money(new BigDecimal(35), Currency.CAD);
        Assert.assertEquals(expectedBalance, remainingBalance);
    }

}

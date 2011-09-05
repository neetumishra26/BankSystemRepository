package test.domain;

import main.domain.Currency;
import main.domain.Money;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class MoneyTest {
    private Money money;

    @Test
    public void testEquals() {
        money = new Money(new BigDecimal(23), Currency.CAD);
        Assert.assertFalse(money.equals(null));
        Assert.assertFalse(money.equals(new Object()));
        Assert.assertFalse(money.equals(new Money(new BigDecimal(45), Currency.CAD)));
        Assert.assertTrue(money.equals(new Money(new BigDecimal(23), Currency.CAD)));
    }

    @Test
    public void testConvert() {
        Money money = new Money(new BigDecimal(12), Currency.CAD);
        Money expectedProduct = new Money(new BigDecimal(540), Currency.INR);
        Money product = money.convert(new BigDecimal(45), Currency.INR);
        Assert.assertEquals(expectedProduct, product);
    }

    @Test
    public void testAdd() {
        Money money = new Money(new BigDecimal(12), Currency.CAD);
        Money addedResult = money.add(new Money(new BigDecimal(12), Currency.CAD));
        Money expectedResult = new Money(new BigDecimal(24), Currency.CAD);
        Assert.assertEquals(expectedResult, addedResult);

    }

    @Test(expected = RuntimeException.class)
    public void testShouldThrowErrorOnAdditionOfDifferentCurrency() throws Exception {
        Money money = new Money(new BigDecimal(12), Currency.CAD);
        money.add(new Money(new BigDecimal(12), Currency.INR));
    }

    @Test
    public void testSubtract() {
        Money money = new Money(new BigDecimal(24), Currency.CAD);
        Money subtractedResult = money.subtract(new Money(new BigDecimal(12), Currency.CAD));
        Money expectedResult = new Money(new BigDecimal(12), Currency.CAD);
        Assert.assertEquals(expectedResult, subtractedResult);
    }

    @Test(expected = RuntimeException.class)
    public void testShouldThrowErrorOnSubtractionOfDifferentCurrency() throws Exception {
        Money money = new Money(new BigDecimal(24), Currency.CAD);
        money.subtract(new Money(new BigDecimal(12), Currency.INR));
    }

    @Test
    public void testGreaterThan() {
        money = new Money(new BigDecimal(23), Currency.CAD);
        Money rhs = new Money(new BigDecimal(45), Currency.CAD);
        Assert.assertFalse(money.greaterThanEqualTo(rhs));
        rhs = new Money(new BigDecimal(23), Currency.CAD);
        Assert.assertTrue(money.greaterThanEqualTo(rhs));
        rhs = new Money(new BigDecimal(12), Currency.CAD);
        Assert.assertTrue(money.greaterThanEqualTo(rhs));

    }

    @Test(expected = RuntimeException.class)
    public void testShouldThrowExceptionForGreaterThanOperationWithDifferentCurrency() {
        money = new Money(new BigDecimal(23), Currency.CAD);
        Money rhs = new Money(new BigDecimal(23), Currency.INR);
        money.greaterThanEqualTo(rhs);

    }

}

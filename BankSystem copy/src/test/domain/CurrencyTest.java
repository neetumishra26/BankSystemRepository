package test.domain;

import main.domain.Currency;
import org.junit.*;


public class CurrencyTest {

    @Test
    public void testParse()
    {
        Assert.assertEquals(Currency.CAD,Currency.parse("CAD"));
        Assert.assertEquals(Currency.INR,Currency.parse("INR"));
        Assert.assertEquals(Currency.USD,Currency.parse("USD"));
    }


}

package main.ForeignExchangeManager;

import main.domain.Currency;

import java.math.BigDecimal;

public class ForeignExchangeServiceImpl implements ForeignExchangeService{
    public BigDecimal conversionRate(Currency from, Currency to) {
        return null;
        //This will interact with an external service to the conversion rate. Should not be implemented for the exercises.
    }
}

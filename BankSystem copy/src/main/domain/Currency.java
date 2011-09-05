package main.domain;

public class Currency {
    private String currencyCode;

    public static final Currency CAD = new Currency("CAD");
    public static final Currency USD = new Currency("USD");
    public static final Currency INR = new Currency("INR");

    public Currency(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    @Override
    public String toString() {
        return "Currency{" +
                "currencyCode='" + currencyCode + '\'' +
                '}';
    }
    public static Currency parse(String currency)
    {
        currency = currency.trim();
        if("CAD".compareToIgnoreCase(currency) == 0)
           return CAD;
       if("USD".compareToIgnoreCase(currency) == 0)
           return USD;
       if("INR".compareToIgnoreCase(currency) == 0)
           return INR;
        throw new RuntimeException("Invalid Currency");
    }
}

package main.domain;

public class InsufficientFundsException extends Exception {

    public InsufficientFundsException() {
        super("Account has insufficient funds for the transaction");
    }

}

package main.bank.seabank;

public abstract class Account {
    private double balance;
    private int accNumber;
    private Password accPass;

    public Account(double startingBalance, int accountNumber, Password p) {
        balance = startingBalance;
        accNumber = accountNumber;
        accPass = p;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public void withdraw(double amount) {
        balance -= amount;
    }

    public abstract void transaction(double amount, String typeOfCard);
}

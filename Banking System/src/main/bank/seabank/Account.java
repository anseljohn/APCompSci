package main.bank.seabank;

import java.io.File;

public abstract class Account {
    private double balance;
    private int accNumber;
    private Password accPass;

    public Account(double startingBalance, int accountNumber, Password p) {
        balance = startingBalance;
        accNumber = accountNumber;
        accPass = p;
        new File("/home/xephy/Desktop/Dev/APCompSci/Banking System/UserAccs/" + accNumber + ".txt");
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public void withdraw(double amount) {
        balance -= amount;
    }

    public abstract void transaction(double amount, String typeOfCard);
}

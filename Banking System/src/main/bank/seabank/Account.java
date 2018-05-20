package main.bank.seabank;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

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

    public void writeToFile() {
        try {
            PrintWriter write = new PrintWriter(accNumber + ".txt", "UTF-8");
        } catch(FileNotFoundException e) {
            System.out.println("U bad where's ur file");
        } catch(UnsupportedEncodingException e) {
            System.out.println("haha u bad encoding bad ha");
        }

    }
}

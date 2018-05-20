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
        writeToFile();
    }

    public void deposit(double amount) {
        balance += amount;
        writeToFile();
    }

    public void withdraw(double amount) {
        balance -= amount;
        writeToFile();
    }

    public abstract void transaction(double amount, String typeOfCard);

    public void writeToFile() {
        try {
            PrintWriter writer = new PrintWriter("/home/xephy/Desktop/Dev/APCompSci/Banking System/UserAccs/" + accNumber + ".txt", "UTF-8");
            writer.println("\"accNumber\":" + accNumber);
            writer.println("\"pass\":\"" + accPass.getPass() + "\"");
            writer.println("\"balance\":" + balance);
            writer.close();
        } catch(FileNotFoundException e) {
            System.out.println("U bad where's ur file");
        } catch(UnsupportedEncodingException e) {
            System.out.println("haha u bad encoding bad ha");
        }
    }
}

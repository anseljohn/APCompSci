package main.bank.seabank;

import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.println("Welcome to Sea Bank!\n");
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch(InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("Please select an item below:");
        try {
            TimeUnit.MILLISECONDS.sleep(150);
        } catch(InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("--Login--");
        try {
            TimeUnit.MILLISECONDS.sleep(150);
        } catch(InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("--Create Account--");
        System.out.print(">> ");

        Account a = new CheckingAccount(123.0, 1234, new Password("Abc123!"));
        a.deposit(200.0);

    }
}

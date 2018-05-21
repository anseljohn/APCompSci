package main.bank.seabank;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/*
    TODO
    1) Try catches for inputmismatchexceptions
 */


public class Main {
    private static Scanner s = new Scanner(System.in);
    public static void main(String[] args) {
        System.out.println("Welcome to Sea Bank!\n");
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch(InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("Please select an item below:");
        System.out.println("(0) Login");
        System.out.println("(1) Create Account");
        System.out.print(">> ");
        int mainMenu = s.nextInt();
        if(mainMenu == 0) {
            login();
        }
        else if(mainMenu == 1) {
           System.out.println("coo create acc");
        }
    }

    public static void login() {
        System.out.print("Account number: ");
        int login_accNum = s.nextInt();
        if(new File(System.getProperty("user.dir") + "/data/accounts/" + login_accNum + ".txt").exists()) {
            System.out.println("Account #" + login_accNum + " found!\n");
            System.out.print("Account password: ");
            String login_pass = s.next();

            if(login_pass.equals(Account.decryptPass(login_accNum))) {
                display(login_accNum);
            }
        }
    }

    public static void display(int accountToDisplay) {
        System.out.println("Account #" + accountToDisplay);
        System.out.println("\tBalance: $" + Account.getBalance(accountToDisplay));
        System.out.println("\n\t(0) Withdraw Money");
        System.out.println("\t(1) Deposit Money");
        System.out.println("\t(2) Log a Transaction");

        int accountOption = s.nextInt();
        if(accountOption == 0) {

        }
        else if(accountOption == 1) {

        }
        else if(accountOption == 2) {

        }

    }
}

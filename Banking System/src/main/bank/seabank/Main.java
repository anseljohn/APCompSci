package main.bank.seabank;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {
    private static Scanner s = new Scanner(System.in);
    public static void main(String[] args) {
//        System.out.println("Welcome to Sea Bank!\n");
//        try {
//            TimeUnit.SECONDS.sleep(3);
//        } catch(InterruptedException e) {
//            Thread.currentThread().interrupt();
//        }
//        System.out.println("Please select an item below:");
//        try {
//            TimeUnit.MILLISECONDS.sleep(150);
//        } catch(InterruptedException e) {
//            Thread.currentThread().interrupt();
//        }
//        System.out.println("(0) Login");
//        try {
//            TimeUnit.MILLISECONDS.sleep(150);
//        } catch(InterruptedException e) {
//            Thread.currentThread().interrupt();
//        }
//        System.out.println("(1) Create Account");
//        System.out.print(">> ");
//        int mainMenu = s.nextInt();
//        if(mainMenu == 0) {
//            login();
//        }
//        else if(mainMenu == 1) {
//           System.out.println("coo create acc");
//        }
        Account acc = new CheckingAccount(100.0, 1, new Password("Abcd123!"));
        System.out.println("" + Account.decryptPass(1));
    }

    public static void login() {
        System.out.print("Account number: ");
        int login_accNum = s.nextInt();
        if(new File(System.getProperty("user.dir") + "/data/accounts/" + login_accNum + ".txt").exists()) {
            System.out.println("Account #" + login_accNum + " found!\n");
            System.out.println("Account password: ");
            String login_pass = s.next();

            String actualPass;
            try {
                Files.lines(Paths.get(System.getProperty("user.dir") + "/data/pass/" + login_accNum + "pass.txt"));
            } catch (Exception e) {
                System.out.println("An error has occurred");
            }
        }
    }
}

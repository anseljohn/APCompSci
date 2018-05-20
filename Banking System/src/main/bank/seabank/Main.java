package main.bank.seabank;

import java.io.File;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class Main {
    private static Scanner s = new Scanner(System.in);
    public static void main(String[] args) {
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
        System.out.println("(1) Login");
        try {
            TimeUnit.MILLISECONDS.sleep(150);
        } catch(InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("(2) Create Account");
        System.out.print(">> ");
        int mainMenu = s.nextInt();
        if(mainMenu == 2) {
            System.out.println("cool create acc");
        }
        else if(mainMenu == 1) {
           login();
        }
    }

    public static void login() {
        System.out.print("Account number: ");
        int login_accNum = s.nextInt();
        if(new File(System.getProperty("user.dir") + "/" + login_accNum + ".txt").exists()) {
            System.out.println("");
        }
    }
}

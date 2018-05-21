package main.bank.seabank;

import java.io.File;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.text.NumberFormat;
//import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

/*
    TODO
    1) Logged in account transactions
    2) Branch 2: Create Account
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
        mainMenu();
    }

    public static void mainMenu() {
        System.out.println("\nPlease select an item below:");
        System.out.println("(0) Login");
        System.out.println("(1) Create Account");
        System.out.println("(2) Exit");
        System.out.print(">> ");
        try {
            int mainMenu = parseInt(s.next());
            if (mainMenu == 0) {
                login();
            }
            else if(mainMenu == 1) {
                System.out.println("coo create acc"); //add in account creation
            }
            else if(mainMenu == 2) {
                System.exit(0);
            }
            else {
                System.out.println("\nPlease select a number from the list below");
                mainMenu();
            }
        } catch(NumberFormatException e) {
            System.out.println("\nPlease enter an integer\n");
            mainMenu();
        }
    }


    /*
        START OF TREE FOR login OPTION
     */
    public static void login() {
        System.out.print("\nAccount number (or enter 0 to go back): ");
        try {
            int login_accNum = parseInt(s.next());
            if(login_accNum == 0) {
                mainMenu();
            }
            if (new File(System.getProperty("user.dir") + "/data/accounts/" + login_accNum + ".txt").exists()) {
                System.out.println("\nAccount #" + login_accNum + " found!");
                promptForPass(login_accNum);
            }
            else {
                accNonexistant(login_accNum);
            }
        } catch(NumberFormatException e) {
            System.out.println("\nPlease enter an integer\n");
            login();
        }

    }

    public static void accNonexistant(int nonExistentAcc) {
        System.out.println("\nAccount #" + nonExistentAcc + " does not seem to exist.\n"  +
                "Would you like to create an account?\n");
        System.out.print("(yY/nN)>> ");
        String createAcc_failedLogin = s.next();
        if(createAcc_failedLogin.toLowerCase().equals("y")) {
            System.out.println("go to create account"); //fill in later with method call
        }
        else if(createAcc_failedLogin.toLowerCase().equals("n")) {
            retryLogin();
        }
        else {
            System.out.println("\nPlease enter (yY/nN)");
            accNonexistant(nonExistentAcc);
        }
    }

    public static void retryLogin() {
        System.out.println("\nRetry login?");
        System.out.print("(yY/nN)>> ");

        String retry = s.next();
        if(retry.toLowerCase().equals("y")) {
            login();
        }
        else if(retry.toLowerCase().equals("n")) {
            mainMenu();
        }
        else {
            System.out.println("\nPlease enter (yY/nN)");
            retryLogin();
        }
    }

    public static void promptForPass(int accToAccess) {
        System.out.print("Account password (or enter 0 to go back): ");
        String login_pass = s.next();

        if (login_pass.equals("0")) {
            login();
        }
        if(login_pass.equals(Account.decryptPass(accToAccess))) {
            display(accToAccess);
        }
        else {
//            cls();
            System.out.println("\nIncorrect password!");
            promptForPass(accToAccess);
        }
    }

    public static void display(int accountToDisplay) {
        System.out.println("\nAccount #" + accountToDisplay);
        System.out.println("\n\tBalance: $" + Account.getBalance(accountToDisplay));
        System.out.println("\n\t(0) Withdraw Money");
        System.out.println("\t(1) Deposit Money");
        System.out.println("\t(2) Log a Transaction");
        System.out.println("\t(3) Log out");
        System.out.print("\n>> ");

        try {
            int accountOption = parseInt(s.next());
            if (accountOption == 0) {
                withdrawMon(accountToDisplay);
            }
            else if(accountOption == 1) {
                depositMon(accountToDisplay);
            }
            else if(accountOption == 2) {

            }
            else if(accountOption == 3) {
                mainMenu();
            }
        } catch(NumberFormatException e) {
            System.out.println("\nPlease enter an integer\n");
        }

    }

    public static void withdrawMon(int accToWithdrawFrom){
        System.out.println("\nBalance: " + Account.getBalance(accToWithdrawFrom));
        System.out.print("Amount to withdraw (or enter 0 to go back): $");
        try {
            double amountToWithdraw = parseDouble(s.next());
            if(amountToWithdraw == 0.0) display(accToWithdrawFrom);
            else if(amountToWithdraw < 0) {
                System.out.println("\n\n\n\n\nCannot withdraw a negative amount!");
                withdrawMon(accToWithdrawFrom);
            }
            else {
                Account.withdraw(amountToWithdraw, accToWithdrawFrom);
                System.out.println("Successfully withdrawn $" + amountToWithdraw + " from account #" + accToWithdrawFrom + "!");
                display(accToWithdrawFrom);
            }
        } catch(NumberFormatException e) {
            System.out.println("\n\n\n\n\nPlease enter a decimal");
            withdrawMon(accToWithdrawFrom);
        }
    }

    public static void depositMon(int accToDepositTo) {
        System.out.println("\nBalance: " + Account.getBalance(accToDepositTo));
        System.out.print("Amount to deposit (or 0 to go bacl): $");
        try {
            double amountToDeposit = parseDouble(s.next());
            if(amountToDeposit == 0.0) display(accToDepositTo);
            else if(amountToDeposit < 0) {
                System.out.println("\n\n\n\n\nCannot deposit a negative amount!");
                depositMon(accToDepositTo);
            }
            else {
                Account.deposit(amountToDeposit, accToDepositTo);
            }
        } catch(NumberFormatException e) {
            System.out.println("\n\n\n\n\nPlease enter a decimal");
            depositMon(accToDepositTo);
        }
    }
    /*
        END OF TREE FOR login OPTION
     */



    /*
        START OF TREE FOR create account OPTION
     */
    public static void createAccount() {

    }
    public static void promptForNewUser() {

    }


//    public static void cls() {
//        if(System.getProperty("os.name").toLowerCase().indexOf("win") > -1) {
//            try {
//                Runtime.getRuntime().exec("cls");
//            } catch(Exception e) {
//                System.out.println("Command not found: cls");
//            }
//        }
//        else if(System.getProperty("os.name").toLowerCase().indexOf("ux") > -1) {
//            try {
//                Runtime.getRuntime().exec("clear");
//            } catch(Exception e) {
//                System.out.println("Command not found: clear");
//            }
//        }
//    }
}

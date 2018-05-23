package main.bank.seabank;

import java.io.File;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.text.NumberFormat;
//import java.util.InputMismatchException;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

/*
    TODO
    1) Ability to transfer $$ to acc in display()
    2) Logged in account transactions
    3) Branch 2: Create Account
    4) Change : to >> on next line
    5) Change stupid error messages
    6) Update readme
 */


public class Main {
    private static Scanner s = new Scanner(System.in);
    public static void main(String[] args) {
        Account acc = new CheckingAccount(200000.0, 2, new Password("Antonio703!"));
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
                System.out.println("\n\n\n\n\nPlease select a number from the list below");
                mainMenu();
            }
        } catch(NumberFormatException e) {
            System.out.println("\n\n\n\n\nPlease enter an integer");
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
            if (accExists(login_accNum)) {
                System.out.println("\nAccount #" + login_accNum + " found!");
                promptForPass(login_accNum);
            }
            else {
                accNonexistant(login_accNum);
            }
        } catch(NumberFormatException e) {
            System.out.println("\n\n\n\n\nPlease enter an integer\n");
            login();
        }

    }

    public static void accNonexistant(int nonExistentAcc) {
        System.out.println("\n\n\n\n\nAccount #" + nonExistentAcc + " does not seem to exist.\n"  +
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
            System.out.println("\n\n\n\n\nPlease enter (yY/nN)");
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
            System.out.println("\n\n\n\n\nIncorrect password!");
            promptForPass(accToAccess);
        }
    }

    public static void display(int accountToDisplay) {
        System.out.println("\nAccount #" + accountToDisplay);
        System.out.println("\n\tBalance: $" + NumberFormat.getNumberInstance(Locale.US).format(Account.getBalance(accountToDisplay)));
        System.out.println("\n\t(0) Withdraw Money");
        System.out.println("\t(1) Deposit Money");
        System.out.println("\t(2) Transfer money");
        System.out.println("\t(3) Log a Transaction");
        System.out.println("\t(4) Log out");
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
                transferMon(accountToDisplay);
            }
            else if(accountOption == 3) {

            }
            else if(accountOption == 4) {
                mainMenu();
            }
            else {
                System.out.println("\n\n\n\n\nPlease select a number from below");
                display(accountToDisplay);
            }
        } catch(NumberFormatException e) {
            System.out.println("\n\n\n\n\nPlease enter an integer\n");
            display(accountToDisplay);
        }

    }

    public static void withdrawMon(int accToWithdrawFrom){
        System.out.println("\nBalance: " + NumberFormat.getNumberInstance(Locale.US).format(Account.getBalance(accToWithdrawFrom)));
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
                System.out.println("\n\n\nSuccessfully withdrawn $" + NumberFormat.getNumberInstance(Locale.US).format(amountToWithdraw) + " from account #" + accToWithdrawFrom + "!");
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch(InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                display(accToWithdrawFrom);
            }
        } catch(NumberFormatException e) {
            System.out.println("\n\n\n\n\nPlease enter a number");
            withdrawMon(accToWithdrawFrom);
        }
    }

    public static void depositMon(int accToDepositTo) {
        System.out.println("\nBalance: " + NumberFormat.getNumberInstance(Locale.US).format(Account.getBalance(accToDepositTo)));
        System.out.print("Amount to deposit (or 0 to go back): $");
        try {
            double amountToDeposit = parseDouble(s.next());
            if(amountToDeposit == 0.0) display(accToDepositTo);
            else if(amountToDeposit < 0) {
                System.out.println("\n\n\n\n\nCannot deposit a negative amount!");
                depositMon(accToDepositTo);
            }
            else {
                Account.deposit(amountToDeposit, accToDepositTo);
                System.out.println("\n\n\nSuccessfully deposited $" + NumberFormat.getNumberInstance(Locale.US).format(amountToDeposit) + " to account #" + accToDepositTo + "!");
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch(InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                display(accToDepositTo);
            }
        } catch(NumberFormatException e) {
            System.out.println("\n\n\n\n\nPlease enter a number");
            depositMon(accToDepositTo);
        }
    }

    public static void transferMon(int from) {
        System.out.println("\nBalance: $" + NumberFormat.getNumberInstance(Locale.US).format(Account.getBalance(from)));
        System.out.print("Transfer money to (or 0 to go back): #");
        try {
            int transferTo = parseInt(s.next());
            if(transferTo == 0) {
                display(from);
            }
            else if(accExists(transferTo)) {
                transferAmount(from, transferTo);
            }
            else {
                System.out.println("That account doesn't seem to exist.");
            }
        } catch(NumberFormatException e) {
            System.out.println("\n\n\n\n\nPlease enter an integer");
            transferMon(from);
        }
    }
    public static void transferAmount(int fromAcc, int toAcc) {
        System.out.print("Amount to transfer (or 0 to go back): $");
        double amountToTransfer = parseDouble(s.next());
        if(amountToTransfer == 0.0) display(fromAcc);
        else if(amountToTransfer < 0) {
            System.out.println("\n\n\n\n\nCannot transfer a negative amount!");
            transferAmount(fromAcc, toAcc);
        }
        else {
            Account.withdraw(amountToTransfer, fromAcc);
            Account.deposit(amountToTransfer, toAcc);
            System.out.println("\n\n\n\n\n$" + NumberFormat.getNumberInstance(Locale.US).format(amountToTransfer)  + " successfully transferred to account #" + toAcc + "!");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch(InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            display(fromAcc);
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


    //For finding if acc exists or not
    public static boolean accExists(int accToCheck) {
        return new File(System.getProperty("user.dir") + "/data/accounts/" + accToCheck + ".txt").exists();
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

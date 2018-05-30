package main.bank.seabank;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.text.NumberFormat;
//import java.util.InputMismatchException;
//import java.text.NumberFormat;

/*
    TODO
    REMEMBER FOR NO DUPLICATE USERNAMES
    USERNAME MUST BE MORE THAN 4 CHARACTERS

    1)Logged in account transactions
        - If debit
            - If transaction > balance, subtract fee, return that program subtracted a fee
    2) Branch 2: Create Account
    3) Fix verify methods in Username and Password
    4) Change : to >> on next line
    5) Change stupid error messages
    6) Update readme
    7) Add password change to display()
 */


public class Main {
    private static Scanner s = new Scanner(System.in);
    private static String dir = System.getProperty("user.dir");

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
        System.out.print("\n\n\n\n\nUsername (or enter b to go back): ");
        try {
            String user = s.next();
            if(user.equals("b")) {
                mainMenu();
            }
            if (accExists(user)) {
                System.out.println("\nUser \'" + user + "\' found!");
                promptForPass(user);
            }
            else {
                accNonexistant(user);
            }
        } catch(NumberFormatException e) {
            System.out.println("\n\n\n\n\nPlease enter an integer\n");
            login();
        }

    }

    public static void accNonexistant(String nonExistentAcc) {
        System.out.println("\n\n\n\n\nAccount \'" + nonExistentAcc + "\' does not seem to exist.\n"  +
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

    public static void promptForPass(String accToAccess) {
        System.out.print("Account password (or enter b to go back): ");
        String login_pass = s.next();

        if (login_pass.equals("b")) {
            login();
        }
        if(login_pass.equals(UserAccount.decryptPass(accToAccess))) {
            display(accToAccess);
        }
        else {
//            cls();
            System.out.println("\n\n\n\n\nIncorrect password!");
            promptForPass(accToAccess);
        }
    }

    public static void display(String accToDisplay) {
        System.out.println("\n\n\n\n\nWelcome, " + accToDisplay + "!\n");
        System.out.println("\tAccounts owned: ");

        File accountFolder = new File(dir + "/data/UserAccounts/" + accToDisplay + "/BankAccounts/");
        String[] accountAccounts = accountFolder.list();
        for(String fileName : accountAccounts) {
            try {
                Scanner readAccType = new Scanner(new File(dir + "/data/UserAccounts/" + accToDisplay + "/BankAccounts/" + fileName));
                String accountType = readAccType.next().split(":")[2];
                readAccType.close();
                System.out.println("\n\tAccount #" + Integer.parseInt(fileName.replaceAll("[\\D]", "")) + " (" + accountType + ")");
                chooseAccount(accToDisplay);
            } catch (FileNotFoundException e) {
                System.err.println("Cannot open account file: " + fileName);
            } catch (Exception e) {
                System.err.println(e);
            }
        }
        System.out.println("\n");

    }

    public static void chooseAccount(String user) {
        System.out.println("\nPlease select an account number from above (or enter 0 to go back)");
        System.out.print(">> ");
        int accountToChoose = parseInt(s.next());
        if(bankAccExists(user, accountToChoose)) {
            displayBankAccount(user, accountToChoose);
        }
        else if(accountToChoose == 0) {
            display(user);
        }
        else {
            System.err.println("Account does not exist");
        }
    }

    public static void displayBankAccount(String accountToDisplay, int usersAccToDisplay) {
        System.out.println("\nAccount #" + usersAccToDisplay);
        System.out.println("\n\tBalance: $" + NumberFormat.getNumberInstance(Locale.US).format(Account.getBalance(accountToDisplay, usersAccToDisplay)));
        System.out.println("\n\t(0) Withdraw Money");
        System.out.println("\t(1) Deposit Money");
        System.out.println("\t(2) Transfer money");
        System.out.println("\t(3) Log a Transaction");
        System.out.println("\t(4) Log out");
        System.out.print("\n>> ");

        try {
            int accountOption = parseInt(s.next());
            if (accountOption == 0) {
                withdrawMon(accountToDisplay, usersAccToDisplay);
            }
            else if(accountOption == 1) {
                depositMon(accountToDisplay, usersAccToDisplay);
            }
            else if(accountOption == 2) {
                transferMon(accountToDisplay, usersAccToDisplay);
            }
//            else if(accountOption == 3) {
//
//            }
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

    public static void withdrawMon(String user, int accToWithdrawFrom){
        System.out.println("\nBalance: " + NumberFormat.getNumberInstance(Locale.US).format(Account.getBalance(user, accToWithdrawFrom)));
        System.out.print("Amount to withdraw (or enter 0 to go back): $");
        try {
            double amountToWithdraw = parseDouble(s.next());
            if(amountToWithdraw == 0.0) displayBankAccount(user, accToWithdrawFrom);
            else if(amountToWithdraw < 0) {
                System.out.println("\n\n\n\n\nCannot withdraw a negative amount!");
                withdrawMon(user, accToWithdrawFrom);
            }
            else {
                if(Account.getBalance(user, accToWithdrawFrom) >= amountToWithdraw) {
                    Account.withdraw(amountToWithdraw, user, accToWithdrawFrom);
                    System.out.println("\n\n\nSuccessfully withdrawn $" + NumberFormat.getNumberInstance(Locale.US).format(amountToWithdraw) + " from account #" + accToWithdrawFrom + "!");
                }
                else {
                    System.out.println("The amount of money you are requesting to withdraw exceeds your balance.");
                    withdrawMon(user, accToWithdrawFrom);
                }
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch(InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                displayBankAccount(user, accToWithdrawFrom);
            }
        } catch(NumberFormatException e) {
            System.out.println("\n\n\n\n\nPlease enter a number");
            withdrawMon(user, accToWithdrawFrom);
        }
    }

    public static void depositMon(String user, int accToDepositTo) {
        System.out.println("\nBalance: $" + NumberFormat.getNumberInstance(Locale.US).format(Account.getBalance(user, accToDepositTo)));
        System.out.print("Amount to deposit (enter 0 to go back): $");
        try {
            double amountToDeposit = parseDouble(s.next());
            if(amountToDeposit == 0.0) displayBankAccount(user, accToDepositTo);
            else if(amountToDeposit < 0) {
                System.out.println("\n\n\n\n\nCannot deposit a negative amount!");
                depositMon(user, accToDepositTo);
            }
            else if(Account.getBalance(user, accToDepositTo) >= 5000000) {
                System.out.println("\n\n\n\n\nYou have reached the maximum amount of money!");
                System.out.println("Please open a new bank account.");
            }
            else if(amountToDeposit >= 5000000) {
                System.out.println("\n\n\n\n\nBank accounts can have a maximum of $5,000,000");
                System.out.println("Please open a new bank account or deposit less.");
            }
            else {
                Account.deposit(amountToDeposit, user, accToDepositTo);
                System.out.println("\n\n\nSuccessfully deposited $" + NumberFormat.getNumberInstance(Locale.US).format(amountToDeposit) + " to account #" + accToDepositTo + "!");
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch(InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                displayBankAccount(user, accToDepositTo);
            }
        } catch(NumberFormatException e) {
            System.out.println("\n\n\n\n\nPlease enter a number");
            depositMon(user, accToDepositTo);
        }
    }

    public static void transferMon(String user, int from) {
        System.out.println("\n\n\nBalance: $" + NumberFormat.getNumberInstance(Locale.US).format(Account.getBalance(user, from)));
        System.out.println("\nUser to send money to (enter b to go back): ");
        System.out.print(">> ");
        String transferTo = s.next();
        if(transferTo.equals("b")) {
            displayBankAccount(user, from);
        }
        else if(accExists(transferTo)) {                        // FOR HERE: check if user exists, then check if that account number exists for that user

        }
        else {
            System.out.println("That account doesn't seem to exist.");
        }
    }
    public static void transferToUsersAcc(String user, int from, String toUser) {
        System.out.println("Account # for that user (enter 0 to go back): ");
        System.out.print(">> #");
        int toUsersAcc = parseInt(s.next());
        
        try {
        	if(toUsersAcc == 0) {
        		transferMon(user, from);
        	}
        	else if(bankAccExists(user, toUsersAcc)) {
        		transferAmount(user, from, toUser, toUsersAcc);
        	}
        } catch (NumberFormatException e) {
        	System.out.println("\n\n\n\n\nPlease enter an integer");
        	transferToUsersAcc(user, from, toUser);
        }
    }
    public static void transferAmount(String fromUser, int fromAcc, String toUser, int toAcc) {
        System.out.print("Amount to transfer (enter 0 to go back): $");
        double amountToTransfer = parseDouble(s.next());
        if(amountToTransfer == 0.0) displayBankAccount(fromUser, fromAcc);
        else if(amountToTransfer < 0) {
            System.out.println("\n\n\n\n\nCannot transfer a negative amount!");
            transferAmount(fromUser, fromAcc, toUser, toAcc);
        }
        else {
            if(Account.getBalance(fromUser, fromAcc) >= amountToTransfer) {
                Account.withdraw(amountToTransfer, fromUser, fromAcc);
                Account.deposit(amountToTransfer, toUser, toAcc);
                System.out.println("\n\n\n\n\n$" + NumberFormat.getNumberInstance(Locale.US).format(amountToTransfer) + " successfully transferred to account #" + toAcc + "!");
            }
            else {
                System.out.println("The amount of money you are requesting to send exceeds your limit.");
                transferAmount(fromUser, fromAcc, toUser, toAcc);
            }
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch(InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            displayBankAccount(fromUser, fromAcc);
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
    public static boolean accExists(String user) {
        return new File(dir + "/data/UserAccounts/" + user).exists();
    }
    public static boolean bankAccExists(String user, int usersAcc) {
        return new File(dir + "/data/UserAccounts/" + user + "/BankAccounts/" + usersAcc + ".txt").exists();
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

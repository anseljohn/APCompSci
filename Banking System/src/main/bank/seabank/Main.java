package main.bank.seabank;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;

/*
    TODO
    REMEMBER FOR NO DUPLICATE USERNAMES
    USERNAME MUST BE MORE THAN 4 CHARACTERS

    1) Change add accs on display to add new bank acc
    2) Error codes
    3) Fix verify methods in Username and Password
    4) Update readme to show directions
    5) Add password change to display() //OPTIONAL
 */


public class Main {
    private static String dir = System.getProperty("user.dir");
    private static Username newUsername;
    private static Password newPassword;

    private static void main(String[] args) {
    	
        System.out.println("Welcome to Sea Bank!\n");
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch(InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        mainMenu();
    }

    private static void mainMenu() {
        Scanner s = new Scanner(System.in);
        System.out.println("\nPlease select an item below:");
        System.out.println("(0) Login");
        System.out.println("(1) Create Account");
        System.out.println("(2) Exit");
        System.out.print("\n>> ");
        try {
            int mainMenu = parseInt(s.next());
            if (mainMenu == 0) {
                login();
            }
            else if(mainMenu == 1) {
                promptForNewUsername();
                promptForNewPassword();
                UserAccount newUser = new UserAccount(newUsername, newPassword);
                System.out.println("\n\n\n\n\n\n\nAccount successfully created!\n");
                mainMenu();
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
    private static void login() {
        Scanner s = new Scanner(System.in);
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

    private static void accNonexistant(String nonExistentAcc) {
        Scanner s = new Scanner(System.in);
        System.out.println("\n\n\n\n\nAccount \'" + nonExistentAcc + "\' does not seem to exist.\n"  +
                "Would you like to create an account?\n");
        System.out.print("(yY/nN/bB(back))>> ");
        String createAcc_failedLogin = s.next();
        if(createAcc_failedLogin.toLowerCase().equals("y")) {
            promptForNewUsername();
            promptForNewPassword();
            UserAccount newUser = new UserAccount(newUsername, newPassword);
            System.out.println("\n\n\n\n\nAccount successfully created!\n");
            mainMenu();
        }
        else if(createAcc_failedLogin.toLowerCase().equals("n")) {
            retryLogin();
        }
        else if(createAcc_failedLogin.toLowerCase().equals("b")) {
            mainMenu();
        }
        else {
            System.out.println("\nPlease enter (yY/nN)");
            accNonexistant(nonExistentAcc);
        }
    }

    private static void retryLogin() {
        Scanner s = new Scanner(System.in);
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

    private static void promptForPass(String accToAccess) {
        Scanner s = new Scanner(System.in);
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

    private static void display(String accToDisplay) {
        System.out.println("\n\n\n\n\nWelcome, " + accToDisplay + "!\n");
        System.out.println("\tAccounts owned: ");

        File accountFolder = new File(dir + "/data/UserAccounts/" + accToDisplay + "/BankAccounts/");
        String[] accountAccounts = accountFolder.list();
        try {
            for (String fileName : accountAccounts) {
                try {
                    Scanner readAccType = new Scanner(new File(dir + "/data/UserAccounts/" + accToDisplay + "/BankAccounts/" + fileName));
                    readAccType.close();
                    System.out.println("\n\tAccount #" + Integer.parseInt(fileName.replaceAll("[\\D]", "")));
                    chooseAccount(accToDisplay);
                } catch (FileNotFoundException e) {
                    System.err.println("Cannot open account file: " + fileName);
                } catch (Exception e) {
                    System.err.println(e);
                }
            }
        } catch(NullPointerException e) {
            System.out.println("Error code 99 : User has no bank accounts");
        }
    }

    private static void chooseAccount(String user) {
        Scanner s = new Scanner(System.in);
        try {
            System.out.println("\nPlease select an account number from above [ enter \'0\' to log out | enter \'999\' to add a new bank account ]");
            System.out.print(">> ");
            String accountToChooseStr = s.next();
            int accountToChoose = parseInt(accountToChooseStr);
            if (bankAccExists(user, accountToChoose)) {
                displayBankAccount(user, accountToChoose);
            } else if (accountToChoose == 0) {
                mainMenu();
            } else if (accountToChoose == 999) {
                Account newAcc = new Account(user);
            } else {
                System.err.println("Account does not exist");
            }
        } catch(NumberFormatException e) {
            System.out.println("\nPlease enter an integer");
            chooseAccount(user);
        }
    }

    private static void displayBankAccount(String accountToDisplay, int usersAccToDisplay) {
        Scanner s = new Scanner(System.in);
        System.out.println("\nAccount #" + usersAccToDisplay);
        System.out.println("\n\tBalance: $" + NumberFormat.getNumberInstance(Locale.US).format(Account.getBalance(accountToDisplay, usersAccToDisplay)));
        System.out.println("\n\t(0) Withdraw Money");
        System.out.println("\t(1) Deposit Money");
        System.out.println("\t(2) Transfer money");
        System.out.println("\t(3) Go back to accounts");
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
            else if(accountOption == 3) {
                display(accountToDisplay);
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

    private static void withdrawMon(String user, int accToWithdrawFrom){
        Scanner s = new Scanner(System.in);
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

    private static void depositMon(String user, int accToDepositTo) {
        Scanner s = new Scanner(System.in);
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

    private static void transferMon(String user, int from) {
        Scanner s = new Scanner(System.in);
        System.out.println("\n\n\nBalance: $" + NumberFormat.getNumberInstance(Locale.US).format(Account.getBalance(user, from)));
        System.out.println("\nUser to send money to (enter b to go back): ");
        System.out.print(">> ");
        String transferTo = s.next();
        if(transferTo.equals("b")) {
            displayBankAccount(user, from);
        }
        else if(accExists(transferTo)) {
            transferToUsersAcc(user, from, transferTo);
        }
        else {
            System.out.println("That account doesn't seem to exist.");
            transferMon(user, from);
        }
    }
    private static void transferToUsersAcc(String user, int from, String toUser) {
        Scanner s = new Scanner(System.in);
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
    private static void transferAmount(String fromUser, int fromAcc, String toUser, int toAcc) {
        Scanner s = new Scanner(System.in);
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
                System.out.println("\n\n\n\n\n$" + NumberFormat.getNumberInstance(Locale.US).format(amountToTransfer) + " successfully transferred to " + toUser + "\'s account #" + toAcc + "!");
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
    private static void promptForNewUsername() {
        Scanner s = new Scanner(System.in);
        System.out.print("\nUsername: ");
        String newUserUsername = s.next();
        if(accExists(newUserUsername)) {
            System.out.println("\n\n\nUser \'" + newUserUsername + "\' already exists!\n");
            promptForNewUsername();
        }
        else {
            newUsername = new Username(newUserUsername);
        }
    }

    private static void promptForNewPassword() {
        Scanner s = new Scanner(System.in);
        System.out.print("Password: ");
        String newPass = s.next();
        System.out.print("Re-enter password: ");
        String confirmPass = s.next();

        if(!newPass.equals(confirmPass)) {
            System.out.println("\n\nPassword confirmation incorrect!");
            promptForNewPassword();
        }
        else {
            newPassword = new Password(newPass);
        }
    }


    //For finding if acc exists or not
    private static boolean accExists(String user) {
        return new File(dir + "/data/UserAccounts/" + user).exists();
    }
    private static boolean bankAccExists(String user, int usersAcc) {
        return new File(dir + "/data/UserAccounts/" + user + "/BankAccounts/" + usersAcc + ".txt").exists();
    }
}

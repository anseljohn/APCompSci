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
    (Optional) Add ability to change username and password
 */


public class Main {
    private static String dir = System.getProperty("user.dir");
    private static Username newUsername;
    private static Password newPassword;

    public static void main(String[] args) {
    	
        System.out.println("Welcome to Sea Bank!\n");
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch(InterruptedException e) {
            System.err.println("Error code 51339");
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
                System.out.println("\n\nUsername must:");
                System.out.println("\t- have 5-13 characters" +
                        "\n\t- contain spaces.");
                promptForNewUsername();
                System.out.println("\n\nPassword must:");
                System.out.println("\t- have 5-13 characters." +
                        "\n\t- have at least 1 uppercase and 1 lowercase letter." +
                        "\n\t- have at least 1 special character (~`!@#$%^&*()+=_-{}[]\\|:;”’?/<>,.)." +
                        "\n\t- have at least 1 digit (0123456789).\n");
                promptForNewPassword();
                UserAccount newUser = new UserAccount(newUsername, newPassword);
                System.out.println("\n\n\n\n\n\n\nAccount successfully created!\n");
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch(InterruptedException e) {
                    System.err.println("Error code 51339");
                    Thread.currentThread().interrupt();
                }
                mainMenu();
            }
            else if(mainMenu == 2) {
                System.exit(0);
            }
            else {
                System.out.println("\n\n\n\n\nPlease select a number from the list below");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch(InterruptedException ie) {
                    System.err.println("Error code 51339");
                    Thread.currentThread().interrupt();
                }
                mainMenu();
            }
        } catch(NumberFormatException e) {
            System.out.println("\n\n\n\n\nPlease enter an integer");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch(InterruptedException ie) {
                System.err.println("Error code 51339");
                Thread.currentThread().interrupt();
            }
            mainMenu();
        }
    }


    /*
        START OF TREE FOR login OPTION
     */
    private static void login() {
        Scanner s = new Scanner(System.in);
        System.out.println("\n\n\n\n\nUsername (or enter b to go back)");
        System.out.print(">> ");
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
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch(InterruptedException ie) {
                System.err.println("Error code 51339");
                Thread.currentThread().interrupt();
            }
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
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch(InterruptedException ie) {
                System.err.println("Error code 51339");
                Thread.currentThread().interrupt();
            }
            accNonexistant(nonExistentAcc); //Recursion
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
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch(InterruptedException ie) {
                System.err.println("Error code 51339");
                Thread.currentThread().interrupt();
            }
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
            System.out.println("\n\n\n\n\nIncorrect password!");
            promptForPass(accToAccess);
        }
    }

    private static void display(String accToDisplay) {
        System.out.println("\n\nWelcome, " + accToDisplay + "!\n");
        System.out.println("\tAccounts owned:");

        File accountFolder = new File(dir + "/data/UserAccounts/" + accToDisplay + "/BankAccounts/");
        String[] accountAccounts = accountFolder.list();
        int[] files = new int[accountAccounts.length];
        for(int i = 0; i < accountAccounts.length; i++) {
            files[i] = Integer.parseInt(accountAccounts[i].replaceAll("[\\D]", ""));
        }
        files = sortFiles(files, accountAccounts.length);
        try {
            for (int i = 0; i <  accountAccounts.length; i++) {
                for(int j = 0; j < accountAccounts.length; j++) {
                    if (Integer.parseInt(accountAccounts[j].replaceAll("[\\D]", "")) == files[i]) {
                        System.out.println("\t\t- Account #" + Integer.parseInt(accountAccounts[j].replaceAll("[\\D]", "")));
                        break;
                    }
                }
            }
            chooseAccount(accToDisplay);
        } catch(NullPointerException e) {
            System.out.println("Error code 99 : User has no bank accounts");
            System.out.println("Returning to main menu...");
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch(InterruptedException ie) {
                System.err.println("Error code 51339");
                Thread.currentThread().interrupt();
            }
            mainMenu();
        }
    }

    private static int[] sortFiles(int[] arr, int n)
    {
        int i, key, j;
        for (i = 1; i < n; i++)
        {
            key = arr[i];
            j = i-1;
            while (j >= 0 && arr[j] > key)
            {
                arr[j+1] = arr[j];
                j = j-1;
            }
            arr[j+1] = key;
        }
        return arr;
    }

    private static void chooseAccount(String user) {
        Scanner s = new Scanner(System.in);
        try {
            System.out.println("\nPlease enter an account number from above [ enter \'0\' to log out | enter \'999\' to add a new bank account ]");
            System.out.print(">> #");
            String accountToChooseStr = s.next();
            int accountToChoose = parseInt(accountToChooseStr);
            if (bankAccExists(user, accountToChoose)) {
                displayBankAccount(user, accountToChoose);
            } else if (accountToChoose == 0) {
                mainMenu();
            } else if (accountToChoose == 999) {
                Account newAcc = new Account(user);
                display(user);
            } else {
                System.out.println("\n\n\n\n\nAccount #" + accountToChoose + " does not exist");
                display(user);
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
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch(InterruptedException ie) {
                    System.err.println("Error code 51339");
                    Thread.currentThread().interrupt();
                }
                displayBankAccount(accountToDisplay, usersAccToDisplay);
            }
        } catch(NumberFormatException e) {
            System.out.println("\n\n\n\n\nPlease enter an integer\n");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch(InterruptedException ie) {
                System.err.println("Error code 51339");
                Thread.currentThread().interrupt();
            }
            display(accountToDisplay);
        }

    }

    private static void withdrawMon(String user, int accToWithdrawFrom){
        Scanner s = new Scanner(System.in);
        System.out.println("\nBalance: " + NumberFormat.getNumberInstance(Locale.US).format(Account.getBalance(user, accToWithdrawFrom)));
        System.out.println("\nAmount to withdraw (or enter 0 to go back):");
        System.out.print(">> $");
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
                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch(InterruptedException ie) {
                        System.err.println("Error code 51339");
                        Thread.currentThread().interrupt();
                    }
                    withdrawMon(user, accToWithdrawFrom);
                }
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch(InterruptedException e) {
                    System.err.println("Error code 51339");
                    Thread.currentThread().interrupt();
                }
                displayBankAccount(user, accToWithdrawFrom);
            }
        } catch(NumberFormatException e) {
            System.out.println("\n\n\n\n\nPlease enter a number");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch(InterruptedException ie) {
                System.err.println("Error code 51339");
                Thread.currentThread().interrupt();
            }
            withdrawMon(user, accToWithdrawFrom);
        }
    }

    private static void depositMon(String user, int accToDepositTo) {
        Scanner s = new Scanner(System.in);
        System.out.println("\nBalance: $" + NumberFormat.getNumberInstance(Locale.US).format(Account.getBalance(user, accToDepositTo)));
        System.out.println("Amount to deposit (enter 0 to go back):");
        System.out.print(">> $");
        try {
            double amountToDeposit = parseDouble(s.next());
            if(amountToDeposit == 0.0) displayBankAccount(user, accToDepositTo);
            else if(amountToDeposit < 0) {
                System.out.println("\n\n\n\n\nCannot deposit a negative amount!");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch(InterruptedException ie) {
                    System.err.println("Error code 51339");
                    Thread.currentThread().interrupt();
                }
                depositMon(user, accToDepositTo);
            }
            else if(Account.getBalance(user, accToDepositTo) + amountToDeposit > 5000000) {
                System.out.println("\n\n\n\n\nDepositing $" + (5000000 - Account.getBalance(user, accToDepositTo)) + "...");
                System.out.println("Please open a new bank account to deposit the rest.");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch(InterruptedException ie) {
                    System.err.println("Error code 51339");
                    Thread.currentThread().interrupt();
                }
                displayBankAccount(user, accToDepositTo);
            }
            else if(amountToDeposit > 5000000) {
                System.out.println("\n\n\n\n\nBank accounts can have a maximum of $5,000,000");
                System.out.println("Please open a new bank account or deposit less.");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch(InterruptedException ie) {
                    System.err.println("Error code 51339");
                    Thread.currentThread().interrupt();
                }
                depositMon(user, accToDepositTo);
            }
            else {
                Account.deposit(amountToDeposit, user, accToDepositTo);
                System.out.println("\n\n\nSuccessfully deposited $" + NumberFormat.getNumberInstance(Locale.US).format(amountToDeposit) + " to account #" + accToDepositTo + "!");
                try {
                    TimeUnit.SECONDS.sleep(2);
                } catch(InterruptedException e) {
                    System.err.println("Error code 51339");
                    Thread.currentThread().interrupt();
                }
                displayBankAccount(user, accToDepositTo);
            }
        } catch(NumberFormatException e) {
            System.out.println("\n\n\n\n\nPlease enter a number");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch(InterruptedException ie) {
                System.err.println("Error code 51339");
                Thread.currentThread().interrupt();
            }
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
            System.out.println("\n\n\n\n\nThat account doesn't seem to exist.");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch(InterruptedException e) {
                System.err.println("Error code 51339");
                Thread.currentThread().interrupt();
            }
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
        	else if(bankAccExists(toUser, toUsersAcc)) {
        		transferAmount(user, from, toUser, toUsersAcc);
        	}
        	else {
        	    System.out.println(toUser + " does not have account #" + toUsersAcc);
        	    transferToUsersAcc(user, from, toUser);
            }
        } catch (NumberFormatException e) {
        	System.out.println("\n\n\n\n\nPlease enter an integer");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch(InterruptedException ie) {
                System.err.println("Error code 51339");
                Thread.currentThread().interrupt();
            }
        	transferToUsersAcc(user, from, toUser);
        }
    }
    private static void transferAmount(String fromUser, int fromAcc, String toUser, int toAcc) {
        Scanner s = new Scanner(System.in);
        System.out.println("Amount to transfer (enter 0 to go back):");
        System.out.print(">> $");
        double amountToTransfer = parseDouble(s.next());
        if(amountToTransfer == 0.0) displayBankAccount(fromUser, fromAcc);
        else if(amountToTransfer < 0) {
            System.out.println("\n\n\n\n\nCannot transfer a negative amount!");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch(InterruptedException ie) {
                System.err.println("Error code 51339");
                Thread.currentThread().interrupt();
            }
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
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch(InterruptedException ie) {
                    System.err.println("Error code 51339");
                    Thread.currentThread().interrupt();
                }
                transferAmount(fromUser, fromAcc, toUser, toAcc);
            }
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch(InterruptedException e) {
                System.err.println("Error code 51339");
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
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch(InterruptedException ie) {
                System.err.println("Error code 51339");
                Thread.currentThread().interrupt();
            }
            promptForNewUsername();
        }
        else {
            if (Username.verifyUsername(newUserUsername)) newUsername = new Username(newUserUsername);
            else promptForNewUsername();
        }
    }

    private static void promptForNewPassword() {
        Scanner s = new Scanner(System.in);
        System.out.print("Password: ");
        String newPass = s.next();
        if(Password.verifyPassword(newPass)) {
            System.out.print("Re-enter password: ");
            String confirmPass = s.next();

            if(newPass.equals(confirmPass)) {
                newPassword = new Password(newPass);
            }
            else {
                System.out.println("\n\nPassword confirmation incorrect!");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch(InterruptedException ie) {
                    System.err.println("Error code 51339");
                    Thread.currentThread().interrupt();
                }
                promptForNewPassword();
            }
        }
        else promptForNewPassword();
    }


    //For finding if acc exists or not
    private static boolean accExists(String user) {
        return new File(dir + "/data/UserAccounts/" + user).exists();
    }
    private static boolean bankAccExists(String user, int usersAcc) {
        return new File(dir + "/data/UserAccounts/" + user + "/BankAccounts/" + usersAcc + ".txt").exists();
    }

    private static void printAllAccounts() {

    }
}

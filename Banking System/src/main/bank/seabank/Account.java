package main.bank.seabank;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;
//import java.math.BigDecimal;
//import java.nio.file.Files;
//import java.nio.file.Paths;

import static java.lang.Double.parseDouble;
import static java.lang.Integer.parseInt;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import javax.crypto.Cipher;
//import javax.crypto.KeyGenerator;
//import javax.crypto.SecretKey;
//import javax.crypto.spec.SecretKeySpec;

public abstract class Account {
    private double balance;
    private int accNumber;
    private String user;
    private String accTypeStr;

    private static String dir = System.getProperty("user.dir");

    public Account(String username, AccountType paymentType) {
        balance = 0.0;
        user = username;
        accTypeStr = paymentType.toString();
        try {
            Scanner s = new Scanner(new File(dir + "/data/UserAccounts/" + username + "/AccountTrack.txt"));
            accNumber = parseInt(s.nextLine());
        } catch(FileNotFoundException e) {
            System.err.println("File could not be opened");
        }
        accountTrack(username);
        writeToFile();
    }

    public static void deposit(double amount, String u, int acc_depositTo) {
        try {
            double prevBalance = getBalance(u, acc_depositTo);
            String type = getAccountType(u, acc_depositTo);
            PrintWriter rewrite = new PrintWriter(dir + "/data/UserAccounts/" + u + "/BankAccounts/" + acc_depositTo + ".txt", "UTF-8");
            if(amount > 0) {
                rewrite.print(acc_depositTo + ":" + (prevBalance + amount) + ":" + type);
                rewrite.close();
            }
        } catch(FileNotFoundException e) {
            System.err.println("Unable to locate file");
        } catch(UnsupportedEncodingException e) {
            System.err.println("Encoding not supported");
        }
    }

    public static void withdraw(double amount, String u, int acc_withdrawFrom) {
        try {
            double prevBalance = getBalance(u, acc_withdrawFrom);
            String type = getAccountType(u, acc_withdrawFrom);
            PrintWriter rewrite = new PrintWriter(dir + "/data/UserAccounts/" + u + "/BankAccounts/" + acc_withdrawFrom + ".txt", "UTF-8");
            if(amount > 0 && amount <= prevBalance) {
                rewrite.println(acc_withdrawFrom + ":" + (prevBalance - amount) + ":" + type);
                rewrite.close();
            }
        } catch(FileNotFoundException e) {
            System.err.println("Unable to locate file");
        } catch(UnsupportedEncodingException e) {
            System.err.println("Encoding not supported");
        }
    }

    public abstract void transaction(double amount, Payment typeOfPayment, String u);

    public void writeToFile() {
        try {
            PrintWriter writer = new PrintWriter(dir + "/data/UserAccounts/" + user + "/BankAccounts/" + accNumber + ".txt", "UTF-8");
            writer.print(accNumber + ":" + balance + ":" + accTypeStr);
            writer.close();
        } catch(FileNotFoundException e) {
            System.err.println("U bad where's ur file");
        } catch(UnsupportedEncodingException e) {
            System.err.println("haha u bad encoding bad ha");
        }
    }


    public static double getBalance(String u, int accBalance_toGet) {
        double balance = 0.0;
        try {
            Scanner accFileReader = new Scanner(new File(dir + "/data/UserAccounts/" + u + "/BankAccounts/" + accBalance_toGet + ".txt"));
            String fullFile = accFileReader.nextLine();
            String[] splitFile = fullFile.split(":");
            balance = parseDouble(splitFile[1]);
        } catch(Exception e) {
            System.err.println(e);
        }
        return balance;
    }

    public static String getAccountType(String u, int accsTypeToGet) {
        String type = "";
        try {
            Scanner readFile = new Scanner(new File(dir + "/data/UserAccounts/" + u + "/BankAccounts/" + accsTypeToGet + ".txt"));
            type = readFile.nextLine().split(":")[2];
        } catch(FileNotFoundException e) {
            System.err.println("Cannot open file: " + accsTypeToGet  + ".txt");
        }
        return type;
    }

    public static void accountTrack(String u) {
        try {
            Scanner getAccts = new Scanner(new File(dir + "/data/UserAccounts/" + u + "/AccountTrack.txt"));
            int prevAccts = parseInt(getAccts.nextLine());

            PrintWriter writer = new PrintWriter(dir + "/data/UserAccounts/" + u + "/AccountTrack.txt", "UTF-8");
            writer.print(prevAccts + 1);
            writer.close();
        } catch(FileNotFoundException e) {
            System.err.println("File could not be opened");
        } catch(UnsupportedEncodingException e) {
            System.err.println("Encoding not supported");
        }
    }

    //Enums
    enum Payment {
        DEBIT, CREDIT, CHECK;
    }

    public enum AccountType {
        CHECKING, SAVINGS; //maybe add KIDS
    }
}

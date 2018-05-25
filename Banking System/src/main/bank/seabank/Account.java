package main.bank.seabank;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

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

    private static String dir = System.getProperty("user.dir");

    public Account(String u) {
        balance = 0.0;
        try {
            Scanner s = new Scanner(new File(dir + "/data/AccountTrack.txt"));
            accNumber = parseInt(s.nextLine());
        } catch(FileNotFoundException e) {
            System.err.println("File could not be opened");
        }
        accountTrack(u);
        writeToFile();
    }

    enum Payment {
        DEBIT, CREDIT, CHECK;
    }

    public static void deposit(double amount, String u, int acc_depositTo) {
        try {
            double prevBalance = getBalance(u, acc_depositTo);
            PrintWriter rewrite = new PrintWriter(dir + "/data/accounts/" + acc_depositTo + ".txt", "UTF-8");
            if(amount > 0) {
                rewrite.print(acc_depositTo + ":" + (prevBalance + amount));
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
            PrintWriter rewrite = new PrintWriter(dir + "/data/accounts/" + acc_withdrawFrom + ".txt", "UTF-8");
            if(amount > 0 && amount <= prevBalance) {
                rewrite.println(acc_withdrawFrom + ":" + (prevBalance - amount));
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
            PrintWriter writer = new PrintWriter(dir + "/data/" + accNumber + ".txt", "UTF-8");
            writer.print(accNumber + ":");
            writer.printf(" %.0f", balance);
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
            Scanner accFileReader = new Scanner(new File(dir + "/data/accounts/" + accBalance_toGet + ".txt"));
            String fullFile = accFileReader.nextLine();
            String[] splitFile = fullFile.split(":");
            balance = parseDouble(splitFile[1]);
        } catch(Exception e) {
            System.err.println(e);
        }
        return balance;
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
}

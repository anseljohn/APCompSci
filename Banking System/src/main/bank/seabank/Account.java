package main.bank.seabank;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
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
    private Password accPass;

    private static String dir = System.getProperty("user.dir");

    public Account(double startingBalance, Password p) {
        balance = startingBalance;
        try {
            Scanner s = new Scanner(new File(dir + "/data/AccountTrack.txt"));
            accNumber = parseInt(s.nextLine());
        } catch(FileNotFoundException e) {
            System.err.println("File could not be opened");
        }
        accPass = p;
        accountTrack();
        writeToFile();
    }

    enum Payment {
        DEBIT, CREDIT, CHECK;
    }

    public static void deposit(double amount, int acc_depositTo) {
        try {
            double prevBalance = getBalance(acc_depositTo);
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

    public static void withdraw(double amount, int acc_withdrawFrom) {
        try {
            double prevBalance = getBalance(acc_withdrawFrom);
            PrintWriter rewrite = new PrintWriter(dir + "/data/accounts/" + acc_withdrawFrom + ".txt", "UTF-8");
            if(amount > 0 && amount <= prevBalance) {
                rewrite.println(acc_withdrawFrom + ":" + (prevBalance - amount));
                rewrite.close();
            }
            if(amount > prevBalance) {
                rewrite.println(acc_withdrawFrom + ":" + (prevBalance - (amount + amount * .5)));
                rewrite.close();
            }
        } catch(FileNotFoundException e) {
            System.err.println("Unable to locate file");
        } catch(UnsupportedEncodingException e) {
            System.err.println("Encoding not supported");
        }
    }

    public abstract void transaction(double amount, Payment typeOfPayment);

    public void writeToFile() {
        try {
            PrintWriter writer = new PrintWriter(dir + "/data/accounts/" + accNumber + ".txt", "UTF-8");
            writer.print(accNumber + ":" + balance);
            writer.close();

            PrintWriter passWriter = new PrintWriter(dir + "/data/pass/" + accNumber + "pass.txt", "UTF-8");
            passWriter.print(encryptPass());
            passWriter.close();

        } catch(FileNotFoundException e) {
            System.err.println("U bad where's ur file");
        } catch(UnsupportedEncodingException e) {
            System.err.println("haha u bad encoding bad ha");
        }
    }

    public String encryptPass() {
        String message = accPass.getPass();
        String encryptedMessage = "";
        int key = accPass.getPass().length();
        char ch;

        for(int i = 0; i < message.length(); ++i){
            ch = message.charAt(i);

            if(ch >= 'a' && ch <= 'z'){
                ch = (char)(ch + key);

                if(ch > 'z'){
                    ch = (char)(ch - 'z' + 'a' - 1);
                }

                encryptedMessage += ch;
            }
            else if(ch >= 'A' && ch <= 'Z'){
                ch = (char)(ch + key);

                if(ch > 'Z'){
                    ch = (char)(ch - 'Z' + 'A' - 1);
                }

                encryptedMessage += ch;
            }
            else {
                encryptedMessage += ch;
            }
        }
        return encryptedMessage;
    }

    public static String decryptPass(int decrypt_AccNum) {
        String message = "";
        String decryptedMessage = "";
        try {
            Scanner encryptedPass_File = new Scanner(new File(dir + "/data/pass/" + decrypt_AccNum + "pass.txt"));
            message = encryptedPass_File.nextLine();
        } catch(Exception e) {
            System.err.println("U R SOOOOO BAD, ERROR HAS OCCURRED");
        }
        int key = message.length();
        char ch;


        for(int i = 0; i < message.length(); ++i){
            ch = message.charAt(i);
            if(ch >= 'a' && ch <= 'z'){
                ch = (char)(ch - key);
                if(ch < 'a'){
                    ch = (char)(ch + 'z' - 'a' + 1);
                }
                decryptedMessage += ch;
            }
            else if(ch >= 'A' && ch <= 'Z'){
                ch = (char)(ch - key);
                if(ch < 'A'){
                    ch = (char)(ch + 'Z' - 'A' + 1);
                }
                decryptedMessage += ch;
            }
            else {
                decryptedMessage += ch;
            }
        }
        return decryptedMessage;
    }

    public static double getBalance(int accBalance_toGet) {
        double balance = 0.0;
        try {
            Scanner accFileReader = new Scanner(new File(dir + "/data/accounts/" + accBalance_toGet + ".txt"));
            String fullFile = accFileReader.nextLine();
            String[] splittedFile = fullFile.split(":");
            balance = parseDouble(splittedFile[1]);
        } catch(Exception e) {
            System.err.println(e);
        }
        return balance;
    }

    public static void accountTrack() {
        try {
            Scanner getAccts = new Scanner(new File(dir + "/data/AccountTrack.txt"));
            int prevAccts = parseInt(getAccts.nextLine());

            PrintWriter writer = new PrintWriter(dir + "/data/AccountTrack.txt", "UTF-8");
            writer.print(prevAccts + 1);
            writer.close();
        } catch(FileNotFoundException e) {
            System.err.println("File could not be opened");
        } catch(UnsupportedEncodingException e) {
            System.err.println("Encoding not supported");
        }
    }
}

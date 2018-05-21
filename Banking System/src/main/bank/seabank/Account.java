package main.bank.seabank;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;
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

    public Account(double startingBalance, int accountNumber, Password p) {
        balance = startingBalance;
        accNumber = accountNumber;
        accPass = p;
        writeToFile();
    }

    enum Payment {
        DEBIT, CREDIT, CHECK;
    }

    public void deposit(double amount) {
        balance += amount;
        writeToFile();
    }

    public void withdraw(double amount) {
        balance -= amount;
        writeToFile();
    }

    public abstract void transaction(double amount, Payment typeOfPayment);

    public void writeToFile() {
        try {
            PrintWriter writer = new PrintWriter(System.getProperty("user.dir") + "/data/accounts/" + accNumber + ".txt", "UTF-8");
            writer.println(accNumber + ":" + balance);
            writer.close();

            PrintWriter passWriter = new PrintWriter(System.getProperty("user.dir") + "/data/pass/" + accNumber + "pass.txt", "UTF-8");
            passWriter.println(encryptPass());
            passWriter.close();

        } catch(FileNotFoundException e) {
            System.out.println("U bad where's ur file");
        } catch(UnsupportedEncodingException e) {
            System.out.println("haha u bad encoding bad ha");
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
            Scanner encryptedPass_File = new Scanner(new File(System.getProperty("user.dir") + "/data/pass/" + decrypt_AccNum + "pass.txt"));
            message = encryptedPass_File.nextLine();
        } catch(Exception e) {
            System.out.println("U R SOOOOO BAD, ERROR HAS OCCURRED");
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
}

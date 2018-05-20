package main.bank.seabank;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

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
            PrintWriter accInfo = new PrintWriter(System.getProperty("user.dir") + "/UserAccs/" + accNumber + ".txt", "UTF-8");
            accInfo.println(accNumber + ":" + balance);
            accInfo.close();

            PrintWriter passWriter = new PrintWriter(System.getProperty("user.dir") + "/UserPass/" + accNumber + "pass.txt", "UTF-8");

        } catch(FileNotFoundException e) {
            System.out.println("U bad where's ur file");
        } catch(UnsupportedEncodingException e) {
            System.out.println("haha u bad encoding bad ha");
        }
    }

//    public String encryptPass() {
//        try {
//            KeyGenerator keyGen = KeyGenerator.getInstance("DES");
//            SecretKey key = keyGen.generateKey();
//
//            Cipher cipher = Cipher.getInstance("DES");
//            byte[] decPass = accPass.getBytes("UTF-8");
//        }
//    }
}

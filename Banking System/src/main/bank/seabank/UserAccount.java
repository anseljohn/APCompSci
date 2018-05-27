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

public class UserAccount {
    private Username user;
    private Password userPass;

    private static String dir = System.getProperty("user.dir");

    public UserAccount(Username u, Password p) {
        user = u;
        userPass = p;
        writeToFile();
        try {
            PrintWriter writeAccTrack = new PrintWriter(new File(dir + "/data/UserAccounts/" + u.getUser() + "/AccountTrack.txt"));
            writeAccTrack.print(1);
            writeAccTrack.close();
        } catch(FileNotFoundException e) {
            System.err.println("Could not open file");
        }
    }

    public void writeToFile() {
        File f = new File(dir + "/data/UserAccounts/" + user.getUser());
        File holdAccs = new File(dir + "/data/UserAccounts/" + user.getUser() + "/BankAccounts/");
        f.mkdirs();
        holdAccs.mkdirs();

        try {
            PrintWriter writePass = new PrintWriter(new File(dir + "/data/UserAccounts/" + user.getUser() + "/pass.txt"));
            writePass.print(encryptPass());
            writePass.close();

        } catch(FileNotFoundException e) {
            System.err.println("Could not open file pass.txt");
        }
    }

    public String encryptPass() {
        String message = userPass.getPass();
        String encryptedMessage = "";
        int key = userPass.getPass().length();
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

    public static String decryptPass(String decrypt_Acc) {
        String message = "";
        String decryptedMessage = "";
        try {
            Scanner encryptedPass_File = new Scanner(new File(dir + "/data/UserAccounts/" + decrypt_Acc + "/pass.txt"));
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

    //getters
    public String getUsername() {
        return user.getUser();
    }
}

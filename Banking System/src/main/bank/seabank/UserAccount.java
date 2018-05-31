package main.bank.seabank;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

class UserAccount {
    private Username user;
    private Password userPass;

    private static String dir = System.getProperty("user.dir");

    private UserAccount(Username u, Password p) {
        user = u;
        userPass = p;
        writeToFile();
        try {
            PrintWriter writeAccTrack = new PrintWriter(new File(dir + "/data/UserAccounts/" + u.getUser() + "/AccountTrack.txt"));
            writeAccTrack.print(1);
            writeAccTrack.close();
        } catch(FileNotFoundException e) {
            System.err.println("Error code 7113 : Could not open file");
        }
        Account usersAccount = new Account(u.getUser());
    }

    private void writeToFile() {
        File f = new File(dir + "/data/UserAccounts/" + user.getUser());
        File holdAccs = new File(dir + "/data/UserAccounts/" + user.getUser() + "/BankAccounts/");
        f.mkdirs();
        holdAccs.mkdirs();

        try {
            PrintWriter writePass = new PrintWriter(new File(dir + "/data/UserAccounts/" + user.getUser() + "/pass.txt"));
            writePass.print(encryptPass());
            writePass.close();

        } catch(FileNotFoundException e) {
            System.err.println("Error code 7113 : Could not open file pass.txt");
        }
    }

    private String encryptPass() {
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

    private static String decryptPass(String decrypt_Acc) {
        String message = "";
        String decryptedMessage = "";
        try {
            Scanner encryptedPass_File = new Scanner(new File(dir + "/data/UserAccounts/" + decrypt_Acc + "/pass.txt"));
            message = encryptedPass_File.nextLine();
        } catch(Exception e) {
            System.err.println(e);
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

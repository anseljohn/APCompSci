package main.bank.seabank;

import java.util.ArrayList;
//import java.util.Scanner;

public class Password {
    private String pass;
    private ArrayList<Character> specialChars = new ArrayList<Character>();
    private static String specialCharsStr = "~`!@#$%^&*()+=_-{}[]\\|:;”’?/<>,.";

    public Password() {
        pass = "";
    }
    public Password(String password) {
        pass = password;
        for(int i = 0; i < specialCharsStr.length(); i++) specialChars.add(specialCharsStr.charAt(i));
        //verifyPassword();
    }

    public void verifyPassword() {
        boolean hasSpecialChar = false;
        boolean hasUpperCase = false;
        boolean hasLowerCase = false;
        boolean hasDigit = false;

        String newPass = pass;

        for(int i = 0; i < newPass.length(); i++) {
            if(Character.isLetter(newPass.charAt(i)) && (newPass.charAt(i) + "").equals((newPass.charAt(i) + "").toUpperCase())) hasUpperCase = true;
            if(Character.isLetter(newPass.charAt(i)) && (newPass.charAt(i) + "").equals((newPass.charAt(i) + "").toLowerCase())) hasLowerCase = true;
            if((newPass.charAt(i) + "").equals(i + "")) hasDigit = true;
            for(Character s : specialChars) {
                if(newPass.charAt(i) == s) {
                    hasSpecialChar = true;
                }
            }
        }

        if(newPass.length() < 5 || newPass.length() > 13) {
            System.out.println("Password must be between 5-13 characters.");
        }
        if(!hasSpecialChar) {
            System.out.println("Password must contain at least one special character. (" + specialCharsStr + ")");
        }
        if(!hasUpperCase) {
            System.out.println("Password must have at least 1 uppercase letter.");
        }
        if(!hasLowerCase) {
            System.out.println("Password must have at least 1 lowercase letter.");
        }
        if(!hasDigit) {
            System.out.println("Password must have at least one digit.");
        }
        else {
            System.out.println( "Password set");
            pass = newPass;
        }
    }

    public static String getSpecialChars() {
        return specialCharsStr;
    }
    public String getPass() { return pass; }
}

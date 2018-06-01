package main.bank.seabank;

import java.util.ArrayList;

public class Password {
    private String pass;
    private static ArrayList<String> specialChars = new ArrayList<String>(); //List interface
    private static String specialCharsStr = "~`!@#$%^&*()+=_-{}[]\\|:;”’?/<>,.";

    Password(String password) {
        pass = password;
    }

    static boolean verifyPassword(String newPass) {
        boolean hasSpecialChar = false;
        boolean hasUpperCase = false;
        boolean hasLowerCase = false;
        boolean hasDigit = false;

        for(int i = 0; i < specialCharsStr.length(); i++) specialChars.add(specialCharsStr.charAt(i) + "");

        for(String s : specialChars) {
            if(newPass.contains(s)) {
                hasSpecialChar = true;
            }
        }
        if(!hasSpecialChar) {
            System.out.println("\n\nPassword must contain at least one special character. (" + specialCharsStr + ")\n");
            return false;
        }
        
        for(int i = 0; i < newPass.length(); i++) { //Looping through strings
            if(Character.isLetter(newPass.charAt(i)) && (newPass.charAt(i) + "").equals((newPass.charAt(i) + "").toUpperCase())) hasUpperCase = true;
            if(Character.isLetter(newPass.charAt(i)) && (newPass.charAt(i) + "").equals((newPass.charAt(i) + "").toLowerCase())) hasLowerCase = true;
            if(newPass.contains(i + "")) hasDigit = true;

        }

        if(newPass.length() < 5 || newPass.length() > 13) {
            System.out.println("\n\nPassword must be between 5-13 characters.");
            return false;
        }
        if(!hasUpperCase) {
            System.out.println("\n\nPassword must have at least 1 uppercase letter.\n");
            return false;
        }
        if(!hasLowerCase) {
            System.out.println("\n\nPassword must have at least 1 lowercase letter.\n");
            return false;
        }
        if(!hasDigit) {
            System.out.println("\n\nPassword must have at least one digit.\n");
            return false;
        }
        else {
            return true;
        }
    }

    public static String getSpecialChars() {
        return specialCharsStr;
    }
    String getPass() { return pass; }
}

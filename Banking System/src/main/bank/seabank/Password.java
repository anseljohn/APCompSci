package main.bank.seabank;

import java.util.ArrayList;
import java.util.Scanner;

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
        verifyPassword();
    }

    public void setPassword() {
        Scanner s = new Scanner(System.in);
        pass = s.nextLine();
        System.out.print("\n>> ");
        verifyPassword();
    }

    public boolean verifyPassword() {
        boolean hasSpecialChar = false;
        boolean hasUpperCase = false;
        boolean hasLowerCase = false;
        boolean hasDigit = false;

        for(int i = 0; i < pass.length(); i++) {
            if(Character.isLetter(pass.charAt(i)) && (pass.charAt(i) + "").equals((pass.charAt(i) + "").toUpperCase())) hasUpperCase = true;
            if(Character.isLetter(pass.charAt(i)) && (pass.charAt(i) + "").equals((pass.charAt(i) + "").toLowerCase())) hasLowerCase = true;
            if((pass.charAt(i) + "").equals(i + "")) hasDigit = true;
            for(Character s : specialChars) {
                if(pass.charAt(i) == s) {
                    hasSpecialChar = true;
                }
            }
        }

        if(pass.length() < 5 || pass.length() > 13) {
            System.out.println("Password must be between 5-13 characters.");
            setPassword();
        }
        if(!hasSpecialChar) {
            System.out.println("Password must contain at least one special character. (" + specialCharsStr + ")");
            setPassword();
        }
        if(!hasUpperCase) {
            System.out.println("Password must have at least 1 uppercase letter.");
            setPassword();
        }
        if(!hasLowerCase) {
            System.out.println("Password must have at least 1 lowercase letter.");
            setPassword();
        }
        if(!hasDigit) {
            System.out.println("Password must have at least one digit.");
            setPassword();
        }
        else {
            System.out.println( "Password set");
            return true;
        }
        return false;
    }

    public static String getSpecialChars() {
        return specialCharsStr;
    }
    public String getPass() { return pass; }
}

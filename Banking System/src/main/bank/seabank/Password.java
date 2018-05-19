package main.bank.seabank;

import java.util.ArrayList;

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
    }

    public String verifyPassword() {
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

        if(pass.length() < 5 || pass.length() > 13) return ("Password must be between 5-13 characters.");
        if(!hasSpecialChar) return("Password must contain at least one special character. (" + specialCharsStr + ")");
        if(!hasUpperCase) return("Password must have at least 1 uppercase letter.");
        if(!hasLowerCase) return("Password must have at least 1 lowercase letter.");
        if(!hasDigit) return("Password must have at least one digit.");
        else return "Password set";
    }

    public static String getSpecialChars() {
        return specialCharsStr;
    }
}

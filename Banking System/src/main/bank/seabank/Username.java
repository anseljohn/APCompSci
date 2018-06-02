package main.bank.seabank;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

class Username {
    private String username;
    Pattern pattern = Pattern.compile("\\s");

    Username (String u) {
        username = u;
    }

    static boolean verifyUsername(String username) {
    	Pattern pattern = Pattern.compile("\\s");
    	Matcher matcher = pattern.matcher(username);
        if(matcher.find()) {
            System.out.println("Username may not contain spaces!");
            return false;
        }
        if(username.length() < 5 || username.length() > 13) {
            System.out.println("Username must be between 5-13 characters.");
            return false;
        }
        return true;
    }


    String getUser() {
        return username;
    }
}

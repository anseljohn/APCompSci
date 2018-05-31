package main.bank.seabank;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Username {
    private String username;
    Pattern pattern = Pattern.compile("\\s");

    public Username (String u) {
        setUsername(u);
    }

    public void setUsername(String newUsername) {
        Matcher matcher = pattern.matcher(newUsername);
        if(matcher.find()) {
            System.out.println("Username cannot contain spaces.");
        }
        else {
            username = newUsername;
        }

    }

    public String getUser() {
        return username;
    }
}

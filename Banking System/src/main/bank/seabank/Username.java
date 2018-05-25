package main.bank.seabank;

import java.io.File;

public class Username {
    private String username;

    public Username (String u) {
        username = u;
    }

    public boolean isVerified(String test) {
        return new File(System.getProperty("user.dir") + "/data/UserAccounts/" + "test").exists() && test.length() <= 10;
    }

    public String getUser() {
        return username;
    }
}

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

public class Username {
    private String username;


    public Username (String u) {
        username = u;
    }

    public boolean isVerified(String test) {
        return new File(System.getProperty("user.dir") + "/data/UserAccounts/" + "test").exists() && test.length() <= 10;
    }


}

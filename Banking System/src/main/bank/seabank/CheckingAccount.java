package main.bank.seabank;

import java.util.Date;
import java.io.FileWriter;
import org.json.JSONArray;
import org.json.JSONObject;

public class CheckingAccount extends Account{
    private double balance;
    private Date startDate;

    public CheckingAccount(double startingBalance, Password p) {
        super(startingBalance, p);
        startDate = new Date();
    }

    public void transaction(double amount, Payment typeOfPayment) {

    }
}

package main.bank.seabank;

import java.util.ArrayList;

public class Bank {
    private ArrayList<Account> accs = new ArrayList<Account>();

    public void addAccount(Account a) {
        accs.add(a);
    }

}

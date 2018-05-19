public abstract class Account {
    private double balance;
    private int accNumber;

    public Account(double startingBalance, int accountNumber, Password p) {
        balance = startingBalance;
        accNumber = accountNumber;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public void withdraw(double amount) {
        balance -= amount;
    }

    public abstract void transaction(double amount, String typeOfCard);
}

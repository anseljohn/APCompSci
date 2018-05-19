public abstract class Account {
    private double balance;

    public Account(double startingBalance) {
        balance = startingBalance;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public void withdraw(double amount) {
        balance -= amount;
    }

    public abstract void transaction(double amount, String typeOfCard);
}

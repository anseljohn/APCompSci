import java.util.Date;
import java.io.FileWriter;
import org.json.JSONArray;
import org.json.JSONObject;

public class CheckingAccount extends Account{
    private double balance;
    private Date startDate;

    public CheckingAccount(double startingBalance, int accountNumber) {
        super(startingBalance, accountNumber);
        startDate = new Date();
    }

    public void transaction(double amount, String typeOfCard) {

    }
}

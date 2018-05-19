import java.util.ArrayList;

public class Password {
    private String pass;
    private ArrayList<Character> specialChars;
    private String specialCharsStr = "~`!@#$%^&*()+=_-{}[]\\|:;”’?/<>,.";

    public Password(String password) {
        pass = password;
        for(int i = 0; i < specialCharsStr.length(); i++) specialChars.add(specialCharsStr.charAt(i));
        verifyPassword();
    }

    public void verifyPassword() {
        boolean hasSpecialChar = false;
        boolean hasUpperCase = false;
        boolean hasLowerCase = false;
        boolean hasDigit = false;

        for(int i = 0; i < pass.length(); i++) {
            if((pass.charAt(i) + "").equals((pass.charAt(i) + "").toUpperCase())) hasUpperCase = true;
            if((pass.charAt(i) + "").equals((pass.charAt(i) + "").toLowerCase())) hasLowerCase = true;
            if((pass.charAt(i) + "").equals(i + "")) hasDigit = true;
            for(Character s : specialChars) {
                if(pass.charAt(i) == s) {
                    hasSpecialChar = true;
                }
            }
        }

        if(pass.length() < 5 || pass.length() > 13) System.out.println("Password must be between 5-13 characters.");
        if(!hasSpecialChar) System.out.println("Password must contain at least one special character. (" + specialCharsStr + ")");
        if(!hasLowerCase) System.out.println("Password must have at least 1 uppercase letter.");
        if(!hasUpperCase) System.out.println("Password must have at least 1 lowercase letter.");
        if(!hasDigit) System.out.println("Password must have at least one digit.");

    }
}

import java.util.ArrayList;

public class Password {
    private String pass;
    private ArrayList<Character> specialChars;
    private ArrayList<Integer> digits;
    private String specialCharsStr = "~`!@#$%^&*()+=_-{}[]\\|:;”’?/<>,.";

    public Password(String password) {
        pass = password;
        for(int i = 0; i < specialCharsStr.length(); i++) specialChars.add(specialCharsStr.charAt(i));
        for(int i = 0; i < 10; i++) digits.add(i);
        verifyPassword();
    }

    public void verifyPassword() {
        boolean hasSpecialChar = false;
        boolean hasUpperCase = false;
        boolean hasLowerCase = false;

        for(int i = 0; i < pass.length(); i++) {
            for(Character s : specialChars) {
                if(pass.charAt(i) == s) {
                    hasSpecialChar = true;
                    break;
                }
            }
        }
        for(int i = 0; i < pass.length(); i++) {
            if((pass.charAt(i) + "").equals((pass.charAt(i) + "").toUpperCase()));
        }




        if(pass.length() < 5 || pass.length() > 13) System.out.println("Password must be between 5-13 characters.");
        if(!hasSpecialChar) System.out.println("Must contain at least one special character. (" + specialCharsStr + ")");

    }
}

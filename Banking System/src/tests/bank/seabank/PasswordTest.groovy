package tests.bank.seabank

import main.bank.seabank.Password;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

class PasswordTest extends Password {
    Password tooShort = new Password("Ab1!");
    Password tooLong = new Password("Abcd1234567890!");
    Password noSpecials = new Password("Abcd123");
    Password noUpperCase = new Password("abcd123");
    Password noLowerCase = new Password("ABCD123");

    @Test
    void testVerifyPassword() {
        assertEquals("Password must be between 5-13 characters.", tooShort.verifyPassword());
        assertEquals("Password must be between 5-13 characters.", tooLong.verifyPassword());
        assertEquals("Password must contain at least one special character. (" + getSpecialChars() + ")", noSpecials.verifyPassword());
        assertEquals("Password must have at least 1 uppercase letter.", noUpperCase.verifyPassword());
        assertEquals("Password must have at least 1 lowercase letter.", noLowerCase.verifyPassword());
    }
}

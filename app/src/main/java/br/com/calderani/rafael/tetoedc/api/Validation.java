package br.com.calderani.rafael.tetoedc.api;


import android.widget.EditText;

import java.util.StringTokenizer;
import java.util.regex.Pattern;

/**
 * Created by Rafael on 11/08/2017.
 */
public class Validation {

    // Regular Expressions
    private static final String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final String PHONE_REGEX = "(\\d{2}) \\d{5}-\\d{4}";

    // Error Messages
    private static final String REQUIRED_MSG = "Mandatory field.";
    private static final String EMAIL_MSG = "Invalid email.";
    private static final String PHONE_MSG = "Invalid phone. Ex.: (##) #####-####";
    private static final String MINLENGTH_MSG = "This field requires at least %s characters.";

    public static boolean isEmailAddress(EditText editText, boolean required) {
        return isValid(editText, EMAIL_REGEX, EMAIL_MSG, required);
    }

    public static boolean isPhoneNumber(EditText editText, boolean required) {
        return isValid(editText, PHONE_REGEX, PHONE_MSG, required);
    }

    public static boolean validateMinimumLength(EditText editText, int minLength, boolean required) {
        boolean result = true;
        String text = editText.getText().toString().trim();
        editText.setError(null);

        if (text.length() < minLength) {
            editText.setError(String.format(MINLENGTH_MSG, minLength));
            result = false;
        }
        return result;
    }

    // return true if the input field is valid, based on the parameter passed
    public static boolean isValid(EditText editText, String regex, String errMsg, boolean required) {

        String text = editText.getText().toString().trim();
        // clearing the error, if it was previously set by some other values
        editText.setError(null);

        // text required and editText is blank, so return false
        if ( required && !hasText(editText) ) return false;

        // pattern doesn't match so returning false
        if (required && !Pattern.matches(regex, text)) {
            editText.setError(errMsg);
            return false;
        }

        return true;
    }

    // check the input field has any text or not
    // return true if it contains text otherwise false
    public static boolean hasText(EditText editText) {

        String text = editText.getText().toString().trim();
        editText.setError(null);

        if (text.length() == 0) {
            editText.setError(REQUIRED_MSG);
            return false;
        }

        return true;
    }
}

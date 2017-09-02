package br.com.calderani.rafael.tetoedc.api;


import android.widget.EditText;

import java.util.StringTokenizer;
import java.util.regex.Pattern;

import br.com.calderani.rafael.tetoedc.R;

/**
 * Created by Rafael on 11/08/2017.
 */
public class Validation {

    // Regular Expressions
    private static final String EMAIL_REGEX = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final String PHONE_REGEX = "(\\d{2}) \\d{5}-\\d{4}";

    public static boolean isEmailAddress(EditText editText, boolean required, String errorMessage) {
        return isValid(editText, EMAIL_REGEX, errorMessage, required);
    }

    public static boolean isPhoneNumber(EditText editText, boolean required, String errorMessage) {
        return isValid(editText, PHONE_REGEX, errorMessage, required);
    }

    public static boolean validateMinimumLength(EditText editText, int minLength, String errorMessage) {
        boolean result = true;
        String text = editText.getText().toString().trim();
        editText.setError(null);

        if (text.length() < minLength) {
            editText.setError(String.format(errorMessage, minLength));
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
        if ( required && !hasText(editText, errMsg) ) return false;

        // pattern doesn't match so returning false
        if (required && !Pattern.matches(regex, text)) {
            editText.setError(errMsg);
            return false;
        }

        return true;
    }

    // check the input field has any text or not
    // return true if it contains text otherwise false
    public static boolean hasText(EditText editText, String errMsg) {

        String text = editText.getText().toString().trim();
        editText.setError(null);

        if (text.length() == 0) {
            editText.setError(errMsg);
            return false;
        }

        return true;
    }
}

package se.karingotrafiken.timemanager.rest.utils;

import java.util.regex.Pattern;

public class StringUtils {

    private static final String emailRegex = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
    private static final String personalNumberRegex = "^\\d{2}([0][1-9]|[1][0-2])([0][1-9]|[1-2][0-9]|[3][0-1])-\\d{4}$";

    public static boolean isValidEmail(String email) {
        return match(email, emailRegex);
    }

    public static boolean isValidPersonalNumber(String personalNumber) {
        return match(personalNumber, personalNumberRegex);
    }

    private static boolean match(String evaluated, String regex) {
        return evaluated != null && Pattern.matches(regex, evaluated);
    }

    public static boolean isValidPassword(String password) {
        return password != null && password.length() > 5;
    }
}

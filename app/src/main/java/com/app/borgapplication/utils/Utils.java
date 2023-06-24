package com.app.borgapplication.utils;

import java.util.regex.Pattern;

public class Utils {

    public static final Pattern EMAIL_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static boolean validEmail(String emailString) {
        return EMAIL_REGEX.matcher(emailString).find();
    }
}

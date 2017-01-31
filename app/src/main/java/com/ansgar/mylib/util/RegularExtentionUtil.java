package com.ansgar.mylib.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by kirill on 31.1.17.
 */

public class RegularExtentionUtil {

    private static final String EMAIL_PATTER = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
            "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public static boolean checkEmail(String email) {
        Pattern pattern = Pattern.compile(EMAIL_PATTER);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}

package com.ansgar.mylib.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by kirill on 30.1.17.
 */

public class DateUtils {

    private static final String DATE_FORMAT = "dd MMM yyyy kk:mm";
    private static final String NEW_FILE_DATE_FORMAT = "ddMMyyyykk:mm";

    public static String getDate(){
        String date;
        Calendar calendar = Calendar.getInstance();
        DateFormat format = new SimpleDateFormat(DATE_FORMAT);
        date = format.format(calendar.getTime());
        return date;
    }

    public static String getNewFileDate(){
        String date;
        Calendar calendar = Calendar.getInstance();
        DateFormat format = new SimpleDateFormat(DATE_FORMAT);
        date = format.format(calendar.getTime());
        return date;
    }

}

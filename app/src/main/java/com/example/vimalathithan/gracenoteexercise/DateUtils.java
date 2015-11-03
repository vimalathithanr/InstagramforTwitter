package com.example.vimalathithan.gracenoteexercise;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by vimalathithan on 10/19/2015.
 * The purpose of this class is to convert date format.
 */
public class DateUtils {

    private static final SimpleDateFormat sFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);

    public static long getDateInMs(String dateString)  {
        if (!StringUtils.isStringValid(dateString)) {
            return System.currentTimeMillis();
        }

        try {
            Date date = sFormat.parse(dateString);
            return date.getTime();
        } catch (ParseException ex) {
            return System.currentTimeMillis();
        }
    }

    public static String getDateString(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return sFormat.format(calendar.getTime());
    }
}

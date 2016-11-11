package net.jordanalphonso.commons.utils;

import java.util.Calendar;

/**
 * Created by jordan.alphonso on 11/11/2016.
 */

public class TimeUtils {

    private TimeUtils() {
        //accessed statically
    }

    public static String generateTime(Calendar cal) {
        cal.setTime(new java.util.Date());
        return String.valueOf(cal.get(Calendar.HOUR)) + "."
                + String.valueOf(cal.get(Calendar.MINUTE)) + "."
                + String.valueOf(cal.get(Calendar.SECOND));
    }
}

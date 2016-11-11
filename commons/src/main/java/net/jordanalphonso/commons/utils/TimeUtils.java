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
        String hr = String.valueOf(cal.get(Calendar.HOUR));
        String mn = String.valueOf(cal.get(Calendar.MINUTE)).length() == 1 ?
                "0" + String.valueOf(cal.get(Calendar.MINUTE)) : String.valueOf(cal.get(Calendar.MINUTE));
        String sec = String.valueOf(cal.get(Calendar.SECOND)).length() == 1 ?
                "0" + String.valueOf(cal.get(Calendar.SECOND)) : String.valueOf(cal.get(Calendar.SECOND));

        return hr + "." + mn + "." + sec;
    }
}

package net.jordanalphonso.commons.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by jordan.alphonso on 11/11/2016.
 */

public class TimeUtils {

    private static final Map<Integer, String> daysOfWeek = new HashMap<Integer, String>() {
        {
            put(1, "SUN");
            put(2, "MON");
            put(3, "TUES");
            put(4, "WED");
            put(5, "THURS");
            put(6, "FRI");
            put(7, "SAT");
        }
    };

    private TimeUtils() {
        //accessed statically
    }

    public static List<String> generateTime(Calendar cal) {
        List<String> list = new LinkedList<>();
        cal.setTime(new java.util.Date());
        String hr = String.valueOf(cal.get(Calendar.HOUR));
        String mn = String.valueOf(cal.get(Calendar.MINUTE)).length() == 1 ?
                "0" + String.valueOf(cal.get(Calendar.MINUTE)) : String.valueOf(cal.get(Calendar.MINUTE));
        String sec = String.valueOf(cal.get(Calendar.SECOND)).length() == 1 ?
                "0" + String.valueOf(cal.get(Calendar.SECOND)) : String.valueOf(cal.get(Calendar.SECOND));
        String ampm;
        int AMPM = cal.get(Calendar.AM_PM);
        if (AMPM == Calendar.AM) {
            ampm = "AM";
        } else {
            ampm = "PM";
        }

        if ("0".equals(hr)) {
            list.add("12");
        } else if (hr.length() == 1) {
            list.add("0"+hr);
        } else {
            list.add(hr);
        }
        list.add(mn);
        list.add(sec);
        list.add(ampm);

        return list;
    }

    public static Integer getCurrentSecond(Calendar cal) {
        cal.setTime(new java.util.Date());
        return cal.get(Calendar.SECOND);
    }

    public static Integer getCurrentMillisecond(Calendar cal) {
        cal.setTime(new java.util.Date());
        return cal.get(Calendar.MILLISECOND);
    }

    public static int getCurrentHour(Calendar cal) {
        cal.setTime(new java.util.Date());
        return cal.get(Calendar.HOUR_OF_DAY);
    }

    public static String getCurrentDayOfWeek(Calendar cal) {
        cal.setTime(new java.util.Date());
        int day = cal.get(Calendar.DAY_OF_WEEK);
        return daysOfWeek.get(day);
    }

    public static String getCurrentDayofMonth(Calendar cal) {
        cal.setTime(new java.util.Date());
        int dayM = cal.get(Calendar.DAY_OF_MONTH);
        return dayM < 10 ? "0"+String.valueOf(dayM) : String.valueOf(dayM);
    }
}

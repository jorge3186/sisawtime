package net.jordanalphonso.commons.utils;

/**
 * Created by jordan.alphonso on 11/28/2016.
 */

public class StringUtils {

    public static final String E = "";

    private StringUtils() {
        //accessed statically
    }

    public static boolean isEmpty(String value) {
        if (value == null) {
            return true;
        }
        if (E.equals(value.trim())) {
            return true;
        }
        return false;
    }

    public static boolean isNotEmpty(String value) {
        return !isEmpty(value);
    }

}

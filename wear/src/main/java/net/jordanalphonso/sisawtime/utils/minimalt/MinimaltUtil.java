package net.jordanalphonso.sisawtime.utils.minimalt;

import android.content.res.Resources;
import android.graphics.Color;
import android.support.annotation.NonNull;

import net.jordanalphonso.commons.utils.TimeUtils;
import net.jordanalphonso.sisawtime.utils.WatchFaceUtil;

import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jordan.alphonso on 11/14/2016.
 */

public class MinimaltUtil extends WatchFaceUtil {

    private static Integer currentSelection;

    private static Resources resources;

    private static final Map<Integer, String> colorMap = new LinkedHashMap<Integer, String>() {
        {
            put(0, "#FFFFBB33");
            put(1, "#4DCBF9");
            put(2, "#ACFC3E");
            put(3, "#AF66FF");
            put(4, "#FF29AE");
        }
    };

    private MinimaltUtil() {
        setServiceName("MinimaltWatchFaceService");
    }

    public static int getColor() {
        if (currentSelection == null || currentSelection >= colorMap.size()) {
            currentSelection = 0;
        }
        return Color.parseColor(colorMap.get(currentSelection));
    }

    public static void changeColor() {
        if (currentSelection == null || currentSelection >= colorMap.size()) {
            currentSelection = 0;
        } else {
            currentSelection++;
        }
    }

    @NonNull
    public static String generateTimeString(Calendar cal, boolean includeSeconds) {
        StringBuilder time = new StringBuilder();
        List<String> timeGrab = TimeUtils.generateTime(cal);

        time.append(timeGrab.get(0));
        time.append(".");
        time.append(timeGrab.get(1));
        if (includeSeconds) {
            time.append(".").append(timeGrab.get(2));
        }
        return time.toString();
    }

    public static String getAMPM(Calendar cal) {
        List<String> time = TimeUtils.generateTime(cal);
        return time.get(3);
    }
}

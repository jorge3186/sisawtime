package net.jordanalphonso.sisawtime.utils.minimalt;

import android.content.res.Resources;
import android.graphics.Color;
import android.support.annotation.NonNull;

import net.jordanalphonso.commons.utils.StringUtils;
import net.jordanalphonso.commons.utils.TimeUtils;
import net.jordanalphonso.sisawservices.constants.OpenWeatherMapUtil;
import net.jordanalphonso.sisawservices.model.openweathermap.DailyWeather;
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

    private static Integer dailyStepCount;

    private static Integer dailyStepGoal;

    private static DailyWeather weather;

    private static Resources resources;

    private static boolean askForLocations = true;

    private static boolean locationsGranted = false;

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
        time.append(":");
        time.append(timeGrab.get(1));
        if (includeSeconds) {
            time.append(":").append(timeGrab.get(2));
        }
        return time.toString();
    }

    public static String getAMPM(Calendar cal) {
        List<String> time = TimeUtils.generateTime(cal);
        return time.get(3);
    }

    public static void updateStepCount(Integer stepCount) {
        MinimaltUtil.dailyStepCount = stepCount;
    }

    public static void updateWeather(DailyWeather dailyWeather) {
        MinimaltUtil.weather = dailyWeather;
    }

    public static String getDailyStepCount() {
        if (dailyStepCount == null) {
            return StringUtils.E;
        } else {
            return dailyStepCount.toString();
        }
    }

    public static DailyWeather getWeather() {
        return weather;
    }

    public static String getTemperature(boolean celsius) {
        if (weather == null) {
            return StringUtils.E;
        } else {
            if (celsius) {
                return weather.getMain().getTemperatureC()
                        .toString().split("\\.")[0] + "°";
            } else {
                return weather.getMain().getTemperatureInF()
                        .toString().split("\\.")[0] + "°";
            }
        }
    }

    public static String getWeatherSummary() {
        if (weather == null) {
            return StringUtils.E;
        } else {
            String icon = weather.getWeather().get(0).getIcon();
            return OpenWeatherMapUtil.getWeatherIcon(icon);
        }
    }

    public static void updateStepGoal(Integer goal) {
        MinimaltUtil.dailyStepGoal = goal;
    }

    public static String getDailyStepGoal (){
        if (dailyStepGoal == null) {
            return StringUtils.E;
        } else {
            return dailyStepGoal.toString();
        }
    }

    public static boolean isLocationsGranted() {
        return MinimaltUtil.locationsGranted;
    }

    public static void updatedLocationsGranted(boolean locationsGranted) {
        MinimaltUtil.locationsGranted = locationsGranted;
    }

    public static void setAskForLocations(boolean askForLocations) {
        MinimaltUtil.askForLocations = askForLocations;
    }

    public static boolean isAskForLocations() {
        return MinimaltUtil.askForLocations;
    }
}

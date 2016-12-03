package net.jordanalphonso.sisawservices.constants;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jordan.alphonso on 11/23/2016.
 */

public class OpenWeatherMapUtil {

    private static final Map<String, String> icons = new HashMap<String, String>() {
        {
            put("01d", "clear");
            put("02d", "clouds");
            put("03d", "cloudy");
            put("04d", "cloudy");
            put("09d", "showers");
            put("10d", "rain");
            put("11d", "storms");
            put("13d", "snow");

            put("01n", "clear");
            put("02n", "clouds");
            put("03n", "cloudy");
            put("04n", "cloudy");
            put("09n", "showers");
            put("10n", "rain");
            put("11n", "storms");
            put("13n", "snow");

            put("50d", "mist");
        }
    };

    private OpenWeatherMapUtil() {
        //accessed statically
    }

    public static final String API_KEY = "16ead5bdbb4e6717738efa7a3f2b1404";

    public static final String URL_FOR_COORD = "http://api.openweathermap.org/data/2.5/weather?lat={lat}&lon={lon}&appid={api_key}";

    public static String getWeatherIcon(String icon) {
        return icons.get(icon);
    }
}

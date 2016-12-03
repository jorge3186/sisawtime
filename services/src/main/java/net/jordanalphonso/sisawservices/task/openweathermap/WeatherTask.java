package net.jordanalphonso.sisawservices.task.openweathermap;

import android.os.AsyncTask;
import android.util.Log;

import net.jordanalphonso.sisawservices.constants.OpenWeatherMapUtil;
import net.jordanalphonso.sisawservices.model.openweathermap.DailyWeather;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * Created by jordan.alphonso on 11/23/2016.
 */

public class WeatherTask extends AsyncTask<Double, Void, DailyWeather> {

    private Double longitude;

    private Double latitude;

    @Override
    protected DailyWeather doInBackground(Double... coords) {
        if (coords.length == 2) {
            setLongitude(coords[0]);
            setLatitude(coords[1]);

            try {
                RestTemplate restTemplate = generateRestTemplate();
                return restTemplate.getForObject(OpenWeatherMapUtil.URL_FOR_COORD, DailyWeather.class,
                        getLatitude(), getLongitude(), OpenWeatherMapUtil.API_KEY);
            } catch (Exception e) {
                Log.e("WeatherTask", e.getMessage());
            }
        }
        return null;
    }

    private RestTemplate generateRestTemplate() {
        RestTemplate template = new RestTemplate();
        template.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        return template;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
}

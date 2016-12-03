package net.jordanalphonso.sisawservices.service.weather;

import android.os.AsyncTask;

import net.jordanalphonso.sisawservices.model.openweathermap.DailyWeather;
import net.jordanalphonso.sisawservices.task.openweathermap.WeatherTask;

import java.util.concurrent.ExecutionException;

/**
 * Created by jordan.alphonso on 11/23/2016.
 */

public class WeatherService {

    private WeatherTask executingTask;

    public DailyWeather getResult(Double... params) {
        executingTask = new WeatherTask();

        try {
            executingTask.execute(params);
            return executingTask.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
}

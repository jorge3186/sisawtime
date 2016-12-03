package net.jordanalphonso.sisawservices.model.openweathermap;

import java.util.List;

/**
 * Created by jordan.alphonso on 11/23/2016.
 */

public class DailyWeather {

    private Long id;

    private String name;

    private String cod;

    private Coordinate coord;

    private List<WeatherSummary> weather;

    private String base;

    private Weather main;

    private Wind wind;

    private Rain rain;

    private Clouds clouds;

    private Long dt;

    private WeatherSystem sys;

    private Long visibility;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public Coordinate getCoord() {
        return coord;
    }

    public void setCoord(Coordinate coord) {
        this.coord = coord;
    }

    public List<WeatherSummary> getWeather() {
        return weather;
    }

    public void setWeather(List<WeatherSummary> weather) {
        this.weather = weather;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public Weather getMain() {
        return main;
    }

    public void setMain(Weather main) {
        this.main = main;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Rain getRain() {
        return rain;
    }

    public void setRain(Rain rain) {
        this.rain = rain;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    public Long getDt() {
        return dt;
    }

    public void setDt(Long dt) {
        this.dt = dt;
    }

    public WeatherSystem getSys() {
        return sys;
    }

    public void setSys(WeatherSystem sys) {
        this.sys = sys;
    }

    public Long getVisibility() {
        return visibility;
    }

    public void setVisibility(Long visibility) {
        this.visibility = visibility;
    }
}

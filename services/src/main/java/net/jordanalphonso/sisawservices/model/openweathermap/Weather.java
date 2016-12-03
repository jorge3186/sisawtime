package net.jordanalphonso.sisawservices.model.openweathermap;

/**
 * Created by jordan.alphonso on 11/23/2016.
 */

public class Weather {

    private Double temp;

    private Double pressure;

    private Double humidity;

    private Double temp_min;

    private Double temp_max;

    private Double sea_level;

    private Double grnd_level;

    public Double getTemp() {
        return temp;
    }

    public void setTemp(Double temp) {
        this.temp = temp;
    }

    public Double getPressure() {
        return pressure;
    }

    public void setPressure(Double pressure) {
        this.pressure = pressure;
    }

    public Double getHumidity() {
        return humidity;
    }

    public void setHumidity(Double humidity) {
        this.humidity = humidity;
    }

    public Double getTemp_min() {
        return temp_min;
    }

    public void setTemp_min(Double temp_min) {
        this.temp_min = temp_min;
    }

    public Double getTemp_max() {
        return temp_max;
    }

    public void setTemp_max(Double temp_max) {
        this.temp_max = temp_max;
    }

    public Double getSea_level() {
        return sea_level;
    }

    public void setSea_level(Double sea_level) {
        this.sea_level = sea_level;
    }

    public Double getGrnd_level() {
        return grnd_level;
    }

    public void setGrnd_level(Double grnd_level) {
        this.grnd_level = grnd_level;
    }

    public Double getTemperatureInF() {
        if (temp != null) {
            return temp * 1.8 - 459.67;
        }
        return temp;
    }

    public Double getTemperatureC() {
        if (temp != null) {
            return temp - 273.15;
        }
        return temp;
    }
}

package net.jordanalphonso.sisawservices.model.openweathermap;

/**
 * Created by jordan.alphonso on 11/23/2016.
 */

public class Wind {

    private Double speed;

    private Double deg;

    private Double gust;

    public Double getSpeed() {
        return speed;
    }

    public void setSpeed(Double speed) {
        this.speed = speed;
    }

    public Double getDeg() {
        return deg;
    }

    public void setDeg(Double deg) {
        this.deg = deg;
    }

    public Double getGust() {
        return gust;
    }

    public void setGust(Double gust) {
        this.gust = gust;
    }
}

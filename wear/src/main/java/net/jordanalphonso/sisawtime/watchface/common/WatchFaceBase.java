package net.jordanalphonso.sisawtime.watchface.common;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.Calendar;

/**
 * Created by jordan on 11/11/16.
 */

public class WatchFaceBase {

    private Canvas canvas;

    private Rect bounds;

    private Calendar calendar;

    private Paint white;

    private Paint black;

    protected Rect getBounds() {
        return bounds;
    }

    protected Canvas getCanvas() {
        return canvas;
    }

    protected Calendar getCalendar() { return calendar; }

    public void initialize(Canvas canvas, Rect bounds, Calendar cal) {
        this.canvas = canvas;
        this.bounds = bounds;
        this.calendar = cal;
    }

    protected Double getRadius() {
        if (getBounds() != null) {
            return (double)getBounds().height()/2;
        }
        return null;
    }

    protected Double getRadius(double  percent) {
        if (getRadius() != null) {
            return getRadius()*percent;
        }
        return null;
    }

    public Paint getBlack() {
        if (black == null) {
            black = new Paint();
            black.setAntiAlias(true);
            black.setColor(Color.BLACK);
        }
        return black;
    }

    public Paint getWhite() {
        if (white == null) {
            white = new Paint();
            white.setAntiAlias(true);
            white.setColor(Color.WHITE);
        }
        return white;
    }
}

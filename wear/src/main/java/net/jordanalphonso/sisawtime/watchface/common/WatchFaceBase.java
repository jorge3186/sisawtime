package net.jordanalphonso.sisawtime.watchface.common;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

/**
 * Created by jordan on 11/11/16.
 */

public class WatchFaceBase {

    private Canvas canvas;

    private Rect bounds;

    private Paint white;

    private Paint black;

    protected Rect getBounds() {
        return bounds;
    }

    protected Canvas getCanvas() {
        return canvas;
    }

    public void initialize(Canvas canvas, Rect bounds) {
        this.canvas = canvas;
        this.bounds = bounds;
    }

    protected Integer getRadius() {
        if (getBounds() != null) {
            return getBounds().height()/2;
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

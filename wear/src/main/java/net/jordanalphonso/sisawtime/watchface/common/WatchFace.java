package net.jordanalphonso.sisawtime.watchface.common;

import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.Calendar;

/**
 * Created by jordan on 11/11/16.
 */

public interface WatchFace {

    void draw(boolean ambientMode);

    void initialize(Canvas canvas, Rect bounds, Calendar cal);

}

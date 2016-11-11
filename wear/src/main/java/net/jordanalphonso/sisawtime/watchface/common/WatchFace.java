package net.jordanalphonso.sisawtime.watchface.common;

import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by jordan on 11/11/16.
 */

public interface WatchFace {

    void draw();

    void initialize(Canvas canvas, Rect bounds);

}

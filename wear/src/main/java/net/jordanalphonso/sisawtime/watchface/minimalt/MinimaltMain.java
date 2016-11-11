package net.jordanalphonso.sisawtime.watchface.minimalt;

import android.graphics.Canvas;
import android.graphics.Rect;

import net.jordanalphonso.sisawtime.watchface.common.WatchFace;
import net.jordanalphonso.sisawtime.watchface.common.WatchFaceBase;

/**
 * Created by jordan on 11/11/16.
 */

public class MinimaltMain extends WatchFaceBase implements WatchFace {

    private MinimaltBackground background;

    @Override
    public void draw() {
        background.draw();
    }

    @Override
    public void initialize(Canvas canvas, Rect bounds) {
        super.initialize(canvas, bounds);
        background = new MinimaltBackground();
        background.initialize(canvas, bounds);
    }
}

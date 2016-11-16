package net.jordanalphonso.sisawtime.watchface.minimalt;

import android.graphics.Canvas;
import android.graphics.Rect;

import net.jordanalphonso.sisawtime.watchface.common.WatchFace;
import net.jordanalphonso.sisawtime.watchface.common.WatchFaceBase;

import java.util.Calendar;

/**
 * Created by jordan on 11/11/16.
 */

public class MinimaltMain extends WatchFaceBase implements WatchFace {

    private MinimaltBackground background;
    private MinimaltMidLevel midLevel;
    private MinimaltTopLevel topLevel;

    @Override
    public void initialize(Canvas canvas, Rect bounds, Calendar cal) {
        super.initialize(canvas, bounds, cal);
        background = new MinimaltBackground();
        background.initialize(canvas, bounds, cal);
        midLevel = new MinimaltMidLevel();
        midLevel.initialize(canvas, bounds, cal);
        topLevel = new MinimaltTopLevel();
        topLevel.initialize(canvas, bounds, cal);
    }

    @Override
    public void draw(boolean ambientMode) {
        background.draw(ambientMode);
        midLevel.draw(ambientMode);
        topLevel.draw(ambientMode);
    }
}

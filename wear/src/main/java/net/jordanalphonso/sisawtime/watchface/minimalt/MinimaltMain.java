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

    private MinimaltBackground background = new MinimaltBackground();
    private MinimaltMidLevel midLevel = new MinimaltMidLevel();
    private MinimaltTopLevel  topLevel = new MinimaltTopLevel();

    private Float batteryLevel;

    @Override
    public void initialize(Canvas canvas, Rect bounds, Calendar cal) {
        super.initialize(canvas, bounds, cal);
        background.initialize(canvas, bounds, cal);
        midLevel.initialize(canvas, bounds, cal);
        topLevel.initialize(canvas, bounds, cal);
    }

    @Override
    public void draw(boolean ambientMode) {
        background.setBatteryLevel(batteryLevel);
        background.draw(ambientMode);
        midLevel.draw(ambientMode);
        topLevel.draw(ambientMode);
    }

    public void setBatteryLevel(Float batteryLevel) {
        this.batteryLevel = batteryLevel;
    }
}

package net.jordanalphonso.sisawtime.watchface.minimalt;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import net.jordanalphonso.commons.utils.TimeUtils;
import net.jordanalphonso.sisawtime.watchface.common.WatchFace;
import net.jordanalphonso.sisawtime.watchface.common.WatchFaceBase;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by jordan on 11/11/16.
 */

public class MinimaltMain extends WatchFaceBase implements WatchFace {

    private MinimaltBackground background;

    @Override
    public void draw() {
        background.draw();
        Paint p = new Paint();
        p.setColor(Color.WHITE);
        p.setTextSize(12);
        getCanvas().drawText(TimeUtils.generateTime(getCalendar()),
                getBounds().exactCenterX(), getBounds().exactCenterY(), p);
    }

    @Override
    public void initialize(Canvas canvas, Rect bounds, Calendar cal) {
        super.initialize(canvas, bounds, cal);
        background = new MinimaltBackground();
        background.initialize(canvas, bounds, cal);
    }
}

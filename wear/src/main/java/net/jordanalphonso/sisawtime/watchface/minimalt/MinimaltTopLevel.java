package net.jordanalphonso.sisawtime.watchface.minimalt;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;

import net.jordanalphonso.commons.utils.TimeUtils;
import net.jordanalphonso.sisawtime.utils.minimalt.MinimaltUtil;
import net.jordanalphonso.sisawtime.watchface.common.WatchFaceBase;
import net.jordanalphonso.sisawtime.watchface.common.WatchFace;

public class MinimaltTopLevel extends WatchFaceBase implements WatchFace {

    @Override
    public void draw(boolean ambientMode) {
        generateTime(ambientMode);
        generateUIStrokes(ambientMode);
    }

    private void generateTime(boolean ambientMode) {
        Paint timePaint = new Paint();
        timePaint.setAntiAlias(true);
        timePaint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD));
        timePaint.setTextSize(64);
        timePaint.setColor(Color.WHITE);
        timePaint.setTextAlign(Paint.Align.CENTER);

        getCanvas().drawText(MinimaltUtil.generateTimeString(getCalendar(), false),
                getBounds().exactCenterX(),getBounds().exactCenterY()/1.25F, timePaint);
    }

    private void generateUIStrokes(boolean ambientMode) {
        Paint p = new Paint();
        p.setAntiAlias(!ambientMode);
        p.setStrokeWidth(1.5F);
        p.setColor(MinimaltUtil.getColor());

        int millis = TimeUtils.getCurrentMillisecond(getCalendar());
        int secs = TimeUtils.getCurrentSecond(getCalendar());
        int hr = TimeUtils.getCurrentHour(getCalendar());

        float min = getBounds().exactCenterX()/3.3F;
        float max = getBounds().exactCenterX()*1.7F;

        float stopXMillisEven = (millis/1000F) * (max - min) + min;
        float stopXMillisOdd = (millis/1000F) * (min - max) + max;
        float stopXSecs = (((secs*1000)+millis)/60000F) * (max - min) + min;
        float stopXHr = (((hr*150000)+(secs*1000)+millis)/3600000F) * (max - min) + min;

        if (!ambientMode) {
            //Milliseconds animated line
            if (secs % 2 == 0) {
                getCanvas().drawLine(min, getBounds().exactCenterY()/1.15F,
                        stopXMillisEven, getBounds().exactCenterY()/1.15F, p);
            } else {
                getCanvas().drawLine(max, getBounds().exactCenterY()/1.15F,
                        stopXMillisOdd, getBounds().exactCenterY()/1.15F, p);
            }

            //Seconds animated line
            getCanvas().drawLine(min, getBounds().exactCenterY()/1.1F,
                    stopXSecs, getBounds().exactCenterY()/1.1F, p);

            //hour animated line
            getCanvas().drawLine(min, getBounds().exactCenterY()/1.05F,
                    stopXHr, getBounds().exactCenterY()/1.05F, p);
        }
    }
}

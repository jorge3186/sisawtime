package net.jordanalphonso.sisawtime.watchface.minimalt;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.v4.content.res.ResourcesCompat;

import net.jordanalphonso.commons.utils.StringUtils;
import net.jordanalphonso.commons.utils.TimeUtils;
import net.jordanalphonso.sisawtime.R;
import net.jordanalphonso.sisawtime.services.minimalt.MinimaltWatchFaceService;
import net.jordanalphonso.sisawtime.utils.minimalt.MinimaltUtil;
import net.jordanalphonso.sisawtime.watchface.common.WatchFace;
import net.jordanalphonso.sisawtime.watchface.common.WatchFaceBase;

/**
 * Created by jordan.alphonso on 11/15/2016.
 */

public class MinimaltMidLevel extends WatchFaceBase implements WatchFace {


    @Override
    public void draw(boolean ambientMode) {
        populateDateBox(ambientMode);
        populateStepsBox(ambientMode);
        populateWeatherBox(ambientMode);
    }

    private void populateDateBox(boolean ambientMode) {
        Paint p = new Paint();
        p.setAntiAlias(!ambientMode);
        p.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD));
        p.setColor(MinimaltUtil.getColor());
        p.setTextSize(14);
        p.setTextAlign(Paint.Align.CENTER);

        getCanvas().drawText(TimeUtils.getCurrentDayOfWeek(getCalendar()),
                getBounds().exactCenterX()+(getBounds().width()/4.6F), getBounds().exactCenterY()+15, p);

        p.setTextSize(32);
        getCanvas().drawText(TimeUtils.getCurrentDayofMonth(getCalendar()),
                getBounds().exactCenterX()+(getBounds().width()/4.6F), getBounds().exactCenterY()+44, p);
    }

    private void populateStepsBox(boolean ambientMode) {
        if (!StringUtils.E.equals(MinimaltUtil.getDailyStepCount())) {
            Paint p = new Paint();
            p.setAntiAlias(!ambientMode);
            p.setColor(MinimaltUtil.getColor());
            p.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD));
            p.setTextAlign(Paint.Align.CENTER);
            p.setTextSize(14);

            getCanvas().drawText("STEPS", getBounds().exactCenterX()-(getBounds().width()/4.4F),
                    getBounds().exactCenterY()+15F, p);

            p.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL));
            p.setTextSize(12);
            getCanvas().drawText(MinimaltUtil.getDailyStepCount(), getBounds().exactCenterX()-(getBounds().width()/4.4F),
                    getBounds().exactCenterY()+30F, p);
        }
    }

    private void populateWeatherBox(boolean ambientMode) {
        if (!StringUtils.E.equals(MinimaltUtil.getTemperature(false))) {
            Paint p = new Paint();
            p.setAntiAlias(!ambientMode);
            p.setColor(MinimaltUtil.getColor());
            p.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD));
            p.setTextSize(28);
            p.setTextAlign(Paint.Align.CENTER);

            getCanvas().drawText(MinimaltUtil.getTemperature(false), getBounds().exactCenterX(),
                    getBounds().exactCenterY()+25F, p);

            p.setTextSize(14);
            p.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL));
            getCanvas().drawText(MinimaltUtil.getWeatherSummary(), getBounds().exactCenterX(),
                    getBounds().exactCenterY()+42F, p);
        }
    }
}

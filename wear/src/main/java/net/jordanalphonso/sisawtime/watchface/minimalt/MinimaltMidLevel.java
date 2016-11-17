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

import net.jordanalphonso.commons.utils.TimeUtils;
import net.jordanalphonso.sisawtime.R;
import net.jordanalphonso.sisawtime.utils.minimalt.MinimaltUtil;
import net.jordanalphonso.sisawtime.watchface.common.WatchFace;
import net.jordanalphonso.sisawtime.watchface.common.WatchFaceBase;

/**
 * Created by jordan.alphonso on 11/15/2016.
 */

public class MinimaltMidLevel extends WatchFaceBase implements WatchFace {


    @Override
    public void draw(boolean ambientMode) {
        generateBoxes(ambientMode);
        populateDateBox(ambientMode);
        populateStepsBox(ambientMode);
    }

    private void generateBoxes(boolean ambientMode) {
        Paint p = new Paint();
        p.setAntiAlias(!ambientMode);
        p.setColor(MinimaltUtil.getColor());
        p.setStrokeWidth(0.6F);
        p.setStyle(Paint.Style.STROKE);

        getCanvas().save();
        getCanvas().translate((getBounds().width() / 4.75F), 40);
        getCanvas().drawRect(getBounds().exactCenterX()/1.2F, getBounds().exactCenterY()*1.2F,
                getBounds().exactCenterX()*1.2F, getBounds().exactCenterY()/1.2F, p);
        getCanvas().restore();

        getCanvas().save();
        getCanvas().translate(-(getBounds().width()/4.75F), 40);
        getCanvas().drawRect(getBounds().exactCenterX()/1.2F, getBounds().exactCenterY()*1.2F,
                getBounds().exactCenterX()*1.2F, getBounds().exactCenterY()/1.2F, p);
        getCanvas().restore();

        getCanvas().save();
        getCanvas().translate(0, 65);
        getCanvas().drawRect(getBounds().exactCenterX()/1.2F, getBounds().exactCenterY()*1.2F,
                getBounds().exactCenterX()*1.2F, getBounds().exactCenterY()/1.2F, p);
        getCanvas().restore();
    }

    private void populateDateBox(boolean ambientMode) {
        Paint p = new Paint();
        p.setAntiAlias(!ambientMode);
        p.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD));
        p.setColor(MinimaltUtil.getColor());
        p.setTextSize(14);
        p.setTextAlign(Paint.Align.CENTER);

        getCanvas().drawText(TimeUtils.getCurrentDayOfWeek(getCalendar()),
                getBounds().exactCenterX()+(getBounds().width()/4.6F), getBounds().exactCenterY()+33, p);

        p.setTextSize(32);
        getCanvas().drawText(TimeUtils.getCurrentDayofMonth(getCalendar()),
                getBounds().exactCenterX()+(getBounds().width()/4.6F), getBounds().exactCenterY()+62, p);
    }

    private void populateStepsBox(boolean ambientMode) {
        Paint p = new Paint();
        p.setAntiAlias(!ambientMode);
        p.setColor(MinimaltUtil.getColor());
        p.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD));
        p.setTextAlign(Paint.Align.CENTER);
        p.setTextSize(12);
        p.setColorFilter(new LightingColorFilter(MinimaltUtil.getColor(), 1));

        Bitmap runningIcon = BitmapFactory.decodeResource(
                MinimaltUtil.getResources(), R.drawable.minimalt_running_icon);
        Bitmap scaledIcon = Bitmap.createScaledBitmap(runningIcon, 26, 26, false);
        getCanvas().drawBitmap(scaledIcon, getBounds().exactCenterX()-(getBounds().width()/4.1F),
                getBounds().exactCenterY()+20, p);

        p.setColorFilter(null);
        getCanvas().drawText(MinimaltUtil.getDailyStepCount(), getBounds().exactCenterX()-(getBounds().width()/4.9F),
                getBounds().exactCenterY()+60, p);

        getCanvas().drawLine((getBounds().exactCenterX()-(getBounds().width()/3.6F)), getBounds().exactCenterY()+65,
                getBounds().exactCenterX()-(getBounds().width()/3.6F)+48, getBounds().exactCenterY()+65, getGrey());
    }
}

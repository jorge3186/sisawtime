package net.jordanalphonso.sisawtime.watchface.minimalt;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.graphics.Typeface;

import net.jordanalphonso.commons.utils.BatteryUtil;
import net.jordanalphonso.commons.utils.TimeUtils;
import net.jordanalphonso.sisawtime.utils.minimalt.MinimaltUtil;
import net.jordanalphonso.sisawtime.watchface.common.WatchFace;
import net.jordanalphonso.sisawtime.watchface.common.WatchFaceBase;

import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * Created by jordan on 11/11/16.
 */

public class MinimaltBackground extends WatchFaceBase implements WatchFace {

    private Float batteryLevel;

    @Override
    public void draw(boolean ambientMode) {
        generateBackgroundGradient(ambientMode);
        generateActiveSecondsStroke(ambientMode);
        generateTickStrokes();
        generateSecondsStrokes();
        generateBatteryLevel(ambientMode);
    }

    private void generateBackgroundGradient(boolean ambientMode) {
        getCanvas().drawColor(Color.BLACK);
        if (!ambientMode) {
            Paint bg = new Paint();
            bg.setColor(Color.BLACK);
            bg.setStyle(Paint.Style.FILL_AND_STROKE);
            bg.setShader(new RadialGradient(getBounds().exactCenterX(), getBounds().exactCenterY(), (float)getRadius().intValue(),
                    MinimaltUtil.getColor(), Color.BLACK, Shader.TileMode.CLAMP));
            bg.setAntiAlias(true);
            bg.setAlpha(60);
            getCanvas().drawCircle(getBounds().exactCenterX(), getBounds().exactCenterY(),
                    (float)getRadius().intValue(), bg);
        }
    }

    private void generateTickStrokes() {
        int angle = 0;
        getCanvas().save();
        for (int i = 0; i < 60; i++) {
            getCanvas().rotate(angle, getBounds().exactCenterX(), getBounds().exactCenterY());
            getCanvas().drawLine(getCenterX(0, (float)0.98)-10, getCenterY(0, (float)0.98),
                    getCenterX(0, (float)0.98), getCenterY(0, (float)0.98), getWhite());
            angle = 6;
        }
        getCanvas().restore();
    }

    private void generateSecondsStrokes() {
        int angle = 0;
        Paint p = new Paint();
        p.setColor(MinimaltUtil.getColor());
        p.setAntiAlias(true);

        getCanvas().save();
        for (int i = 0; i < 12; i++) {
            getCanvas().rotate(angle, getBounds().exactCenterX(), getBounds().exactCenterY());
            getCanvas().drawLine(getCenterX(0, (float)0.90)-15, getCenterY(0, (float)0.90),
                    getCenterX(0, (float)0.90), getCenterY(0, (float)0.90), p);
            angle = 30;
        }
        getCanvas().restore();
    }

    private void generateActiveSecondsStroke(boolean ambientMode) {
        Paint p = new Paint();
        p.setAntiAlias(true);
        p.setColor(MinimaltUtil.getColor());
        p.setStrokeWidth(10F);
        p.setAlpha(100);

        int angle = (TimeUtils.getCurrentSecond(getCalendar()) * 6)-1;
        if (ambientMode) {
            angle = 354;
        }

        for (int i = 0; i < angle+3; i=i+6) {
            getCanvas().save();
            getCanvas().rotate(i+3-90, getBounds().exactCenterX(), getBounds().exactCenterY());
            getCanvas().drawLine(getCenterX(0, (float)0.98)-18, getCenterY(0, 0.98F),
                    getCenterX(0, 0.98F), getCenterY(0, 0.98F), p);
            getCanvas().restore();
        }
    }

    private void generateBatteryLevel(boolean ambientMode) {
        if (!ambientMode) {
            Paint p = new Paint();
            p.setColor(MinimaltUtil.getColor());
            p.setAntiAlias(!ambientMode);
            p.setTextSize(14);
            p.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL));

            getCanvas().save();
            getCanvas().translate(-10,-10);
            getCanvas().drawRect(getBounds().exactCenterX()-5, getBounds().exactCenterY()+82,
                    getBounds().exactCenterX()+5, getBounds().exactCenterY()+97, p);
            getCanvas().drawRect(getBounds().exactCenterX()-2, getBounds().exactCenterY()+79,
                    getBounds().exactCenterX()+2, getBounds().exactCenterY()+85, p);

            String bat = this.batteryLevel == null ? "" : batteryLevel.toString().split("\\.")[0]+"%";
            getCanvas().drawText(bat, getBounds().exactCenterX()+10F,
                    getBounds().exactCenterY()+95F, p);
            getCanvas().restore();
        }
    }

    private float getCenterX(int angle, float percent) {
        return (float)(getBounds().exactCenterX()
                + getRadius(percent) * Math.cos(angle*Math.PI/180));
    }

    private float getCenterY(int angle, float percent) {
        return (float)(getBounds().exactCenterY()
                + getRadius(percent) * Math.sin(angle*Math.PI/180));
    }

    public void setBatteryLevel(Float batteryLevel) {
        this.batteryLevel = batteryLevel;
    }
}

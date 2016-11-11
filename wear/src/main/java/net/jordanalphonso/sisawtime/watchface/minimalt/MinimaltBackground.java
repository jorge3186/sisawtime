package net.jordanalphonso.sisawtime.watchface.minimalt;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;

import net.jordanalphonso.commons.utils.PaintUtils;
import net.jordanalphonso.sisawtime.R;
import net.jordanalphonso.sisawtime.watchface.common.WatchFace;
import net.jordanalphonso.sisawtime.watchface.common.WatchFaceBase;

import java.util.Calendar;

/**
 * Created by jordan on 11/11/16.
 */

public class MinimaltBackground extends WatchFaceBase implements WatchFace {


    @Override
    public void draw() {
        generateBackgroundGradient();
        generateTickStrokes();
        generateNthStrokes();
    }

    private void generateBackgroundGradient() {
        Paint bg = new Paint();
        bg.setColor(Color.BLACK);
        bg.setStyle(Paint.Style.FILL_AND_STROKE);
        bg.setShader(new RadialGradient(getBounds().exactCenterX(), getBounds().exactCenterY(), (float)getRadius().intValue(),
                Color.parseColor("#FFFFBB33"), Color.BLACK, Shader.TileMode.CLAMP));
        bg.setAntiAlias(true);
        bg.setAlpha(60);
        getCanvas().drawColor(Color.BLACK);
        getCanvas().drawCircle(getBounds().exactCenterX(), getBounds().exactCenterY(),
                (float)getRadius().intValue(), bg);

    }

    private void generateTickStrokes() {
        int angle = 0;
        for (int i = 0; i < 60; i++) {
            getCanvas().rotate(angle, getBounds().exactCenterX(), getBounds().exactCenterY());
            getCanvas().drawLine(getCenterX(0, (float)0.98)-10, getCenterY(0, (float)0.98),
                    getCenterX(0, (float)0.98), getCenterY(0, (float)0.98), getWhite());
            angle = 6;
        }
        getCanvas().rotate(angle, getBounds().exactCenterX(), getBounds().exactCenterY());
    }

    private void generateNthStrokes() {
        int angle = 0;
        Paint p = new Paint();
        p.setColor(Color.parseColor("#FFFFBB33"));
        p.setAntiAlias(true);

        for (int i = 0; i < 12; i++) {
            getCanvas().rotate(angle, getBounds().exactCenterX(), getBounds().exactCenterY());
            getCanvas().drawLine(getCenterX(0, (float)0.90)-15, getCenterY(0, (float)0.90),
                    getCenterX(0, (float)0.90), getCenterY(0, (float)0.90), p);
            angle = 30;
        }
        getCanvas().rotate(angle, getBounds().exactCenterX(), getBounds().exactCenterY());
    }

    private float getCenterX(int angle, float percent) {
        return (float)(getBounds().exactCenterX()
                + getRadius(percent) * Math.cos(angle*Math.PI/180));
    }

    private float getCenterY(int angle, float percent) {
        return (float)(getBounds().exactCenterY()
                + getRadius(percent) * Math.sin(angle*Math.PI/180));
    }
}

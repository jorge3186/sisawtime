package net.jordanalphonso.sisawtime.watchface.minimalt;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;

import net.jordanalphonso.sisawtime.watchface.common.WatchFace;
import net.jordanalphonso.sisawtime.watchface.common.WatchFaceBase;

/**
 * Created by jordan on 11/11/16.
 */

public class MinimaltBackground extends WatchFaceBase implements WatchFace {


    @Override
    public void draw() {
        getCanvas().drawColor(Color.BLACK);
        generateTickStrokes();
    }

    private void generateTickStrokes() {

        int angle = 0;
        for (int i = 0; i < 60; i++) {
            int r = 3;
            if (i == 0 || i == 5 || i % 5 == 0) {
                r = 6;
            }

            getCanvas().drawCircle(getCenterX(angle), getCenterY(angle), r, getWhite());
            angle = angle+6;
        }
    }

    private float getCenterX(int angle) {
        return (float)(getBounds().exactCenterX() + getRadius() * Math.sin(angle*Math.PI/180));
    }

    private float getCenterY(int angle) {
        return (float)(getBounds().exactCenterY() + getRadius() * Math.sin(angle*Math.PI/180));
    }
}

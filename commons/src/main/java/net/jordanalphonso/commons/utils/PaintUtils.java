package net.jordanalphonso.commons.utils;

import android.graphics.Paint;

/**
 * Created by jordan.alphonso on 11/11/2016.
 */

public class PaintUtils {

    private PaintUtils() {
        //accessed statically
    }

    public static Paint createSimplePaint(int color) {
        Paint p = new Paint();
        p.setColor(color);
        p.setAntiAlias(true);
        return p;
    }
}

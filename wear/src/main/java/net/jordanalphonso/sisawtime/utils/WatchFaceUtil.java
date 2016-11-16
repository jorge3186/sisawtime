package net.jordanalphonso.sisawtime.utils;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;

import net.jordanalphonso.sisawtime.R;


/**
 * Created by jordan.alphonso on 11/16/2016.
 */

public class WatchFaceUtil {

    private static Resources resources;

    private static String serviceName;

    public static void setResources(Resources resources) {
        WatchFaceUtil.resources = resources;
    }

    public static Resources getResources() {
        return resources;
    }

    public static Drawable getDrawableByName(String name) {
        return null;
    }

    protected void setServiceName(String serviceName) {
        WatchFaceUtil.serviceName = serviceName;
    }

    protected String getServiceName() {
        return serviceName;
    }
}

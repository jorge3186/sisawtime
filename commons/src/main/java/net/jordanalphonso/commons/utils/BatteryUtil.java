package net.jordanalphonso.commons.utils;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;

/**
 * Created by jordan.alphonso on 12/1/2016.
 */

public class BatteryUtil {

    private static final IntentFilter bChangedFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);

    private BatteryUtil() {
        //accessed statically
    }

    public static float getBatteryPercentage(Context context) {
        Intent battIntent = context.registerReceiver(null, bChangedFilter);
        return battIntent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
    }

}

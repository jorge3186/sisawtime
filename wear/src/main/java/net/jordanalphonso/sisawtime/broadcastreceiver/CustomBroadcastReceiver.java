package net.jordanalphonso.sisawtime.broadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.wearable.watchface.CanvasWatchFaceService;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by jordan.alphonso on 11/15/2016.
 */

public class CustomBroadcastReceiver extends BroadcastReceiver {

    private Calendar calendar;

    private CanvasWatchFaceService.Engine engine;

    public CustomBroadcastReceiver(CanvasWatchFaceService.Engine engine, Calendar cal) {
        this.engine = engine;
        this.calendar = cal;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        calendar.setTimeZone(TimeZone.getDefault());
        this.engine.invalidate();
    }
}

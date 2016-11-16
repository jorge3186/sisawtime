package net.jordanalphonso.sisawtime.handler.time;

import android.app.Service;
import android.os.Handler;
import android.os.Message;
import android.support.wearable.watchface.CanvasWatchFaceService;
import android.support.wearable.watchface.WatchFaceService;

/**
 * Created by jordan.alphonso on 11/14/2016.
 */

public class CustomTimeHandler extends Handler {

    private static final int MSG_UPDATE_TIME = 0;

    private long refreshRate;

    private CanvasWatchFaceService.Engine engine;

    public CustomTimeHandler(CanvasWatchFaceService.Engine engine, long refreshRate) {
        this.refreshRate = refreshRate;
        this.engine = engine;
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case MSG_UPDATE_TIME:
                engine.invalidate();
                if (shouldTimerBeRunning()) {
                    long timeMs = System.currentTimeMillis();
                    long delayMs = refreshRate
                            - (timeMs % refreshRate);
                    this.sendEmptyMessageDelayed(MSG_UPDATE_TIME, delayMs);
                }
                break;
        }
    }

    private boolean shouldTimerBeRunning() {
        return this.engine.isVisible() && !this.engine.isInAmbientMode();
    }
}

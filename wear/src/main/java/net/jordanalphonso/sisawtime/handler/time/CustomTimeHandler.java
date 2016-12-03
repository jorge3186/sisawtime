package net.jordanalphonso.sisawtime.handler.time;

import android.app.Service;
import android.os.Handler;
import android.os.Message;
import android.support.wearable.watchface.CanvasWatchFaceService;
import android.support.wearable.watchface.WatchFaceService;
import android.util.Log;

import net.jordanalphonso.commons.exception.WatchFaceException;
import net.jordanalphonso.commons.utils.ReflectionUtil;

/**
 * Created by jordan.alphonso on 11/14/2016.
 */

public class CustomTimeHandler extends Handler {

    private static final int MSG_UPDATE = 0;

    private long refreshRate;

    private String[] methods;

    private CanvasWatchFaceService.Engine engine;

    public CustomTimeHandler(CanvasWatchFaceService.Engine engine, long refreshRate, String[] methods) {
        this.refreshRate = refreshRate;
        this.engine = engine;
        this.methods = methods;
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.what) {
            case MSG_UPDATE:
                executeMethods();
                long timeMs = System.currentTimeMillis();
                long delayMs = refreshRate
                        - (timeMs % refreshRate);
                this.sendEmptyMessageDelayed(MSG_UPDATE, delayMs);
                break;
        }
    }

    private void executeMethods() {
        if (methods == null || methods.length == 0) {
            return;
        }

        for (int i = 0; i < methods.length; i++) {
            try {
                ReflectionUtil.invokeMethod(engine, engine.getClass(), methods[i]);
            } catch (WatchFaceException e) {
                e.printStackTrace();
            }
        }
    }
}

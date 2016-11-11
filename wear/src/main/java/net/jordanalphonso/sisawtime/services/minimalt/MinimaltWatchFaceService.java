package net.jordanalphonso.sisawtime.services.minimalt;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.wearable.watchface.CanvasWatchFaceService;
import android.support.wearable.watchface.WatchFaceStyle;
import android.view.SurfaceHolder;

import net.jordanalphonso.sisawtime.watchface.common.WatchFace;
import net.jordanalphonso.sisawtime.watchface.minimalt.MinimaltMain;

import java.util.Calendar;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

/**
 * Created by jordan on 11/11/16.
 */
public class MinimaltWatchFaceService extends CanvasWatchFaceService {

    @Override
    public Engine onCreateEngine() {
        return new MinimaltEngine();
    }

    private class MinimaltEngine extends CanvasWatchFaceService.Engine {

        private final long TICK_PERIOD_MILLIS = TimeUnit.SECONDS.toMillis(1);

        private final int MSG_UPDATE_TIME = 0;

        private Calendar calendar;

        private boolean registeredTimeZoneReceiver;

        final Handler updateTimeHandler = new Handler() {
            @Override
            public void handleMessage(Message message) {
                switch (message.what) {
                    case MSG_UPDATE_TIME:
                        invalidate();
                        if (shouldTimerBeRunning()) {
                            long timeMs = System.currentTimeMillis();
                            long delayMs = TICK_PERIOD_MILLIS
                                    - (timeMs % TICK_PERIOD_MILLIS);
                            updateTimeHandler
                                    .sendEmptyMessageDelayed(MSG_UPDATE_TIME, delayMs);
                        }
                        break;
                }
            }
        };

        final BroadcastReceiver timeZoneReciever = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                calendar.setTimeZone(TimeZone.getDefault());
                invalidate();
            }
        };

        @Override
        public void onCreate(SurfaceHolder holder) {
            super.onCreate(holder);
            calendar = Calendar.getInstance();

            setWatchFaceStyle(new WatchFaceStyle.Builder(MinimaltWatchFaceService.this)
                    .setAcceptsTapEvents(true)
                    .setHideStatusBar(true)
                    .setShowUnreadCountIndicator(true)
                    .setCardProgressMode(WatchFaceStyle.PROGRESS_MODE_DISPLAY)
                    .setStatusBarGravity(WatchFaceStyle.PROTECT_STATUS_BAR)
                    .build());
        }

        @Override
        public void onDraw(Canvas canvas, Rect bounds) {
            super.onDraw(canvas, bounds);
            WatchFace minimalt = new MinimaltMain();
            minimalt.initialize(canvas, bounds, calendar);
            minimalt.draw();
        }

        @Override
        public void onTimeTick() {
            super.onTimeTick();
            invalidate();
        }

        private void updateTimer() {
            updateTimeHandler.removeMessages(MSG_UPDATE_TIME);
            if (shouldTimerBeRunning()) {
                updateTimeHandler.sendEmptyMessage(MSG_UPDATE_TIME);
            }
        }

        private boolean shouldTimerBeRunning() {
            return isVisible() && !isInAmbientMode();
        }

        @Override
        public void onVisibilityChanged(boolean visible) {
            super.onVisibilityChanged(visible);
            if (visible) {
                registerReceiver();
                calendar.setTimeZone(TimeZone.getDefault());
            } else {
                unregisterReceiver();
            }
            updateTimer();
        }

        private void registerReceiver() {
            if (registeredTimeZoneReceiver) {
                return;
            }
            registeredTimeZoneReceiver = true;
            IntentFilter filter = new IntentFilter(Intent.ACTION_TIMEZONE_CHANGED);
            MinimaltWatchFaceService.this.registerReceiver(timeZoneReciever, filter);
        }

        private void unregisterReceiver() {
            if (!registeredTimeZoneReceiver) {
                return;
            }
            registeredTimeZoneReceiver = false;
            MinimaltWatchFaceService.this.unregisterReceiver(timeZoneReciever);
        }
    }
}

package net.jordanalphonso.sisawtime.services.minimalt;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.wearable.watchface.CanvasWatchFaceService;
import android.support.wearable.watchface.WatchFaceStyle;
import android.view.SurfaceHolder;

import net.jordanalphonso.sisawtime.broadcastreceiver.CustomBroadcastReceiver;
import net.jordanalphonso.sisawtime.handler.time.CustomTimeHandler;
import net.jordanalphonso.sisawtime.utils.minimalt.MinimaltUtil;
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

        private final long REFRESH_RATE_MILLIS = TimeUnit.MILLISECONDS.toMillis(25);

        private final int MSG_UPDATE_TIME = 0;

        private CustomTimeHandler timeHandler;

        private CustomBroadcastReceiver timeZoneReciever;

        private Calendar calendar;

        private boolean registeredTimeZoneReceiver;

        private MinimaltMain minimalt;

        @Override
        public void onCreate(SurfaceHolder holder) {
            super.onCreate(holder);
            calendar = Calendar.getInstance();
            timeHandler = new CustomTimeHandler(this, REFRESH_RATE_MILLIS);
            timeZoneReciever = new CustomBroadcastReceiver(this, calendar);
            MinimaltUtil.setResources(getResources());

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
            minimalt = new MinimaltMain();
            minimalt.initialize(canvas, bounds, calendar);
            minimalt.draw(isInAmbientMode());
        }

        @Override
        public void onTimeTick() {
            super.onTimeTick();
            invalidate();
        }

        private void updateTimer() {
            timeHandler.removeMessages(MSG_UPDATE_TIME);
            if (shouldTimerBeRunning()) {
                timeHandler.sendEmptyMessage(MSG_UPDATE_TIME);
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

        @Override
        public void onTapCommand(int tapType, int x, int y, long eventTime) {
            if (MinimaltWatchFaceService.TAP_TYPE_TAP == tapType) {
                MinimaltUtil.changeColor();
                invalidate();
            } else {
                super.onTapCommand(tapType, x, y, eventTime);
            }
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            timeHandler.removeMessages(MSG_UPDATE_TIME);
        }
    }
}

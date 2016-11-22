package net.jordanalphonso.sisawtime.services.minimalt;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.wearable.watchface.CanvasWatchFaceService;
import android.support.wearable.watchface.WatchFaceStyle;
import android.util.Log;
import android.view.SurfaceHolder;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.fitness.result.DailyTotalResult;

import net.jordanalphonso.sisawtime.broadcastreceiver.CustomBroadcastReceiver;
import net.jordanalphonso.sisawtime.handler.time.CustomTimeHandler;
import net.jordanalphonso.sisawtime.utils.minimalt.MinimaltUtil;
import net.jordanalphonso.sisawtime.watchface.minimalt.MinimaltMain;

import java.util.Calendar;
import java.util.List;
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

    private class MinimaltEngine extends CanvasWatchFaceService.Engine implements
            GoogleApiClient.ConnectionCallbacks,
            GoogleApiClient.OnConnectionFailedListener,
            ResultCallback<DailyTotalResult> {
        private GoogleApiClient googleApiClient;

        private final long REFRESH_RATE_MILLIS = TimeUnit.MILLISECONDS.toMillis(40);

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
            minimalt = new MinimaltMain();
            MinimaltUtil.setResources(getResources());

            googleApiClient = new GoogleApiClient.Builder(MinimaltWatchFaceService.this)
                    .addConnectionCallbacks(this)
                    .addApi(Fitness.HISTORY_API)
                    .useDefaultAccount()
                    .build();

            setWatchFaceStyle(new WatchFaceStyle.Builder(MinimaltWatchFaceService.this)
                    .setAcceptsTapEvents(true)
                    .setShowUnreadCountIndicator(false)
                    .setCardProgressMode(WatchFaceStyle.PROGRESS_MODE_DISPLAY)
                    .setStatusBarGravity(WatchFaceStyle.PROTECT_STATUS_BAR)
                    .build());
        }

        @Override
        public void onDraw(Canvas canvas, Rect bounds) {
            super.onDraw(canvas, bounds);
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
                googleApiClient.connect();
                registerReceiver();
                calendar.setTimeZone(TimeZone.getDefault());
            } else {
                unregisterReceiver();
                if (googleApiClient != null && googleApiClient.isConnected()) {
                    googleApiClient.disconnect();
                }
            }
            updateTimer();
        }

        @Override
        public void onAmbientModeChanged(boolean inAmbientMode) {
            super.onAmbientModeChanged(inAmbientMode);
            if (inAmbientMode) {
                invalidate();
                timeHandler.removeMessages(MSG_UPDATE_TIME);
            } else {
                invalidate();
                timeHandler.sendEmptyMessage(MSG_UPDATE_TIME);
            }
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

        private void getStepsCount() {
            if (googleApiClient != null && googleApiClient.isConnected()) {
                PendingResult<DailyTotalResult> pendingSteps = Fitness.HistoryApi.readDailyTotal(
                        googleApiClient, DataType.TYPE_STEP_COUNT_DELTA);
                pendingSteps.setResultCallback(this);
            }
        }

        private void getStepsGoal() {
            if (googleApiClient != null && googleApiClient.isConnected()) {
                PendingResult<DailyTotalResult> pendingSteps = Fitness.HistoryApi.readDailyTotal(
                        googleApiClient, DataType.TYPE_STEP_COUNT_DELTA);
                pendingSteps.setResultCallback(this);
            }
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

        @Override
        public void onConnected(@Nullable Bundle bundle) {
            subscribeToSteps();
            getStepsCount();
        }

        @Override
        public void onConnectionSuspended(int i) {
            Log.d("Service", "Connection Suspended: "+i);
        }

        private void subscribeToSteps() {
            Fitness.RecordingApi.subscribe(
                    googleApiClient, DataType.TYPE_STEP_COUNT_DELTA);
        }

        @Override
        public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
            Log.d("Service", "Connection Failed");
            Log.d("Service", "Error: "+connectionResult.getErrorMessage());
        }

        @Override
        public void onResult(@NonNull DailyTotalResult dailyTotalResult) {
            if (dailyTotalResult.getStatus().isSuccess()) {
                List<DataPoint> points = dailyTotalResult.getTotal().getDataPoints();
                if (!points.isEmpty()) {
                    MinimaltUtil.updateStepCount(points.get(0).getValue(Field.FIELD_STEPS).asInt());
                }
            }
        }
    }
}

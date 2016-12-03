package net.jordanalphonso.sisawtime.services.minimalt;

import android.Manifest;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.wearable.watchface.CanvasWatchFaceService;
import android.support.wearable.watchface.WatchFaceStyle;
import android.util.Log;
import android.view.SurfaceHolder;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.fitness.result.DailyTotalResult;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.wearable.Wearable;

import net.jordanalphonso.commons.utils.BatteryUtil;
import net.jordanalphonso.sisawservices.model.openweathermap.DailyWeather;
import net.jordanalphonso.sisawservices.service.weather.WeatherService;
import net.jordanalphonso.sisawtime.broadcastreceiver.CustomBroadcastReceiver;
import net.jordanalphonso.sisawtime.handler.time.CustomTimeHandler;
import net.jordanalphonso.sisawtime.utils.minimalt.MinimaltUtil;
import net.jordanalphonso.sisawtime.utils.permission.PermissionUtil;
import net.jordanalphonso.sisawtime.watchface.minimalt.MinimaltMain;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.ExecutionException;
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
            ResultCallback<DailyTotalResult>,
            LocationListener {

        private final String[] LOCATION_REQUESTS = new String[] {
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION};

        private GoogleApiClient googleApiClient;

        private final long REFRESH_RATE_MILLIS = TimeUnit.MILLISECONDS.toMillis(33);

        private final long GOOGLE_API_CALL_MILLIS = TimeUnit.MINUTES.toMillis(30);

        private final int MSG_UPDATE_TIME = 0;

        private CustomTimeHandler timeHandler;

        private CustomTimeHandler googleApiHandler;

        private CustomBroadcastReceiver timeZoneReceiver;

        private Calendar calendar;

        private boolean registeredTimeZoneReceiver;

        private MinimaltMain minimalt;

        private WeatherService weatherService;

        @Override
        public void onCreate(SurfaceHolder holder) {
            super.onCreate(holder);
            calendar = Calendar.getInstance();
            timeHandler = new CustomTimeHandler(this, REFRESH_RATE_MILLIS, new String[] {"invalidate"});
            googleApiHandler = new CustomTimeHandler(this, GOOGLE_API_CALL_MILLIS,
                    new String[] {"getStepsCount", "getLocation"});
            timeZoneReceiver = new CustomBroadcastReceiver(this, calendar);
            minimalt = new MinimaltMain();
            MinimaltUtil.setResources(getResources());
            weatherService = new WeatherService();

            googleApiClient = new GoogleApiClient.Builder(MinimaltWatchFaceService.this)
                    .addConnectionCallbacks(this)
                    .addApi(Fitness.HISTORY_API)
                    .addApi(Fitness.RECORDING_API)
                    .addApi(Wearable.API)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .useDefaultAccount()
                    .build();

            setWatchFaceStyle(new WatchFaceStyle.Builder(MinimaltWatchFaceService.this)
                    .setAcceptsTapEvents(true)
                    .setShowUnreadCountIndicator(false)
                    .build());
        }

        @Override
        public void onDraw(Canvas canvas, Rect bounds) {
            super.onDraw(canvas, bounds);
            minimalt.initialize(canvas, bounds, calendar);
            minimalt.setBatteryLevel(BatteryUtil.getBatteryPercentage(MinimaltWatchFaceService.this));
            minimalt.draw(isInAmbientMode());
        }

        @Override
        public void onTimeTick() {
            super.onTimeTick();
            invalidate();
        }

        private void updateTimers() {
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
                googleApiHandler.sendEmptyMessage(MSG_UPDATE_TIME);
                getStepsCount();
                getLocation();
                registerReceiver();
                calendar.setTimeZone(TimeZone.getDefault());
            } else {
                unregisterReceiver();
                if (googleApiClient != null && googleApiClient.isConnected()) {
                    LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
                    googleApiClient.disconnect();
                    googleApiHandler.removeMessages(MSG_UPDATE_TIME);
                }
            }
            updateTimers();
        }

        @Override
        public void onAmbientModeChanged(boolean inAmbientMode) {
            super.onAmbientModeChanged(inAmbientMode);
            if (inAmbientMode) {
                invalidate();
            } else {
                if (!googleApiClient.isConnected()) {
                    googleApiClient.connect();
                }
                getLocation();
                getStepsCount();
                invalidate();
            }
            updateTimers();
        }

        private void registerReceiver() {
            if (registeredTimeZoneReceiver) {
                return;
            }
            registeredTimeZoneReceiver = true;
            IntentFilter filter = new IntentFilter(Intent.ACTION_TIMEZONE_CHANGED);
            MinimaltWatchFaceService.this.registerReceiver(timeZoneReceiver, filter);
        }

        private void unregisterReceiver() {
            if (!registeredTimeZoneReceiver) {
                return;
            }
            registeredTimeZoneReceiver = false;
            MinimaltWatchFaceService.this.unregisterReceiver(timeZoneReceiver);
        }

        private void getStepsCount() {
            if (googleApiClient != null && googleApiClient.isConnected()) {
                PendingResult<DailyTotalResult> pendingSteps = Fitness.HistoryApi.readDailyTotal(
                        googleApiClient, DataType.TYPE_STEP_COUNT_DELTA);
                pendingSteps.setResultCallback(this);
                Log.i("Steps", "Successfully retrieved steps count.");
            } else {
                Log.i("Google", "Google API is not connected when retrieving steps.");
            }
        }

        private void requestLocationUpdates() {
            if (MinimaltUtil.isAskForLocations() && !MinimaltUtil.isLocationsGranted()){
                PermissionUtil.requestPermissions(getApplicationContext(), LOCATION_REQUESTS);
            }

            if (googleApiClient != null & googleApiClient.isConnected()
                    && MinimaltUtil.isLocationsGranted()) {
                LocationRequest locationRequest = LocationRequest.create()
                        .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                        .setInterval(GOOGLE_API_CALL_MILLIS)
                        .setFastestInterval(GOOGLE_API_CALL_MILLIS);
                try {
                    LocationServices.FusedLocationApi.requestLocationUpdates(
                            googleApiClient, locationRequest, this);
                    Log.i("Location", "Successfully requested location updates.");
                } catch (SecurityException e) {
                    Log.e("Location", "Error retrieving location");
                    e.printStackTrace();
                }
            }
        }

        private void getLocation() {
            if (MinimaltUtil.isAskForLocations() && !MinimaltUtil.isLocationsGranted()){
                PermissionUtil.requestPermissions(MinimaltWatchFaceService.this, LOCATION_REQUESTS);
            }

            try {
                if (googleApiClient != null && googleApiClient.isConnected()
                        && MinimaltUtil.isLocationsGranted()) {
                    Location loc = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
                    onLocationChanged(loc);
                    Log.i("Location", "Successfully retrieved location.");
                } else {
                    Log.i("Google", "Google API is not connected when retrieving location.");
                }
            } catch (SecurityException e) {
                Log.e("Location", "Error retrieving location");
                e.printStackTrace();
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
            googleApiHandler.removeMessages(MSG_UPDATE_TIME);
            if (googleApiClient.isConnected()) {
                LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
                googleApiClient.disconnect();
            }
        }

        @Override
        public void onConnected(@Nullable Bundle bundle) {
            subscribeToSteps();
            requestLocationUpdates();
            Log.i("Google", "Successfully connected to google APIs");
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
                    Log.i("Steps", "Getting steps count");
                }
            }
        }

        @Override
        public void onLocationChanged(Location location) {
            if (location != null) {
                DailyWeather weather = weatherService.getResult(location.getLongitude(), location.getLatitude());
                MinimaltUtil.updateWeather(weather);
                Log.d("Weather", "Location is "+location.getLatitude()+"::"+location.getLongitude());
                Log.d("Weather", "Temp is "+weather.getMain().getTemp());
            } else {
                Log.i("Weather", "Location is null");
            }
        }
    }
}

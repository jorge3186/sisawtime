package net.jordanalphonso.sisawtime.services.minimalt;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.wearable.watchface.CanvasWatchFaceService;
import android.support.wearable.watchface.WatchFaceStyle;
import android.view.SurfaceHolder;

import net.jordanalphonso.sisawtime.watchface.common.WatchFace;
import net.jordanalphonso.sisawtime.watchface.minimalt.MinimaltMain;

/**
 * Created by jordan on 11/11/16.
 */
public class MinimaltWatchFaceService extends CanvasWatchFaceService {

    @Override
    public Engine onCreateEngine() {
        return new MinimaltEngine();
    }

    private class MinimaltEngine extends CanvasWatchFaceService.Engine {

        @Override
        public void onCreate(SurfaceHolder holder) {
            super.onCreate(holder);

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
            minimalt.initialize(canvas, bounds);
            minimalt.draw();
        }
    }
}

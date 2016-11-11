package net.jordanalphonso.sisawtime.services.minimalt;

import android.support.wearable.watchface.CanvasWatchFaceService;
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

            WatchFace minimaltWatchFace = new MinimaltMain();
        }
    }
}

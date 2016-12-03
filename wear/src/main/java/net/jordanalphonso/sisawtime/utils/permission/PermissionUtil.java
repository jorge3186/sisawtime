package net.jordanalphonso.sisawtime.utils.permission;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

import net.jordanalphonso.sisawtime.activity.common.EmptyPermissionActivity;

/**
 * Created by jordan.alphonso on 11/28/2016.
 */

public class PermissionUtil {

    private PermissionUtil() {
        //accessed statically
    }

    public static boolean hasPermissions(@NonNull Context context, @NonNull String[] permissions) {
        boolean granted = true;

        if (permissions == null || permissions.length == 0) {
            return false;
        }

        for (int i = 0; i < permissions.length; i++) {
            if (ActivityCompat.checkSelfPermission(context, permissions[i])
                    != PackageManager.PERMISSION_GRANTED) {
                granted = false;
            }
        }
        return granted;
    }

    public static <T extends Service> void requestPermissions(@NonNull Context context, @NonNull String[] permissions) {
        if (permissions == null || permissions.length == 0) {
            return;
        }

        Intent intent = generateIntent(context);
        intent.putExtra("permissions", permissions);
        context.startActivity(intent);
    }

    private static Intent generateIntent(Context context) {
        Intent intent = new Intent(context.getApplicationContext(), EmptyPermissionActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }

}

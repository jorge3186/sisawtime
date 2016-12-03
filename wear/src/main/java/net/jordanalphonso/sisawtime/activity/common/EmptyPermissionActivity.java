package net.jordanalphonso.sisawtime.activity.common;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.wearable.activity.WearableActivity;

import net.jordanalphonso.sisawtime.utils.minimalt.MinimaltUtil;

/**
 * Created by jordan.alphonso on 11/28/2016.
 */

public class EmptyPermissionActivity extends WearableActivity {

    private String[] permissions;

    private int GRANTED_LOC = 0;

    @Override
    protected void onStart() {
        super.onStart();

        this.permissions = getIntent().getExtras().getStringArray("permissions");
        ActivityCompat.requestPermissions(this, permissions, GRANTED_LOC);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if ((requestCode == 0)
            && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
            MinimaltUtil.updatedLocationsGranted(true);
        } else {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[0])) {
                MinimaltUtil.setAskForLocations(false);
            } else {
                MinimaltUtil.setAskForLocations(true);
            }
            MinimaltUtil.updatedLocationsGranted(false);
        }
        finish();
    }
}

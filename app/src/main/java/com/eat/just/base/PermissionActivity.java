package com.eat.just.base;

import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

/**
 */

public abstract class PermissionActivity extends AppCompatActivity {
    protected abstract String[] getPermissions();

    protected abstract void onPermissionGranted();

    protected abstract void onPermissionDenied(String[] permission);

    private static final int RETURN_FROM_PERMISSION = 0x1;


    protected boolean isPermissionGranted() {
        String[] permissions = getPermissions();
        if (permissions == null)
            return true;
        for (String permission : permissions)
            if (ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED)
                return false;
        return true;
    }

    protected void requestPermission() {
        String[] permission = getPermissions();
        if (permission == null || permission.length == 0)
            return;
        ActivityCompat.requestPermissions(this, permission, RETURN_FROM_PERMISSION);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == RETURN_FROM_PERMISSION) {
            if (grantResults.length <= 0) {
                onPermissionDenied(getPermissions());
                return;
            }
            List<String> permissionDeniedList = new ArrayList<>();
            for (int i = 0; i < grantResults.length; ++i) {
                if (grantResults[i] == PackageManager.PERMISSION_DENIED)
                    permissionDeniedList.add(permissions[i]);
            }
            if (permissionDeniedList.size() == 0)
                onPermissionGranted();
            else
                onPermissionDenied(permissionDeniedList.toArray(new String[permissionDeniedList.size()]));

        }
    }
}

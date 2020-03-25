package darthleonard.archaos.qreader.services;

import android.Manifest;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import darthleonard.archaos.qreader.builders.PromptBuilder;
import darthleonard.archaos.qreader.interfaces.IPermissionHandler;
import darthleonard.archaos.qreader.MainActivity;
import darthleonard.archaos.qreader.R;

public class PermissionHandler implements IPermissionHandler {
    public boolean CheckPermissions(MainActivity mainActivity) {
        if (ContextCompat.checkSelfPermission(mainActivity, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            requestCameraPermission(mainActivity);
            return false;
        }
    }

    public void RequestPermissions(MainActivity mainActivity) {
        ActivityCompat.requestPermissions(
                mainActivity,
                new String[]{ Manifest.permission.CAMERA },
                mainActivity.CAMERA_PERMISSIONS_REQUEST);
    }

    private void requestCameraPermission(MainActivity mainActivity) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(mainActivity,
                Manifest.permission.CAMERA)) {
            showPrompt(mainActivity);
        } else {
            RequestPermissions(mainActivity);
        }
    }

    private void showPrompt(MainActivity mainActivity) {
        new PromptBuilder().Create(mainActivity, R.string.permissionPromptMessage, this);
    }
}

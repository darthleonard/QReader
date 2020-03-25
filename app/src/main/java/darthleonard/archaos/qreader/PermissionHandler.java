package darthleonard.archaos.qreader;

import android.Manifest;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

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
            showPrompt(mainActivity, R.string.permissionPromptMessage);
        } else {
            RequestPermissions(mainActivity);
        }
    }

    private void showPrompt(MainActivity mainActivity, int messageId) {
        new PromptBuilder().Create(mainActivity, messageId, this);
    }
}

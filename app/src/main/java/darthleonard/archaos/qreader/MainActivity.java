package darthleonard.archaos.qreader;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.webkit.URLUtil;
import android.widget.TextView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private final int CAMERA_PERMISSIONS_REQUEST = 1;
    private BarcodeDetector barcodeDetector;
    private CameraSource cameraSource;
    private BarcodeDetectorProcessor barcodeDetectorProcessor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkPermissions();
    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        if(menu instanceof MenuBuilder){
            MenuBuilder m = (MenuBuilder) menu;
            m.setOptionalIconsVisible(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_open_always:
                return true;
            case R.id.menu_reset:
                return true;
            case R.id.menu_copy:
                return true;
            case R.id.menu_open:
                new TokenHandler().OpenToken(getApplicationContext(), barcodeDetectorProcessor.getToken());
                return true;
            case R.id.menu_list:
                return true;
            case R.id.menu_save:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            initComponents();
        } else {
            TextView tv = findViewById(R.id.tvToken);
            tv.setText(R.string.permissionPromptSadMessage);
        }
    }

    private void checkPermissions() {
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            initComponents();
        } else {
            requestCameraPermission();
        }
    }

    private void requestCameraPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                Manifest.permission.CAMERA)) {
            showPrompt(R.string.permissionPromptMessage);
        } else {
            requestPermissions();
        }
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{ Manifest.permission.CAMERA },
                CAMERA_PERMISSIONS_REQUEST);
    }

    private void showPrompt(int messageId) {
        new AlertDialog.Builder(MainActivity.this)
                .setTitle(R.string.permissionPromptTitle)
                .setMessage(messageId)
                .setNeutralButton(R.string.permissionButtonText, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        requestPermissions();
                    }
                })
                .create()
                .show();
    }

    private void initComponents() {
        setContentView(R.layout.activity_main);
        barcodeDetector = new BarcodeDetector.Builder(getApplicationContext())
                .setBarcodeFormats(Barcode.QR_CODE)
                .build();
        cameraSource = new CameraSource.Builder(getApplicationContext(), barcodeDetector)
                .setRequestedPreviewSize(800, 600)
                .build();
        new CameraHelper().Configure((SurfaceView) findViewById(R.id.svCamera), cameraSource);
        barcodeDetectorProcessor = new BarcodeDetectorProcessor(this, barcodeDetector);
        barcodeDetectorProcessor.Start();
    }
}

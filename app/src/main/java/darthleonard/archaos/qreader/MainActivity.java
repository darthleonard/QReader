package darthleonard.archaos.qreader;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.MenuBuilder;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceView;
import android.widget.TextView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import darthleonard.archaos.qreader.services.BarcodeDetectorProcessor;
import darthleonard.archaos.qreader.services.CameraHelper;
import darthleonard.archaos.qreader.services.PermissionHandler;
import darthleonard.archaos.qreader.services.TokenHandler;

public class MainActivity extends AppCompatActivity {
    public final int CAMERA_PERMISSIONS_REQUEST = 1;
    private BarcodeDetectorProcessor barcodeDetectorProcessor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(new PermissionHandler().CheckPermissions(this)) {
            initComponents();
        }
    }

    @SuppressLint("RestrictedApi")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        if(menu instanceof MenuBuilder) {
            MenuBuilder m = (MenuBuilder) menu;
            m.setOptionalIconsVisible(true);
        }
        menu.getItem(0).setChecked(
                getSharedPreferences("preferences", 0)
                        .getBoolean("OpenAlways", false));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_open_always:
                boolean isChecked = !item.isChecked();
                item.setChecked(isChecked);
                SharedPreferences settings = getSharedPreferences("preferences", 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.putBoolean(
                        "OpenAlways",
                        settings.getBoolean("OpenAlways", isChecked));
                editor.apply();
                return true;
            case R.id.menu_reset:
                TextView tv = findViewById(R.id.tvToken);
                tv.setText(R.string.token_default);
                return true;
            case R.id.menu_copy:
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("qr code", barcodeDetectorProcessor.getToken());
                assert clipboard != null;
                clipboard.setPrimaryClip(clip);
                return true;
            case R.id.menu_open:
                new TokenHandler().OpenToken(getApplicationContext(), barcodeDetectorProcessor.getToken());
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            initComponents();
        } else {
            setContentView(R.layout.activity_main);
            TextView tv = findViewById(R.id.tvToken);
            tv.setText(R.string.permissionPromptSadMessage);
        }
    }

    private void initComponents() {
        setContentView(R.layout.activity_main);
        BarcodeDetector barcodeDetector = new BarcodeDetector.Builder(getApplicationContext())
                .setBarcodeFormats(Barcode.QR_CODE)
                .build();
        CameraSource cameraSource = new CameraSource.Builder(getApplicationContext(), barcodeDetector)
                .setRequestedPreviewSize(800, 600)
                .build();
        new CameraHelper().Configure((SurfaceView) findViewById(R.id.svCamera), cameraSource);
        barcodeDetectorProcessor = new BarcodeDetectorProcessor(this, barcodeDetector);
        barcodeDetectorProcessor.Start();
    }
}

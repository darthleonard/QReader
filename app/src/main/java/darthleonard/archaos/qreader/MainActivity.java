package darthleonard.archaos.qreader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private final int CAMERA_PERMISSIONS_REQUEST = 1;
    private BarcodeDetector barcodeDetector;
    private CameraSource cameraSource;
    private String token = "currentToken";
    private String previousToken = "previousToken";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
        configureCamera();

        // preparo el detector de QR
        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if (barcodes.size() > 0) {
                    token = barcodes.valueAt(0).displayValue.toString();
                    if (!token.equals(previousToken)) {
                        previousToken = token;
                        TextView tv = findViewById(R.id.tvToken);
                        tv.setText(token);
                    }
                }
            }
        });
    }

    private void initComponents() {
        // creo el detector qr
        barcodeDetector = new BarcodeDetector.Builder(getApplicationContext())
                .setBarcodeFormats(Barcode.QR_CODE)
                .build();

        // creo la camara fuente
        cameraSource = new CameraSource.Builder(getApplicationContext(), barcodeDetector)
                .setRequestedPreviewSize(640, 480)
                .build();
    }

    private void configureCamera() {
        final SurfaceView cameraView = (SurfaceView) findViewById(R.id.svCamera);
        cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
                            requestPermissions(new String[]{Manifest.permission.CAMERA},
                                    CAMERA_PERMISSIONS_REQUEST);
                        }
                    }
                    return;
                } else {
                    try {
                        cameraSource.start(cameraView.getHolder());
                    } catch (IOException ie) {
                        Log.e("CAMERA SOURCE", ie.getMessage());
                    }
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });
    }
}

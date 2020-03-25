package darthleonard.archaos.qreader.services;

import android.util.SparseArray;
import android.widget.TextView;

import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import darthleonard.archaos.qreader.MainActivity;
import darthleonard.archaos.qreader.R;

public class BarcodeDetectorProcessor {
    private MainActivity mainActivity;
    private BarcodeDetector barcodeDetector;
    private String token = "token";
    private String previousToken = "previousToken";

    public BarcodeDetectorProcessor(final MainActivity mainActivity, BarcodeDetector barcodeDetector) {
        this.mainActivity = mainActivity;
        this.barcodeDetector = barcodeDetector;
    }

    public void Start() {
        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> code = detections.getDetectedItems();
                if (code.size() == 0) {
                    return;
                }
                token = code.valueAt(0).displayValue;
                if (!token.equals(previousToken)) {
                    previousToken = token;
                    TextView tv = mainActivity.findViewById(R.id.tvToken);
                    tv.setText(token);
                }
            }
        });
    }

    public String getToken() {
        return token;
    }
}

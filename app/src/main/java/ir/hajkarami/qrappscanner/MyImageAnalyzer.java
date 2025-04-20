package ir.hajkarami.qrappscanner;

import android.annotation.SuppressLint;
import android.graphics.Point;
import android.graphics.Rect;
import android.media.Image;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;
import androidx.fragment.app.FragmentManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.barcode.BarcodeScanner;
import com.google.mlkit.vision.barcode.BarcodeScannerOptions;
import com.google.mlkit.vision.barcode.BarcodeScanning;
import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.common.InputImage;

import java.util.List;

public class MyImageAnalyzer implements ImageAnalysis.Analyzer {

    private final FragmentManager mFragmentManager;
    private final BottomDialog mBottomDialog;
    private boolean isScanned = false;

    public MyImageAnalyzer(FragmentManager fragmentManager) {
        this.mFragmentManager = fragmentManager;
        this.mBottomDialog = new BottomDialog();
    }

    @Override
    public void analyze(@NonNull ImageProxy image) {
        if (!isScanned) {
            scanBarCode(image);
        } else {
            image.close();
        }
    }

    private void scanBarCode(ImageProxy image) {
        @SuppressLint("UnsafeOptInUsageError")
        Image mediaImage = image.getImage();

        if (mediaImage != null) {
            InputImage inputImage = InputImage.fromMediaImage(mediaImage, image.getImageInfo().getRotationDegrees());

            BarcodeScannerOptions options = new BarcodeScannerOptions.Builder()
                    .setBarcodeFormats(Barcode.FORMAT_QR_CODE, Barcode.FORMAT_AZTEC)
                    .build();

            BarcodeScanner scanner = BarcodeScanning.getClient(options);

            scanner.process(inputImage)
                    .addOnSuccessListener(new OnSuccessListener<List<Barcode>>() {
                        @Override
                        public void onSuccess(List<Barcode> barcodes) {
                            if (!barcodes.isEmpty()) {
                                isScanned = true;
                                readBarcodeData(barcodes);
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            if (mBottomDialog.getActivity() != null) {
                                Toast.makeText(mBottomDialog.getActivity(), "Failed to read code", Toast.LENGTH_SHORT).show();
                            }
                        }
                    })
                    .addOnCompleteListener(new OnCompleteListener<List<Barcode>>() {
                        @Override
                        public void onComplete(@NonNull Task<List<Barcode>> task) {
                            image.close();
                        }
                    });
        } else {
            image.close();
        }
    }

    private void readBarcodeData(List<Barcode> barcodes) {
        for (Barcode barcode : barcodes) {
            String rawValue = barcode.getRawValue();
            if (rawValue == null) continue;

            int valueType = barcode.getValueType();

            switch (valueType) {
                case Barcode.TYPE_WIFI:
                    if (barcode.getWifi() != null) {
                        String ssid = barcode.getWifi().getSsid();
                        String password = barcode.getWifi().getPassword();
                        int type = barcode.getWifi().getEncryptionType();
                    }
                    break;

                case Barcode.TYPE_URL:
                    if (!mBottomDialog.isAdded()) {
                        mBottomDialog.fetchUrl(rawValue);
                        mBottomDialog.show(mFragmentManager, "");
                    }
                    break;

                default:
                    if (!mBottomDialog.isAdded()) {
                        mBottomDialog.fetchUrl(rawValue);
                        mBottomDialog.show(mFragmentManager, "");
                    }
                    break;
            }
            break;
        }
    }
}

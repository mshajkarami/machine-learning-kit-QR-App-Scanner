package ir.hajkarami.qrappscanner;

import android.annotation.SuppressLint;
import android.graphics.Matrix;
import android.media.Image;
import android.util.Size;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
    private FragmentManager mFragmentManager;
    private BottomDialog mBottomDialog;

    public MyImageAnalyzer(FragmentManager supportFragmentManager) {

        this.mFragmentManager = supportFragmentManager;
        mBottomDialog = new BottomDialog();
    }

    @Override
    public void analyze(@NonNull ImageProxy image) {
        ScanBarCode(image);
    }

    private void ScanBarCode(ImageProxy image) {
        @SuppressLint("UnsafeOptInUsageError") Image image1 = image.getImage();
        assert image1 != null;
        InputImage inputImage = InputImage.fromMediaImage(image1, image.getImageInfo().getRotationDegrees());
        BarcodeScannerOptions scannerOptions = new BarcodeScannerOptions.Builder()
                .setBarcodeFormats(Barcode.FORMAT_QR_CODE,Barcode.FORMAT_AZTEC)
                .build();
        BarcodeScanner barcodeScanner = BarcodeScanning.getClient(scannerOptions);
        Task<List<Barcode>> result = barcodeScanner.process(inputImage)
                .addOnSuccessListener(new OnSuccessListener<List<Barcode>>() {
                    @Override
                    public void onSuccess(List<Barcode> barcodes) {
                        ReaderBarCodeData(barcodes);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(mBottomDialog.getActivity(),"Failed to read code", Toast.LENGTH_SHORT).show();
                    }
                }).addOnCompleteListener(new OnCompleteListener<List<Barcode>>() {
                    @Override
                    public void onComplete(@NonNull Task<List<Barcode>> task) {
                        image.close();
                    }
                });

    }

    private void ReaderBarCodeData(List<Barcode> barcodes) {

    }

}

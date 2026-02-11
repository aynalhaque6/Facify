package com.example.facedetection;

import android.Manifest;
import android.os.Bundle;
import android.util.Log;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.aynal.facify.sdk.FacifyCallback;
import com.aynal.facify.sdk.FacifyConfig;
import com.aynal.facify.sdk.FacifyDesign;
import com.aynal.facify.sdk.FacifyState;
import com.aynal.facify.sdk.FacifyView;

public class VerificationActivity extends AppCompatActivity {

    private FacifyView facifyView;

    private final ActivityResultLauncher<String> camPerm = registerForActivityResult(new ActivityResultContracts.RequestPermission(), granted -> {
        if (granted && facifyView != null) {
            facifyView.retry();
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_verification);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        facifyView = findViewById(R.id.facifyView);

        FacifyConfig config = new FacifyConfig.Builder()
                .setTotalSteps(6)
                .setShowDots(true)
                .setShowStatusText(true)
                .setShowSpinner(true)
                .setShowCloseButton(true)
                .setRingStyle(FacifyConfig.RingStyle.DOTS)
                .setStatusTextColor(0xFF222222)
                .setStatusErrorColor(0xFFE1156F)
                .setDotsColors(0xFFE1156F, 0xFFE9D6DE)
                .setRingColors(0xFFCBD0D6, 0xFFE1156F, 0xFF35C759)
                .setAutoRestartOnFail(true)
                .setRestartDelayMs(700).setStepTimeoutMs(12000)
                .setDesign(FacifyDesign.FACIFY_DESIGN)
                .build();


        facifyView.start(this, config, new FacifyCallback() {

            @Override
            public void onPermissionRequired(@NonNull String[] permissions) {
                camPerm.launch(Manifest.permission.CAMERA);
            }

            @Override
            public void onStateChanged(FacifyState state, String message, int doneSteps) {
                Log.i("FACIFY", "state=" + state + " msg=" + message + " steps=" + doneSteps);
            }

            @Override
            public void onSuccess(@NonNull String imagePath) {
                // ✅ Verified image file path
                Log.i("FACIFY", "SUCCESS: " + imagePath);

            }

            @Override
            public void onError(@NonNull String error) {
                Log.e("FACIFY", "ERROR: " + error);
            }

            @Override
            public void onCancelled() {
                Log.w("FACIFY", "Cancelled");
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (facifyView != null) facifyView.stop(); // ✅ recommended
    }
}
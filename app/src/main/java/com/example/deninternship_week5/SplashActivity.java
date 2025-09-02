package com.example.deninternship_week5;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import com.example.deninternship_week5.Utils.PrefsManager;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
public class SplashActivity extends AppCompatActivity {

    private AdView adViewSplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        // Initialize AdMob
        MobileAds.initialize(this, initializationStatus -> {});

        adViewSplash = findViewById(R.id.adViewSplash);
        adViewSplash.loadAd(new AdRequest.Builder().build());

        new Handler().postDelayed(() -> {
            if (PrefsManager.isFirstLaunch(SplashActivity.this)) {
                // Go to onboarding screens if first launch
                startActivity(new Intent(SplashActivity.this, OnboardingFeaturesActivity.class));
            } else {
                // Otherwise go straight to register/login
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
            }
            finish();
        }, 2500);
    }
}

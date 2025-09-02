package com.example.deninternship_week5;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class OnboardingFeaturesActivity extends AppCompatActivity {

    private AdView adViewFeatures;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.onboarding_features);

        MobileAds.initialize(this, initializationStatus -> {});
        adViewFeatures = findViewById(R.id.adViewFeatures);
        adViewFeatures.loadAd(new AdRequest.Builder().build());

        findViewById(R.id.btnNext).setOnClickListener(v -> {
            startActivity(new Intent(this, OnboardingPrivacyActivity.class));
            finish();
        });
    }
}

package com.example.deninternship_week5;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
public class OnboardingPrivacyActivity extends AppCompatActivity {
    private AdView adViewPrivacy;
    private InterstitialAd interstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.onboarding_privacy);
        // Load Banner
        MobileAds.initialize(this, initializationStatus -> {});
        adViewPrivacy = findViewById(R.id.adViewPrivacy);
        adViewPrivacy.loadAd(new AdRequest.Builder().build());
        // Load Interstitial
        InterstitialAd.load(this, "ca-app-pub-3940256099942544/1033173712",
                new AdRequest.Builder().build(),
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd ad) {
                        interstitialAd = ad;
                    }
                });
        findViewById(R.id.btnFinish).setOnClickListener(v -> {
            if (interstitialAd != null) {
                interstitialAd.show(OnboardingPrivacyActivity.this);
                interstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                    @Override
                    public void onAdDismissedFullScreenContent() {
                        goToMainApp();
                    }
                });
            } else {
                goToMainApp();
            }
        });
    }
    private void goToMainApp() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
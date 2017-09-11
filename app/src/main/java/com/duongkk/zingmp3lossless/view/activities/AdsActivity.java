package com.duongkk.zingmp3lossless.view.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.blankj.utilcode.util.LogUtils;
import com.duongkk.zingmp3lossless.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

public class AdsActivity extends AppCompatActivity {
    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ads);
//        MobileAds.initialize(getApplicationContext(),"ca-app-pub-4447279115464296~5831517351");
        MobileAds.initialize(getApplicationContext(),"ca-app-pub-4447279115464296~4239207165");
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-4447279115464296/4099606366");
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                showInterstitial();
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                LogUtils.e(errorCode);
            }

            @Override
            public void onAdClosed() {
            }
        });
        loadInterstitial();
    }
    private void showInterstitial() {
        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            Toast.makeText(this, "Ad did not load", Toast.LENGTH_SHORT).show();
        }
    }

    private void loadInterstitial() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice("1C8067A9CD67109A760B8802C99C0F4D")
               .build();
        mInterstitialAd.loadAd(adRequest);
    }

}

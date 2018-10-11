package com.rollernigi.game;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;



public class AndroidLauncher extends AndroidApplication {
	private InterstitialAd mInterstitialAd;

	Runnable runnable = new Runnable() {        @Override        public void run() {
		mInterstitialAd.setAdListener(new AdListener() {
			@Override
			public void onAdLoaded() {
				mInterstitialAd.show();
			}

			@Override
			public void onAdFailedToLoad(int errorCode) {
				// Code to be executed when an ad request fails.
			}

			@Override
			public void onAdOpened() {
				// Code to be executed when the ad is displayed.
			}

			@Override
			public void onAdLeftApplication() {
				// Code to be executed when the user has left the app.
			}

			@Override
			public void onAdClosed() {
				// Code to be executed when when the interstitial ad is closed.
			}
		});
		mInterstitialAd.show();
	handler.postDelayed(this, 150000);        }    };



	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

		initialize(new RollerNiGiGame(), config);
		// Sample AdMob app ID: ca-app-pub-3940256099942544~3347511713
		MobileAds.initialize(this, "ca-app-pub-1266049613503333~7913903139");
		mInterstitialAd = new InterstitialAd(this);
		mInterstitialAd.setAdUnitId("ca-app-pub-1266049613503333/3591514743");
		mInterstitialAd.loadAd(new AdRequest.Builder().build());
		mInterstitialAd.setAdListener(new AdListener() {
			@Override
			public void onAdClosed() {
				// Load the next interstitial.
				mInterstitialAd.loadAd(new AdRequest.Builder().build());
			}

		});
		mInterstitialAd.show();
		handler.postDelayed(runnable, 600000);//每10分钟执行一次runnable.
		mInterstitialAd.setAdListener(new AdListener() {
			@Override
			public void onAdLoaded() {
			}

			@Override
			public void onAdFailedToLoad(int errorCode) {
				// Code to be executed when an ad request fails.
			}

			@Override
			public void onAdOpened() {
				// Code to be executed when the ad is displayed.
			}

			@Override
			public void onAdLeftApplication() {
				// Code to be executed when the user has left the app.
			}

			@Override
			public void onAdClosed() {
				// Code to be executed when when the interstitial ad is closed.
			}
		});
		hideBottomUIMenu();
	}
	protected void onResume() {
		super.onResume();
		hideBottomUIMenu();
	}

	protected void hideBottomUIMenu() {
		//隐藏虚拟按键，并且全屏
		if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
			View v = this.getWindow().getDecorView();
			v.setSystemUiVisibility(View.GONE);
		} else if (Build.VERSION.SDK_INT >= 19) {
			//for new api versions.
			View decorView = getWindow().getDecorView();
			int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
					| View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
			decorView.setSystemUiVisibility(uiOptions);
		}
	}
}

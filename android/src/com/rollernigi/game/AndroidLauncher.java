package com.rollernigi.game;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import com.google.android.gms.ads.MobileAds;
import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		hideBottomUIMenu();
		initialize(new RollerNiGiGame(), config);
		// Sample AdMob app ID: ca-app-pub-3940256099942544~3347511713
		MobileAds.initialize(this, "ca-app-pub-3940256099942544~3347511713");

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

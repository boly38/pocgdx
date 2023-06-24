package com.olity.pocgdx;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.olity.pocgdx.PocGame;

public class AndroidLauncher extends AndroidApplication {
    private static final String TAG = "AndroidLauncher";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        config.useAccelerometer = false;
        config.useCompass = false;
        showResolution();
        initialize(new PocGame(PocGame.LaunchMode.ANDROID), config);
    }

    private void showResolution() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;
        Log.i(TAG, "width=" + width + " height=" + height);
        // 2023-06-19 13:52:22.104 10568-10568 AndroidLauncher         com.olity.pocgdx                     I  width=2186 height=1080
    }
}

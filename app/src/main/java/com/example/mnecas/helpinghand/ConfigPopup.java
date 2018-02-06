package com.example.mnecas.helpinghand;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;

/**
 * Created by mnecas on 6.2.18.
 */

public class ConfigPopup extends Activity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.config_window);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int height=dm.heightPixels;
        int widht=dm.widthPixels;
        getWindow().setLayout((int) (widht*.8),(int) (height*.4));
    }
}

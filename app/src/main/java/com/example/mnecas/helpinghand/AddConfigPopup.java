package com.example.mnecas.helpinghand;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.Toast;

/**
 * Created by mnecas on 6.2.18.
 */

public class AddConfigPopup extends Activity{
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_config);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int height=dm.heightPixels;
        int widht=dm.widthPixels;
        getWindow().setLayout((int) (widht*.8),(int) (height*.4));
        NumberPicker hours=(NumberPicker) findViewById(R.id.hours);
        NumberPicker minutes=(NumberPicker) findViewById(R.id.minutes);
        hours.setMaxValue(24);
        hours.setMinValue(0);
        hours.setWrapSelectorWheel(true);
        minutes.setMaxValue(60);
        minutes.setMinValue(0);
        minutes.setWrapSelectorWheel(true);
    }

}

package com.example.mnecas.helpinghand;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by mnecas on 6.2.18.
 */

public class AddConfigPopup extends Activity {
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_config);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int height = dm.heightPixels;
        int widht = dm.widthPixels;
        getWindow().setLayout((int) (widht * .85), (int) (height * .45));
        HashMap<String, Integer> checkboxes = new HashMap <String,Integer>();
        checkboxes.put("1",R.id.checkBox1);
        checkboxes.put("2",R.id.checkBox2);
        checkboxes.put("3",R.id.checkBox3);
        checkboxes.put("4",R.id.checkBox4);
        checkboxes.put("5",R.id.checkBox5);
        checkboxes.put("6",R.id.checkBox6);
        checkboxes.put("7",R.id.checkBox7);

        final Intent intent = getIntent();
        try {
            JSONObject config = new JSONObject(intent.getStringExtra("config_settings"));
            JSONArray days=config.getJSONArray("day");
            for (int i = 0; i < days.length(); i++) {
                Integer id = checkboxes.get(days.getJSONObject(i).getString("name"));
                CheckBox checkBox=(CheckBox) findViewById(id);
                checkBox.setChecked(true);
            }
            TextView config_name = (TextView) findViewById(R.id.config_name_textview);
            config_name.setText(config.getString("name"));
            Switch aSwitch= (Switch) findViewById(R.id.switch2);
            aSwitch.setChecked(config.getBoolean("state"));
            NumberPicker hours = (NumberPicker) findViewById(R.id.hours);
            NumberPicker minutes = (NumberPicker) findViewById(R.id.minutes);
            hours.setMaxValue(23);
            hours.setMinValue(0);
            hours.setValue(config.getInt("hours"));
            hours.setWrapSelectorWheel(true);
            minutes.setMaxValue(59);
            minutes.setMinValue(0);
            minutes.setValue(config.getInt("minutes"));
            minutes.setWrapSelectorWheel(true);
        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }

    }

}

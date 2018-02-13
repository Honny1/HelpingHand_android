package com.example.mnecas.helpinghand.popups;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.NumberPicker;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.mnecas.helpinghand.My_classes.ConnectToServer;
import com.example.mnecas.helpinghand.My_classes.VolleyCallback;
import com.example.mnecas.helpinghand.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by mnecas on 6.2.18.
 */

public class AddConfigPopup extends Activity {
    TextView config_name;
    Switch aSwitch;
    NumberPicker hours;
    NumberPicker minutes;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_config);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int height = dm.heightPixels;
        int widht = dm.widthPixels;

        getWindow().setLayout((int) (widht * .85), (int) (height * .55));

        final HashMap<String, Integer> checkboxes = new HashMap<String, Integer>();
        checkboxes.put("Monday", R.id.checkBox1);
        checkboxes.put("Tuesday", R.id.checkBox2);
        checkboxes.put("Wednesday", R.id.checkBox3);
        checkboxes.put("Thursday", R.id.checkBox4);
        checkboxes.put("Friday", R.id.checkBox5);
        checkboxes.put("Saturday", R.id.checkBox6);
        checkboxes.put("Sunday", R.id.checkBox7);

        @SuppressLint("UseSparseArrays") final HashMap<Integer, String> days_names = new HashMap<>();
        days_names.put(0, "Monday");
        days_names.put(1, "Tuesday");
        days_names.put(2, "Wednesday");
        days_names.put(3, "Thursday");
        days_names.put(4, "Friday");
        days_names.put(5, "Saturday");
        days_names.put(6, "Sunday");

        final Intent intent = getIntent();
        final ArrayList<CheckBox> checkBoxes = new ArrayList<CheckBox>();
        try {
            JSONObject config = new JSONObject(intent.getStringExtra("config_settings"));
            JSONArray days = config.getJSONArray("day");
            for (int i = 0; i < days.length(); i++) {
                Integer id = checkboxes.get(days.getJSONObject(i).getString("name"));
                CheckBox checkBox = (CheckBox) findViewById(id);
                checkBoxes.add(checkBox);
                checkBox.setChecked(true);
            }
            config_name = (TextView) findViewById(R.id.config_name_textview);
            config_name.setText(config.getString("name"));
            aSwitch = (Switch) findViewById(R.id.switch2);
            aSwitch.setChecked(config.getBoolean("state"));
            hours = (NumberPicker) findViewById(R.id.hours);
            minutes = (NumberPicker) findViewById(R.id.minutes);
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


        Button save_btn = (Button) findViewById(R.id.save_btn2);
        save_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(final View v) {
                VolleyCallback mResultCallback = new VolleyCallback() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void notifySuccess(String response) {
                        if (Objects.equals(response, "error")) {
                            Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                        } else {
                            finish();
                        }
                    }

                    @Override
                    public void notifyError(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                };

                Intent intent = getIntent();
                String config_id = intent.getStringExtra("config_id");
                Map<String, String> map = new HashMap<String, String>();
                SharedPreferences prefs = getSharedPreferences("user_info", MODE_PRIVATE);
                String name = prefs.getString("name", "");
                String device_id = prefs.getString("device_id", "");
                map.put("username", name);
                map.put("device_id", "" + device_id);
                map.put("config_id", "" + config_id);
                map.put("state", "" + aSwitch.isChecked());
                map.put("hours", "" + hours.getValue());
                map.put("minutes", "" + minutes.getValue());
                for (int i = 0; i < 7; i++) {
                    CheckBox checkBox = (CheckBox) findViewById(checkboxes.get(days_names.get(i)));
                    map.put(checkBox.getText().toString(), "" + checkBox.isChecked());
                }
                map.put("config_name", config_name.getText().toString());
                ConnectToServer cnt = new ConnectToServer(getApplicationContext());
                cnt.postResponse("/api/configSave", map, mResultCallback);
            }
        });

    }

    public void delete_config(View view) {
        VolleyCallback mResultCallback = new VolleyCallback() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void notifySuccess(String response) {
                if (Objects.equals(response, "error")) {
                    Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                } else {
                    finish();
                }
            }

            @Override
            public void notifyError(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        };

        Intent intent = getIntent();
        String config_id = intent.getStringExtra("config_id");
        Map<String, String> map = new HashMap<String, String>();
        map.put("config_id", "" + config_id);
        ConnectToServer cnt = new ConnectToServer(getApplicationContext());
        cnt.postResponse("/api/delConfig", map, mResultCallback);
    }
}

package com.example.mnecas.helpinghand.popups;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.mnecas.helpinghand.Adapters.ConfigListViewAdapter;
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

public class ConfigPopup extends Activity {


    ArrayList<String> configs = new ArrayList<String>();
    EditText light_textview;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.config_window);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int height = dm.heightPixels;
        int widht = dm.widthPixels;
        getWindow().setLayout((int) (widht * .85), (int) (height * .45));

        final Intent intent = getIntent();
        try {
            JSONArray response = new JSONArray(intent.getStringExtra("config_response"));
            light_textview = (EditText) findViewById(R.id.light_textview);
            light_textview.setText(intent.getStringExtra("device_name"));
            for (int i = 0; i < response.length(); i++) {
                configs.add(response.getJSONObject(i).getString("name"));
            }
        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }
        ListAdapter listAdapter = new ConfigListViewAdapter(this, configs);
        ListView listView = (ListView) findViewById(R.id.config_listview);
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent popup = new Intent(getApplicationContext(), AddConfigPopup.class);
                try {
                    JSONArray response = new JSONArray(intent.getStringExtra("config_response"));
                    popup.putExtra("config_settings", response.getJSONObject(position).toString());
                    popup.putExtra("config_id", response.getJSONObject(position).get("id").toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                finish();
                startActivity(popup);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        VolleyCallback mResultCallback = new VolleyCallback() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void notifySuccess(String response) {
                if (Objects.equals(response, "error")) {
                    Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void notifyError(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        };
        Map<String, String> map = new HashMap<String, String>();
        SharedPreferences prefs = getSharedPreferences("user_info", MODE_PRIVATE);

        String name = prefs.getString("name", "");
        String device_id = prefs.getString("device_id", "");
        map.put("username", name);
        map.put("device_id", device_id);
        map.put("light_name", light_textview.getText().toString());
        ConnectToServer cnt = new ConnectToServer(getApplicationContext());
        cnt.postResponse("http://192.168.2.148:8000/api/deviceSave", map, mResultCallback);
    }
}

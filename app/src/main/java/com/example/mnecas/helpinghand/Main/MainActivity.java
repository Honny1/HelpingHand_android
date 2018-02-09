package com.example.mnecas.helpinghand.Main;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.mnecas.helpinghand.Adapters.MainListViewAdapter;
import com.example.mnecas.helpinghand.My_classes.ConnectToServer;
import com.example.mnecas.helpinghand.My_classes.VolleyCallback;
import com.example.mnecas.helpinghand.R;
import com.example.mnecas.helpinghand.popups.ConfigPopup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@SuppressLint("Registered")
public class MainActivity extends AppCompatActivity {

    JSONObject response_json;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    protected void onResume() {
        super.onResume();
        VolleyCallback mResultCallback = new VolleyCallback() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void notifySuccess(String response) {
                if (Objects.equals(response, "error")) {
                    Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        final ArrayList<String> lights = new ArrayList<String>();
                        response_json = new JSONObject(response);
                        //String pageName = response_json.getJSONObject("pageInfo").getString("pageName");
                        final JSONArray device_array = response_json.getJSONArray("device");

                        for (int i = 0; i < device_array.length(); i++) {
                            lights.add(device_array.getJSONObject(i).getString("name"));
                        }

                        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
                        setSupportActionBar(toolbar);

                        ListAdapter listAdapter = new MainListViewAdapter(getApplicationContext(), lights, device_array);
                        ListView listView = (ListView) findViewById(R.id.list_view);
                        listView.setAdapter(listAdapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent popup = new Intent(getApplicationContext(), ConfigPopup.class);
                                try {
                                    popup.putExtra("config_response", device_array.getJSONObject(position).getJSONArray("configuration").toString());
                                    popup.putExtra("device_name", device_array.getJSONObject(position).getString("name"));

                                    SharedPreferences.Editor editor = getSharedPreferences("user_info", MODE_PRIVATE).edit();
                                    editor.putString("device_id", device_array.getJSONObject(position).getString("id"));
                                    editor.putString("device_name", device_array.getJSONObject(position).getString("name"));
                                    editor.apply();
                                } catch (JSONException e) {
                                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                                }

                                startActivity(popup);
                                overridePendingTransition(R.anim.fadein, R.anim.fadeout);

                            }
                        });
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void notifyError(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        };
        try {
            Thread.sleep(3000);
            Map<String, String> map = new HashMap<String, String>();
            Intent intent = getIntent();
            String user = intent.getStringExtra("username");
            map.put("username", user);
            ConnectToServer cnt = new ConnectToServer(getApplicationContext());
            cnt.postResponse("http://192.168.2.148:8000/api/userInfo", map, mResultCallback);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_items, menu);
        return true;
    }


}

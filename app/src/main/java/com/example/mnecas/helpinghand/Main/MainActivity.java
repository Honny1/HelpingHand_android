package com.example.mnecas.helpinghand;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Toolbar toolbar;
    ArrayList<String> lights = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        try {
            JSONObject response = new JSONObject(intent.getStringExtra("response"));
            //String pageName = response.getJSONObject("pageInfo").getString("pageName");
            final JSONArray device_array = response.getJSONArray("device");

            for (int i = 0; i < device_array.length(); i++) {
                lights.add(device_array.getJSONObject(i).getString("name"));
            }
            SharedPreferences.Editor editor = getSharedPreferences("", MODE_PRIVATE).edit();
            editor.putString("name", "Elena");
            editor.putInt("idName", 12);
            editor.apply();

            toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            ListAdapter listAdapter = new MainListViewAdapter(this, lights,device_array);
            ListView listView = (ListView) findViewById(R.id.list_view);
            listView.setAdapter(listAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent popup = new Intent(getApplicationContext(), ConfigPopup.class);
                    try {
                        popup.putExtra("config_response", device_array.getJSONObject(position).getJSONArray("configuration").toString());
                        popup.putExtra("device_name", device_array.getJSONObject(position).getString("name"));
                    } catch (JSONException e) {
                        Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT).show();
                    }
                    startActivity(popup);
                    overridePendingTransition(R.anim.fadein, R.anim.fadeout);

                }
            });
        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
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

package com.example.mnecas.helpinghand.popups;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mnecas.helpinghand.Adapters.ConfigListViewAdapter;
import com.example.mnecas.helpinghand.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by mnecas on 6.2.18.
 */

public class ConfigPopup extends Activity{


    ArrayList<String> configs = new ArrayList<String>();

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.config_window);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int height=dm.heightPixels;
        int widht=dm.widthPixels;
        getWindow().setLayout((int) (widht*.85),(int) (height*.45));

        final Intent intent=getIntent();
        try {
            JSONArray response = new JSONArray(intent.getStringExtra("config_response"));
            EditText light_textview = (EditText) findViewById(R.id.light_textview);
            light_textview.setText(intent.getStringExtra("device_name"));
            for (int i=0;i<response.length();i++){
                configs.add(response.getJSONObject(i).getString("name"));
            }
        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT).show();
        }
        ListAdapter listAdapter = new ConfigListViewAdapter(this,configs);
        ListView listView = (ListView) findViewById(R.id.config_listview);
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent popup = new Intent(getApplicationContext(),AddConfigPopup.class);
                try {
                    JSONArray response = new JSONArray(intent.getStringExtra("config_response"));
                    popup.putExtra("config_settings",response.getJSONObject(position).toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                startActivity(popup);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);

            }
        });

    }
    @Override
    protected void onResume() {
        super.onResume();
    }

}

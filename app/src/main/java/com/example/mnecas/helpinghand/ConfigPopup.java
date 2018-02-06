package com.example.mnecas.helpinghand;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

/**
 * Created by mnecas on 6.2.18.
 */

public class ConfigPopup extends Activity{

    String[] web = {
            "Google Plus",
            "Twitter",
            "Windows",
            "Bing",
            "Itunes",
            "Wordpress",
            "Wordpress",
            "Wordpress",
            "Wordpress",
            "Wordpress",
            "Wordpress",
            "Wordpress",
            "Drupal"
    } ;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.config_window);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int height=dm.heightPixels;
        int widht=dm.widthPixels;
        getWindow().setLayout((int) (widht*.8),(int) (height*.4));

        ListAdapter listAdapter = new ConfigListViewAdapter(this,web);
        ListView listView = (ListView) findViewById(R.id.config_listview);
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),""+parent.getId(),Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(),""+view.getId(),Toast.LENGTH_SHORT).show();
                Intent popup = new Intent(getApplicationContext(),AddConfigPopup.class);
                popup.putExtra("somesting","something");
                startActivity(popup);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);

            }
        });
    }

}

package com.example.mnecas.helpinghand;

import android.content.Intent;
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

public class MainActivity extends AppCompatActivity{
    Toolbar toolbar;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar=(Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ListAdapter listAdapter = new MainListViewAdapter(this,web);
        ListView listView = (ListView) findViewById(R.id.list_view);
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),""+parent.getId(),Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(),""+view.getId(),Toast.LENGTH_SHORT).show();
                Intent popup = new Intent(getApplicationContext(),ConfigPopup.class);
                popup.putExtra("somesting","something");
                startActivity(popup);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_items,menu);
        return true;
    }


}

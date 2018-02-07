package com.example.mnecas.helpinghand;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainListViewAdapter extends ArrayAdapter{
    public MainListViewAdapter(Context context, ArrayList<String> lights) {
        super(context, R.layout.main_listitem,lights);

    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        @SuppressLint("ViewHolder") View customView = layoutInflater.inflate(R.layout.main_listitem,parent,false);
        String oneLigt= (String) getItem(position);
        Switch sw = (Switch) customView.findViewById(R.id.switch1);

        sw.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),""+position,Toast.LENGTH_SHORT).show();

            }
        });


        TextView textView = (TextView) customView.findViewById(R.id.textView2);
        textView.setText(oneLigt);
        return customView;
    }
}

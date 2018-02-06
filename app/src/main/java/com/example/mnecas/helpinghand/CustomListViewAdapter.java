package com.example.mnecas.helpinghand;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class CustomListViewAdapter extends ArrayAdapter{
    public CustomListViewAdapter(Context context, String[] lights) {
        super(context, R.layout.custom_listitem,lights);

    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        @SuppressLint("ViewHolder") View customView = layoutInflater.inflate(R.layout.custom_listitem,parent,false);
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

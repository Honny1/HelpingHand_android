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

import java.util.ArrayList;

public class ConfigListViewAdapter extends ArrayAdapter{
    public ConfigListViewAdapter(Context context, ArrayList<String> lights) {
        super(context, R.layout.main_listitem,lights);

    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        @SuppressLint("ViewHolder") View customView = layoutInflater.inflate(R.layout.config_listitem,parent,false);
        String oneConfig= (String) getItem(position);

        TextView textView = (TextView) customView.findViewById(R.id.config_textview);
        textView.setText(oneConfig);
        return customView;
    }
}

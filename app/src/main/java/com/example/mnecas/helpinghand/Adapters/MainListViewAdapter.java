package com.example.mnecas.helpinghand.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.example.mnecas.helpinghand.My_classes.ConnectToServer;
import com.example.mnecas.helpinghand.R;
import com.example.mnecas.helpinghand.My_classes.VolleyCallback;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class MainListViewAdapter extends ArrayAdapter {
    private final JSONArray response;
    private final Context context;

    public MainListViewAdapter(Context context, ArrayList<String> lights, JSONArray response) {
        super(context, R.layout.main_listitem, lights);
        this.response = response;
        this.context=context;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        @SuppressLint("ViewHolder") View customView = layoutInflater.inflate(R.layout.main_listitem, parent, false);
        String oneLigt = (String) getItem(position);
        SharedPreferences.Editor editor = context.getApplicationContext().getSharedPreferences("config_change", MODE_PRIVATE).edit();
        editor.putString("light_name", oneLigt);
        editor.putInt("light_position", position);
        editor.apply();
        final Switch sw = (Switch) customView.findViewById(R.id.switch1);
        try {
            sw.setChecked(response.getJSONObject(position).getBoolean("state"));
        } catch (JSONException e) {
            Toast.makeText(convertView.getContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }
        sw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                try {
                    String ip = "http://"+response.getJSONObject(position).getString("ip");
                    if (sw.isChecked()) {
                        ip += "/On";
                    } else {
                        ip += "/Off";
                    }
                    VolleyCallback mResultCallback = new VolleyCallback() {
                        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                        @Override
                        public void notifySuccess(String response) {
                        }

                        @Override
                        public void notifyError(VolleyError error) {
                            Toast.makeText(v.getContext(), "cannot connect to device", Toast.LENGTH_LONG).show();
                        }
                    };

                    ConnectToServer cnt = new ConnectToServer(v.getContext());
                    cnt.getResponseFullURL(ip, mResultCallback);

                } catch (JSONException e) {
                    Toast.makeText(v.getContext(), e.toString(), Toast.LENGTH_SHORT).show();
                }

            }
        });


        TextView textView = (TextView) customView.findViewById(R.id.textView2);
        textView.setText(oneLigt);

        return customView;
    }
}

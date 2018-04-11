package com.example.mnecas.helpinghand.My_classes;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;
import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mnecas.helpinghand.My_classes.VolleyCallback;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by mnecas on 5.2.18.ll
 */

public class ConnectToServer extends AppCompatActivity {

    private String url;
    public String my_response;
    private int method;
    private Context mContext;
    public Map<String, String> map;

    public ConnectToServer(Context context) {
        this.url = "http://192.168.43.195:8000";
        map= new HashMap<String, String>();
        this.my_response = "";
        this.mContext = context;
        this.method = Request.Method.GET;
    }

    private void execute(final VolleyCallback callbac) {
        Log.w("URL",this.url);
        RequestQueue queue = Volley.newRequestQueue(this.mContext);
        queue.start();
        StringRequest stringRequest = new StringRequest(this.method, this.url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        callbac.notifySuccess(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                callbac.notifyError(error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                return map;
            }
        };
        queue.add(stringRequest);
    }

    public void getResponse(String url, final VolleyCallback callbac) {
        this.url += url;
        this.method = Request.Method.GET;
        this.execute(callbac);
    }

    public void getResponseFullURL(String url, final VolleyCallback callbac) {
        this.url = url;
        this.method = Request.Method.GET;
        this.execute(callbac);
    }


    public void postResponse(String url, Map map, final VolleyCallback callbac) {
        this.url += url;
        this.method = Request.Method.POST;
        this.map = map;
        this.execute(callbac);
    }


}

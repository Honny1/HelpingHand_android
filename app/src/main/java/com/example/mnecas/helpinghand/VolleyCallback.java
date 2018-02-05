package com.example.mnecas.helpinghand;

import com.android.volley.VolleyError;

import org.json.JSONObject;

/**
 * Created by mnecas on 5.2.18.
 */
public interface VolleyCallback {

    void notifyError( VolleyError error);
    void notifySuccess(String response);
}


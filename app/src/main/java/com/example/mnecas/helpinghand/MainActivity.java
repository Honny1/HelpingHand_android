package com.example.mnecas.helpinghand;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    View regView;
    View mLoadingView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText login_username=(EditText) findViewById(R.id.login_username);
        final EditText login_password=(EditText) findViewById(R.id.login_password);

        Button login_btn = (Button) findViewById(R.id.login_btn);

        login_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                VolleyCallback mResultCallback = new VolleyCallback() {
                    @Override
                    public void notifySuccess(String response) {
                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void notifyError(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
                    }
                };
                Map<String, String> map= new HashMap<String, String>();
                map.put("username",login_username.getText().toString());
                map.put("password",login_password.getText().toString());

                ConnectToServer cnt=new ConnectToServer(getApplicationContext());
                cnt.postResponse("http://192.168.2.148:8000/api/login",map,mResultCallback);

            }
        });

        TextView reg_textview = (TextView) findViewById(R.id.reg_textview);

        reg_textview.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(),register.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);

            }
        });

    }
}

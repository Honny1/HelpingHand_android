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

public class register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText reg_username=(EditText) findViewById(R.id.reg_username);
        final EditText reg_email=(EditText) findViewById(R.id.reg_email);
        final EditText reg_password=(EditText) findViewById(R.id.reg_password);

        TextView reg_textview = (TextView) findViewById(R.id.log_textview);
        Button reg_btn = (Button) findViewById(R.id.reg_button);

        reg_textview.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(),MainActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.fadein, R.anim.fadeout);

            }
        });

        reg_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                VolleyCallback reg_callback = new VolleyCallback() {
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
                map.put("email",reg_email.getText().toString());
                map.put("username",reg_username.getText().toString());
                map.put("password",reg_password.getText().toString());

                ConnectToServer cnt=new ConnectToServer(getApplicationContext());
                cnt.postResponse("http://192.168.2.148:8000/api/register",map,reg_callback);

            }
        });

    }
}

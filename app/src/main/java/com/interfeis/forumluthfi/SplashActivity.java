package com.interfeis.forumluthfi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences data_app = getSharedPreferences("DATA_APP", MODE_PRIVATE);
                Intent i = null;

                if(data_app.contains("data_email")){
                    i = new Intent(getApplicationContext(), MainActivity.class);
                }else{
                    i = new Intent(getApplicationContext(), LoginActivity.class);
                }

                startActivity(i);
                finish();
            }
        }, 2000);
    }
}

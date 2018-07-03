package com.asg.ashish.rine;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        int secondsDelayed = 1;

        new Handler().postDelayed(new Runnable() {
            public void run() {
                try {
                    Intent i = new Intent(SplashActivity.this, SignupActivity.class);
                    startActivity(i);
                    finish();
                }catch (Exception e)
                {
                    Toast.makeText(SplashActivity.this,"App crashed",Toast.LENGTH_SHORT).show();
                }


                // close this activity

            }
        }, secondsDelayed * 1000);
    }
}



package com.asg.ashish.rine;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class Sign_in extends AppCompatActivity {
    EditText email, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        email = (EditText)findViewById(R.id.email);
        pass = (EditText)findViewById(R.id.password);
    }

    public void login(View view){
        if(isNetworkAvailable()){
            String username = email.getText().toString().trim();
            String password = pass.getText().toString();
            String type = "login";
            Background_Worker background_worker = new Background_Worker(this);
            background_worker.execute(type, username, password);

        }
        else Toast.makeText(Sign_in.this,"Check Your connection", Toast.LENGTH_SHORT).show();

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}

package com.asg.ashish.rine;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class User_dash extends AppCompatActivity {

    private TextView agetext, heighttext, weighttext, bmitext, gendertext;
    private String age, height, weight, bmi, gender;
    private SharedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dash);
        agetext = (TextView)findViewById(R.id.dashage);
        heighttext = (TextView)findViewById(R.id.dashheight);
        weighttext = (TextView) findViewById(R.id.dashweight);
        bmitext = (TextView)findViewById(R.id.dashbmi);
        gendertext = (TextView)findViewById(R.id.dashgender);
        preferences = getSharedPreferences("profile", MODE_PRIVATE);
        age = preferences.getString("age","--");
        height = preferences.getString("height","--");
        weight = preferences.getString("weight","--");
        gender = preferences.getString("gender","--");
        bmi = preferences.getString("bmi", "--");
        agetext.setText(age);
        heighttext.setText(height);
        weighttext.setText(weight);
        bmitext.setText(bmi);
        gendertext.setText(gender);

    }

    public void openbmi(View view){
        Intent bmi = new Intent(User_dash.this, BmiActivity.class);
        startActivity(bmi);
    }

    public void onResume(){
        super.onResume();
        /*agetext = (TextView)findViewById(R.id.dashage);
        heighttext = (TextView)findViewById(R.id.dashheight);
        weighttext = (TextView) findViewById(R.id.dashweight);
        bmitext = (TextView)findViewById(R.id.dashbmi);
        gendertext = (TextView)findViewById(R.id.dashgender);*/
        preferences = getSharedPreferences("profile", MODE_PRIVATE);
        age = preferences.getString("age","--");
        height = preferences.getString("height","--");
        weight = preferences.getString("weight","--");
        gender = preferences.getString("gender","--");
        bmi = preferences.getString("bmi", "--");
        agetext.setText(age);
        heighttext.setText(height);
        weighttext.setText(weight);
        bmitext.setText(bmi);
        gendertext.setText(gender);

    }
}

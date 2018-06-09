package com.asg.ashish.rine;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

public class User_dash extends AppCompatActivity {

    private TextView agetext, heighttext, weighttext, bmitext, gendertext, name;
    private String age, height, weight, bmi, gender, namestr, pic;
    private SharedPreferences preferences;
    private ImageView profilepic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dash);
        agetext = (TextView)findViewById(R.id.dashage);
        heighttext = (TextView)findViewById(R.id.dashheight);
        weighttext = (TextView) findViewById(R.id.dashweight);
        bmitext = (TextView)findViewById(R.id.dashbmi);
        gendertext = (TextView)findViewById(R.id.dashgender);
        name = (TextView)findViewById(R.id.profilename);
        profilepic = (ImageView)findViewById(R.id.profilepic);
        preferences = getSharedPreferences("profile", MODE_PRIVATE);
        age = preferences.getString("age","--");
        height = preferences.getString("height","--");
        weight = preferences.getString("weight","--");
        gender = preferences.getString("gender","--");
        bmi = preferences.getString("bmi", "--");
        namestr = preferences.getString("name", "--");
        pic = preferences.getString("profilepic","--");
        name.setText(namestr);
        Glide.with(this).load(pic).override(1000,500).into(profilepic);
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

package com.asg.ashish.rine;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

public class User_dash extends AppCompatActivity {

    private TextView agetext, heighttext, weighttext, bmitext, gendertext, name;
    private String age, height, weight, bmi, gender, namestr, pic;
    private SharedPreferences preferences;
    private ImageView profilepic;
    private Navi_drawer navi_drawer;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dash);
        drawerLayout = findViewById(R.id.navi);
        navigationView = findViewById(R.id.nav_view);
        navi_drawer = new Navi_drawer();
        navi_drawer.nav(drawerLayout, navigationView);
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
        if(pic.equals("--") || pic.equals("")){
            profilepic.setImageResource(R.drawable.sampleprofile);
        }
        else
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

    public void back(View view){
        this.onBackPressed();
    }

    public void edit_name(View view){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(User_dash.this);
        alertDialog.setTitle("Enter Name");

        // Setting Dialog Message
       // alertDialog.setMessage("Enter Password");
            final EditText input = new EditText(this);
        alertDialog.setView(input);

        // Setting Icon to Dialog
        alertDialog.setIcon(R.drawable.splash);

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {
                        SharedPreferences preferences = getSharedPreferences("profile", MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("name", input.getText().toString());
                        editor.apply();
                        name.setText(input.getText().toString());

                    }
                });
        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("CANCEL",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Write your code here to execute after dialog
                        dialog.cancel();
                    }
                });

        // closed

        // Showing Alert Message
        alertDialog.show();
    }
}

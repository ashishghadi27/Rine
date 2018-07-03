package com.asg.ashish.rine;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.github.anastr.speedviewlib.SpeedView;
import com.github.anastr.speedviewlib.Speedometer;

public class Bmicalulate extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    TextView weigh;
    SpeedView s;
    private Navi_drawer navi_drawer;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bmicalulate);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.navi);
        navigationView = findViewById(R.id.nav_view);
        navi_drawer = new Navi_drawer();
        navi_drawer.nav(mDrawerLayout, navigationView);
        s = (SpeedView)findViewById(R.id.speedView);
        weigh = (TextView)findViewById(R.id.weight);
        try{
            Intent myIntent = getIntent();
            String age = myIntent.getStringExtra("age");
            String feet = myIntent.getStringExtra("feet");
            String inch = myIntent.getStringExtra("inch");
            String weight = myIntent.getStringExtra("weight");
            feet = feet.trim();
            inch = inch.trim();
            double feet_meter = Integer.parseInt(feet)*0.3048;
            double inch_meter = Integer.parseInt(inch)*0.0254;
            double bmi = Integer.parseInt(weight)/((feet_meter+inch_meter)*(feet_meter+inch_meter));
            Toast.makeText(this,age,Toast.LENGTH_SHORT).show();
            String set = age+"Y  |  "+weight+" Kg  |  "+feet+"'"+inch;
            weigh.setText(set);
            s.speedTo((float)bmi);
            SharedPreferences sharedPreferences = getSharedPreferences("profile", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("bmi", Integer.toString((int)bmi));
            editor.apply();
        }catch (Exception e){
            Toast.makeText(this,"Go Back And Enter Proper Information",Toast.LENGTH_SHORT).show();
        }


    }

    public void drawerclick(View view){
        mDrawerLayout.openDrawer(Gravity.END);

    }

    public void back(View view){
        this.onBackPressed();
    }
}

package com.asg.ashish.rine;

import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

public class Workouts extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workouts);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.navi);
    }

    public void drawerclick(View view){
        mDrawerLayout.openDrawer(Gravity.END);

    }

    public void back(View view){
        this.onBackPressed();
    }

    public void loadseven(View view){
        Intent i = new Intent(Workouts.this, seven_Day.class);
        startActivity(i);

    }
}

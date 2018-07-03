package com.asg.ashish.rine;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

public class Workouts extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    Navi_drawer navi_drawer;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workouts);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.navi);
        navigationView = findViewById(R.id.nav_view);
        navi_drawer = new Navi_drawer();
        navi_drawer.nav(mDrawerLayout, navigationView);
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

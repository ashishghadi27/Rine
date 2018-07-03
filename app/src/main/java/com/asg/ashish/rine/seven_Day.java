package com.asg.ashish.rine;

import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

public class seven_Day extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private Navi_drawer navi_drawer;
    private NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seven__day);
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
}

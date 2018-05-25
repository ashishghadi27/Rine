package com.asg.ashish.rine;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

public class seven_Day extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seven__day);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.navi);
    }

    public void drawerclick(View view){
        mDrawerLayout.openDrawer(Gravity.END);

    }

    public void back(View view){
        this.onBackPressed();
    }
}

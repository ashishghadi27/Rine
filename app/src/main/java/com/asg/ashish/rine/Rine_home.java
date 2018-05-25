package com.asg.ashish.rine;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;

public class Rine_home extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rine_home);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.navi);

    }

    public void drawerclick(View view){
        mDrawerLayout.openDrawer(Gravity.RIGHT);

    }

    public void loadProducts(View view){
        Intent i = new Intent(Rine_home.this, Products_Activity.class);
        startActivity(i);
    }

    public void calbmi(View view){
        Intent i = new Intent(Rine_home.this, BmiActivity.class);
        startActivity(i);
    }

    public void calrecipe(View view){
        Intent recipe = new Intent(Rine_home.this, Recipes.class);
        startActivity(recipe);
    }

    public void calworkouts(View view){
        Intent workout = new Intent(Rine_home.this, Workouts.class);
        startActivity(workout);

    }

    public void tocart(View view){
        Intent i = new Intent(this, Cart_Activity.class);
        startActivity(i);
    }

    public void back(View view){
        this.onBackPressed();
    }
}

package com.asg.ashish.rine;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class Rine_home extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private Button products_button, sucscription_button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rine_home);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.navi);
        products_button = findViewById(R.id.productsbutton);
        sucscription_button = findViewById(R.id.subscriptionbutton);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                switch (item.getItemId()) {
                    // Respond to the action bar's Up/Home button
                    case R.id.navProducts:
                        Intent a = new Intent(Rine_home.this, Products_Activity.class);
                        startActivity(a);
                        item.setChecked(false);
                        mDrawerLayout.closeDrawer(Gravity.END);
                        return true;

                    case R.id.navcart:
                        Intent x = new Intent(Rine_home.this, Cart_Activity.class);
                        startActivity(x);
                        mDrawerLayout.closeDrawer(Gravity.END);
                        return true;

                    case R.id.profile:
                        Intent p = new Intent(Rine_home.this, User_dash.class);
                        startActivity(p);
                        mDrawerLayout.closeDrawer(Gravity.END);
                        return true;



                }
                return false;
            }
        });


        products_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Rine_home.this, Products_Activity.class);
                startActivity(i);
            }
        });

        sucscription_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent subs = new Intent(Rine_home.this, Main_subscription.class);
                startActivity(subs);
            }
        });







    }



    public void drawerclick(View view){
        mDrawerLayout.openDrawer(Gravity.END);

    }

    /*public void loadProducts(View view){
        Intent i = new Intent(Rine_home.this, Products_Activity.class);
        startActivity(i);
    }*/

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

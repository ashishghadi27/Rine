package com.asg.ashish.rine;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;

public class Rine_home extends AppCompatActivity  {
    private DrawerLayout mDrawerLayout;
    public Button products_button, sucscription_button;
    FirebaseAuth mAuth;
    private Navi_drawer navi_drawer;
    private NavigationView navigationView;
    private ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rine_home);
        mDrawerLayout = findViewById(R.id.navi);
        products_button = findViewById(R.id.productsbutton);
        sucscription_button = findViewById(R.id.subscriptionbutton);
        mAuth = FirebaseAuth.getInstance();
        navigationView = findViewById(R.id.nav_view);
        imageView = findViewById(R.id.imageid);

        navi_drawer = new Navi_drawer();
        navi_drawer.nav(mDrawerLayout, navigationView);
        SharedPreferences preferences = getSharedPreferences("profile", MODE_PRIVATE);
        String id = preferences.getString("email", "");
        Log.v("EMAIL IN RINEHOME",""+id);
        Background_Worker background_worker = new Background_Worker(this);
        background_worker.execute("getid", id);
        //the device token is for moto g4 play:
        //fv_8o8RMyBY:APA91bHD3GRvyRF47rR8fetTHIXqmriVNtRY9e4gYy6u0ijRX04liNBOhUDoacdHr665guIpWh7l-2XBzNLdBmK40QFAqLXWojdRd3uBW4pvj0gnu__AK3XT7z2IRXDYvB_pp88p5qBEq6UOvQJgw5-G7zZ95aJnvQ
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Rine_home.this, Products_Activity.class);
                startActivity(i);
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




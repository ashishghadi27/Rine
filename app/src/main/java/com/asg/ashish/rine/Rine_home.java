package com.asg.ashish.rine;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NotificationCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import java.util.Map;

public class Rine_home extends AppCompatActivity  {
    private DrawerLayout mDrawerLayout;
    public Button products_button, sucscription_button;
    FirebaseAuth mAuth;
    private Navi_drawer navi_drawer;
    private NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rine_home);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.navi);
        products_button = findViewById(R.id.productsbutton);
        sucscription_button = findViewById(R.id.subscriptionbutton);
        mAuth = FirebaseAuth.getInstance();
        navigationView = findViewById(R.id.nav_view);
        navi_drawer = new Navi_drawer();
        navi_drawer.nav(mDrawerLayout, navigationView);
        //the device token is for moto g4 play:
        //fv_8o8RMyBY:APA91bHD3GRvyRF47rR8fetTHIXqmriVNtRY9e4gYy6u0ijRX04liNBOhUDoacdHr665guIpWh7l-2XBzNLdBmK40QFAqLXWojdRd3uBW4pvj0gnu__AK3XT7z2IRXDYvB_pp88p5qBEq6UOvQJgw5-G7zZ95aJnvQ


        /*NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                switch (item.getItemId()) {
                    // Respond to the action bar's Up/Home button
                    case R.id.navProducts:
                        Intent a = new Intent(getApplicationContext(), Products_Activity.class);
                        startActivity(a);
                        item.setChecked(false);
                        mDrawerLayout.closeDrawer(Gravity.END);
                        return true;

                    case R.id.navcart:
                        Intent x = new Intent(getApplicationContext(), Cart_Activity.class);
                        startActivity(x);
                        mDrawerLayout.closeDrawer(Gravity.END);
                        return true;

                    case R.id.profile:
                        Intent p = new Intent(getApplicationContext(), User_dash.class);
                        startActivity(p);
                        mDrawerLayout.closeDrawer(Gravity.END);
                        return true;

                    case R.id.logout:
                        if(AccessToken.getCurrentAccessToken() == null){
                            FirebaseAuth.getInstance().signOut();
                            SharedPreferences sharedPreferences = getSharedPreferences("Issignedin", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("verify", null);
                            editor.apply();
                            Intent i = new Intent(getApplicationContext(), SignupActivity.class);
                            startActivity(i);
                            finish();

                        }

                        else {
                            LoginManager.getInstance().logOut();
                            AccessToken.setCurrentAccessToken(null);
                            Intent i = new Intent(getApplicationContext(), SignupActivity.class);
                            startActivity(i);
                            finish();
                        }





                }
                return false;
            }
        });*/


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




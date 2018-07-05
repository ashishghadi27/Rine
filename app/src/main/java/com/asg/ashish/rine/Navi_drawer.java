package com.asg.ashish.rine;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;

import static android.content.Context.MODE_PRIVATE;

public class Navi_drawer  {
    private DrawerLayout mDrawerLayout;
    private NavigationView navigationView;
    String pic="";
    SharedPreferences preferences;

    public void nav(DrawerLayout mDrawerLayout, NavigationView navigationView) {

        final Context context = mDrawerLayout.getContext();
        preferences = context.getSharedPreferences("profile", MODE_PRIVATE);
        final View headerLayout = navigationView.inflateHeaderView(R.layout.header);
        ImageView profilepic = headerLayout.findViewById(R.id.profilepicdrawer);
        pic = preferences.getString("profilepic","--");


        navigationView.bringToFront();



        if(pic.equals("--") || pic.equals("")){
            profilepic.setImageResource(R.drawable.sampleprofile);
        }
        else
            Glide.with(context).load(pic).override(1000,500).into(profilepic);
        final DrawerLayout finalMDrawerLayout = mDrawerLayout;
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setChecked(true);
                switch (item.getItemId()) {
                    // Respond to the action bar's Up/Home button
                    case R.id.navProducts:
                        Intent a = new Intent(context, Products_Activity.class);
                        context.startActivity(a);
                        item.setChecked(false);
                        finalMDrawerLayout.closeDrawer(Gravity.END);
                        return true;

                    case R.id.navcart:
                        Intent x = new Intent(context, Cart_Activity.class);
                        context.startActivity(x);
                        finalMDrawerLayout.closeDrawer(Gravity.END);
                        return true;

                    case R.id.profile:
                        Intent p = new Intent(context, User_dash.class);
                        context.startActivity(p);
                        finalMDrawerLayout.closeDrawer(Gravity.END);
                        return true;



                    case R.id.logout:
                        if(AccessToken.getCurrentAccessToken() == null){
                            FirebaseAuth.getInstance().signOut();
                            SharedPreferences sharedPreferences = context.getSharedPreferences("Issignedin", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("verify", null);
                            editor.apply();
                            Intent i = new Intent(context, SignupActivity.class);
                            context.startActivity(i);
                            ((Activity)context).finish();

                        }

                        else {
                            LoginManager.getInstance().logOut();
                            AccessToken.setCurrentAccessToken(null);
                            Intent i = new Intent(context, SignupActivity.class);
                            context.startActivity(i);
                            ((Activity)context).finish();
                        }
                        finalMDrawerLayout.closeDrawer(Gravity.END);
                        return true;

                    case R.id.plans:
                        Intent intent = new Intent(context, Main_subscription.class);
                        context.startActivity(intent);
                        finalMDrawerLayout.closeDrawer(Gravity.END);
                        return true;





                }
                return false;
            }
        });



    }
}

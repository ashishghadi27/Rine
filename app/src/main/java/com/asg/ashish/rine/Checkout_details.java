package com.asg.ashish.rine;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;

public class Checkout_details extends AppCompatActivity {

    private Navi_drawer navi_drawer;
    private NavigationView navigationView;
    private DrawerLayout mDrawerLayout;
    private  String mail, name;
    private EditText namefield, nofield, address, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_details);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.navi);
        navigationView = findViewById(R.id.nav_view);
        navi_drawer = new Navi_drawer();
        navi_drawer.nav(mDrawerLayout, navigationView);
        SharedPreferences preferences = getSharedPreferences("profile", MODE_PRIVATE);
        mail = preferences.getString("email","");
        name = preferences.getString("name","");
        namefield = findViewById(R.id.namefield);
        nofield = findViewById(R.id.numberfield);
        address = findViewById(R.id.address);
        email = findViewById(R.id.mailfield);
        namefield.setText(name);
        email.setText(mail);

    }

    public void drawerclick(View view){
        mDrawerLayout.openDrawer(Gravity.END);

    }

    public void tocart(View view){
        Intent i = new Intent(this, Cart_Activity.class);
        startActivity(i);
    }


    public void back(View view){
        this.onBackPressed();
    }
}

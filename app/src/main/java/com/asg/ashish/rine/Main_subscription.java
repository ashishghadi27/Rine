package com.asg.ashish.rine;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Main_subscription extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private Button six_button, twelve_button, eighteen_button;
    TextView counter1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_subscription);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.navi);
        six_button = findViewById(R.id.button_6pack);
        twelve_button = findViewById(R.id.button_12pack);
        eighteen_button = findViewById(R.id.button_18pack);

        six_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main_subscription.this, Subscription_products.class);
                intent.putExtra("quantity", "6");
                startActivity(intent);

            }
        });

        twelve_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main_subscription.this, Subscription_products.class);
                intent.putExtra("quantity", "12");
                startActivity(intent);
            }
        });

        eighteen_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Main_subscription.this, Subscription_products.class);
                intent.putExtra("quantity", "18");
                startActivity(intent);
            }
        });
    }

    public void drawerclick(View view){
        mDrawerLayout.openDrawer(Gravity.RIGHT);

    }


    public void make_plan_button(View view){

        CharSequence engines[] = new CharSequence[] {"Single Delivery", "Multiple Delivery"};

        final AlertDialog.Builder builder = new AlertDialog.Builder(Main_subscription.this);
        builder.setTitle("Select a Delivery Option");
        builder.setItems(engines, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(which == 0) {
                    Intent intent = new Intent(Main_subscription.this, Subscription_products.class);
                    intent.putExtra("quantity", "single");
                    startActivity(intent);

                }
                else if (which == 1){
                    Intent intent = new Intent(Main_subscription.this, Subscription_products.class);
                    intent.putExtra("quantity", "multiple");
                    startActivity(intent);
                }


            }
        });
        builder.show();


    }

    public void tocart(View view){
        Intent i = new Intent(this, Cart_Activity.class);
        startActivity(i);
    }


    public void back(View view){
        this.onBackPressed();
    }


}

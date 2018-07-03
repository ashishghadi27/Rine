package com.asg.ashish.rine;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import Database.DBHandler;
import Model.Cart_list;

public class Per_pro_subscription extends AppCompatActivity {
    private ImageView img_six;
    private CardView cv;
    private DrawerLayout mDrawerLayout;
    TextView counter1;
    int count1=6;
    int single_delivery_id = 0;
    int multiple_delivery_id = 0;
    DBHandler dbHandler;
    String texttitle, link;
    private Navi_drawer navi_drawer;
    private NavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_per_pro_subscription);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.navi);
        navigationView = findViewById(R.id.nav_view);
        navi_drawer = new Navi_drawer();
        navi_drawer.nav(mDrawerLayout, navigationView);
        counter1 = (TextView)findViewById(R.id.bluecountertext);
        Intent intent = getIntent();
        texttitle = intent.getStringExtra("title");
        link = intent.getStringExtra("link");
        img_six = (ImageView)findViewById(R.id.image_subs);
        cv = findViewById(R.id.card_subs);
        Glide.with(this).load(link).override(1000,500).into(img_six);
        dbHandler = new DBHandler(this, null, null, 2);


        if(texttitle.contains("Blueberry")){
            cv.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#1565c0")));
            single_delivery_id = 12950;
            multiple_delivery_id = 12951;
        }
        else if(texttitle.contains("Strawberry")){

            cv.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#f50057")));
            single_delivery_id = 12952;
            multiple_delivery_id = 12953;
        }
        else if(texttitle.contains("Peanut")){

            cv.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ffab00")));
            single_delivery_id = 12954;
            multiple_delivery_id = 12955;
        }
        else if(texttitle.contains("Chocolate")){

            cv.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4e342e")));
            single_delivery_id = 12958;
            multiple_delivery_id = 12665;
        }
        else {

            cv.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#1de9b6")));
            single_delivery_id = 12956;
            multiple_delivery_id = 12957;
        }


    }


    public void drawerclick(View view){
        mDrawerLayout.openDrawer(Gravity.RIGHT);

    }


    public void increase_count1(View view){
        count1 = count1 + 1;
        counter1.setText(""+count1);
    }

    public void decrease_count1(View view){
        if(count1!=6)
        {
            count1 = count1 - 1;
            counter1.setText(""+count1);
        }
        else count1 = 6;

    }

    public void six_pack_order(View view){
        CharSequence engines[] = new CharSequence[] {"Single Delivery"};

        final AlertDialog.Builder builder = new AlertDialog.Builder(Per_pro_subscription.this);
        builder.setTitle("Select a Delivery Option");
        builder.setItems(engines, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(which == 0) {
                    Cart_list item = new Cart_list(Integer.toString(single_delivery_id), texttitle, link, Integer.toString(6));
                    dbHandler.addProduct(item);

                }


            }
        });
        builder.show();

    }

    public void twelve_pack_order(View view){

        CharSequence engines[] = new CharSequence[] {"Single Delivery", "Multiple Delivery"};

        final AlertDialog.Builder builder = new AlertDialog.Builder(Per_pro_subscription.this);
        builder.setTitle("Select a Delivery Option");
        builder.setItems(engines, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(which == 0) {
                    Cart_list item = new Cart_list(Integer.toString(single_delivery_id), texttitle, link, Integer.toString(12));
                    dbHandler.addProduct(item);

                }
                else if (which == 1){
                    Cart_list item = new Cart_list(Integer.toString(multiple_delivery_id), texttitle, link, Integer.toString(12));
                    dbHandler.addProduct(item);
                }


            }
        });
        builder.show();

    }

    public void eighteen_pack_order(View view){

        CharSequence engines[] = new CharSequence[] {"Single Delivery", "Multiple Delivery"};

        final AlertDialog.Builder builder = new AlertDialog.Builder(Per_pro_subscription.this);
        builder.setTitle("Select a Delivery Option");
        builder.setItems(engines, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(which == 0) {
                    Cart_list item = new Cart_list(Integer.toString(single_delivery_id), texttitle, link, Integer.toString(18));
                    dbHandler.addProduct(item);

                }
                else if (which == 1){
                    Cart_list item = new Cart_list(Integer.toString(multiple_delivery_id), texttitle, link,  Integer.toString(18));
                    dbHandler.addProduct(item);
                }


            }
        });
        builder.show();

    }

    public void plan_order(View view){

        CharSequence engines[] = new CharSequence[] {"Single Delivery", "Multiple Delivery"};

        final AlertDialog.Builder builder = new AlertDialog.Builder(Per_pro_subscription.this);
        builder.setTitle("Select a Delivery Option");
        builder.setItems(engines, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(which == 0) {
                    Cart_list item = new Cart_list(Integer.toString(single_delivery_id), texttitle, link,counter1.getText().toString());
                    dbHandler.addProduct(item);

                }
                else if (which == 1){
                    Cart_list item = new Cart_list(Integer.toString(multiple_delivery_id), texttitle, link, counter1.getText().toString());
                    dbHandler.addProduct(item);
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

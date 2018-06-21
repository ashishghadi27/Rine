package com.asg.ashish.rine;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import Database.DBHandler;
import Model.Cart_list;

public class Subscription_products extends AppCompatActivity {
    private ImageView chocolate, blueberry, strawberry, peanut, assorted;
    private DrawerLayout mDrawerLayout;
    static int quantity = 0;
    private TextView action_title;
    TextView counter1, counter2, counter3, counter4, counter5;
    int count1=1, count2 = 1, count3 = 1, count4 = 1, count5 = 1;
    RelativeLayout chocolatel, blueberryl, peanutl, strawberryl, assortedl;
    String check;
    DBHandler dbHandler;
    View parentLayout;
    String choco_image = "http://www.rinebars.com/wp-content/uploads/2018/01/C1.jpg", blueberry_image = "http://www.rinebars.com/wp-content/uploads/2018/01/b1.jpg";
    String strawberry_image = "http://www.rinebars.com/wp-content/uploads/2018/01/s1.jpg", peanut_image = "http://www.rinebars.com/wp-content/uploads/2018/01/p1.jpg", assorted_image = "http://www.rinebars.com/wp-content/uploads/2018/03/assorted.png";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscription_products);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.navi);
        counter1 = (TextView)findViewById(R.id.chocolatecountertext);
        counter2 = findViewById(R.id.bluecountertext);
        counter3 = findViewById(R.id.peanutcountertext);
        counter4 = findViewById(R.id.strawberrycountertext);
        counter5 = findViewById(R.id.assortedcountertext);

        dbHandler = new DBHandler(this, null, null, 2);

        parentLayout = findViewById(android.R.id.content);

        chocolate = findViewById(R.id.image_subscription_chocolate);
        blueberry = findViewById(R.id.image_subscription_blueberry);
        strawberry = findViewById(R.id.image_subscription_strawberry);
        peanut = findViewById(R.id.image_subscription_peanut);
        assorted = findViewById(R.id.image_subscription_assorted);
        action_title = findViewById(R.id.header_title);
        Intent intent = getIntent();
        check = intent.getStringExtra("quantity");
        if(!(check.equals("single") || check.equals("multiple"))) {
            quantity = Integer.parseInt(intent.getStringExtra("quantity"));
            String action_header = intent.getStringExtra("quantity") + "  Pack  Plans";
            action_title.setText(action_header);
            chocolatel = findViewById(R.id.chocolate_layout);
            blueberryl = findViewById(R.id.blueberry_layout);
            peanutl = findViewById(R.id.peanut_layout);
            strawberryl = findViewById(R.id.strawberry_layout);
            assortedl = findViewById(R.id.assorted_layout);
            chocolatel.setVisibility(View.GONE);
            blueberryl.setVisibility(View.GONE);
            peanutl.setVisibility(View.GONE);
            strawberryl.setVisibility(View.GONE);
            assortedl.setVisibility(View.GONE);
        }
        else action_title.setText("Make  a  Plan");

        Glide.with(this).load(choco_image).override(1000,500).into(chocolate);
        Glide.with(this).load(blueberry_image).override(1000,500).into(blueberry);
        Glide.with(this).load(strawberry_image).override(1000,500).into(strawberry);
        Glide.with(this).load(peanut_image).override(1000,500).into(peanut);
        Glide.with(this).load(assorted_image).override(1000,500).into(assorted);
    }

    public void drawerclick(View view){
        mDrawerLayout.openDrawer(Gravity.RIGHT);

    }


    public void increase_count1(View view){
        count1 = count1 + 1;
        counter1.setText(""+count1);
    }

    public void decrease_count1(View view){
        if(count1!=1)
        {
            count1 = count1 - 1;
            counter1.setText(""+count1);
        }
        else count1 = 1;

    }

    public void increase_count2(View view){
        count2 = count2 + 1;
        counter2.setText(""+count2);
    }

    public void decrease_count2(View view){
        if(count2!=1)
        {
            count2 = count2 - 1;
            counter2.setText(""+count2);
        }
        else count2 = 1;

    }

    public void increase_count3(View view){
        count3 = count3 + 1;
        counter3.setText(""+count3);
    }

    public void decrease_count3(View view){
        if(count3!=1)
        {
            count3 = count3 - 1;
            counter3.setText(""+count3);
        }
        else count3 = 1;

    }

    public void increase_count4(View view){
        count4 = count4 + 1;
        counter4.setText(""+count4);
    }

    public void decrease_count4(View view){
        if(count4!=1)
        {
            count4 = count4 - 1;
            counter4.setText(""+count4);
        }
        else count4 = 1;

    }

    public void increase_count5(View view){
        count5 = count5 + 1;
        counter5.setText(""+count5);
    }

    public void decrease_count5(View view){
        if(count5!=1)
        {
            count5 = count5 - 1;
            counter5.setText(""+count5);
        }
        else count5 = 1;

    }

    public void snackbar(){
        Snackbar.make(parentLayout, "Item added to cart", Snackbar.LENGTH_LONG)
                .setAction("CLOSE", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                })
                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light ))
                .show();
    }

    public void choco_button(View view){

        final String choco_single = "12958";
        final String choco_multiple = "12665";
        if(!(check.equals("single") || check.equals("multiple"))) {
            CharSequence engines[] = new CharSequence[]{"Single Delivery", "Multiple Delivery"};

            final AlertDialog.Builder builder = new AlertDialog.Builder(Subscription_products.this);
            builder.setTitle("Select a Delivery Option");
            builder.setItems(engines, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    if (which == 0) {

                        Cart_list item = new Cart_list(choco_single, "Mighty Chocolate", choco_image, Integer.toString(quantity));
                        dbHandler.addProduct(item);

                        snackbar();
                    } else if (which == 1) {
                        Cart_list item = new Cart_list(choco_multiple, "Mighty Chocolate", choco_image, Integer.toString(quantity));
                        dbHandler.addProduct(item);

                        snackbar();
                    }


                }
            });
            builder.show();
        }
        else {
            if (check.equals("single")){
                Cart_list item = new Cart_list(choco_single, "Mighty Chocolate", choco_image, counter1.getText().toString());
                dbHandler.addProduct(item);
            }
            else {
                Cart_list item = new Cart_list(choco_single, "Mighty Chocolate", choco_image, counter1.getText().toString());
                dbHandler.addProduct(item);
            }
            snackbar();
        }


    }

    public void blueberry_button(View view){

        final String blueberry_single = "12950";
        final String blueberry_multiple = "12951";
        if(!(check.equals("single") || check.equals("multiple"))) {
            CharSequence engines[] = new CharSequence[]{"Single Delivery", "Multiple Delivery"};

            final AlertDialog.Builder builder = new AlertDialog.Builder(Subscription_products.this);
            builder.setTitle("Select a Delivery Option");
            builder.setItems(engines, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    if (which == 0) {

                        Cart_list item = new Cart_list(blueberry_single, "Blueberry Bolt", blueberry_image, Integer.toString(quantity));
                        dbHandler.addProduct(item);
                        snackbar();
                    } else if (which == 1) {
                        Cart_list item = new Cart_list(blueberry_multiple, "Blueberry Bolt", blueberry_image, Integer.toString(quantity));
                        dbHandler.addProduct(item);
                        snackbar();
                    }


                }
            });
            builder.show();
        }
        else {
            if (check.equals("single")){
                Cart_list item = new Cart_list(blueberry_single, "Blueberry Bolt", blueberry_image, counter2.getText().toString());
                dbHandler.addProduct(item);
            }
            else {
                Cart_list item = new Cart_list(blueberry_multiple, "Blueberry Bolt", blueberry_image, counter2.getText().toString());
                dbHandler.addProduct(item);
            }
            snackbar();
        }

    }

    public void strawberry_button(View view){

        final String strawberry_single = "12952";
        final String strawberry_multiple = "12953";
        if(!(check.equals("single") || check.equals("multiple"))) {
            CharSequence engines[] = new CharSequence[]{"Single Delivery", "Multiple Delivery"};

            final AlertDialog.Builder builder = new AlertDialog.Builder(Subscription_products.this);
            builder.setTitle("Select a Delivery Option");
            builder.setItems(engines, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    if (which == 0) {

                        Cart_list item = new Cart_list(strawberry_single, "Nutty Strawberry", strawberry_image, Integer.toString(quantity));
                        dbHandler.addProduct(item);
                        snackbar();
                    } else if (which == 1) {
                        Cart_list item = new Cart_list(strawberry_multiple, "Nutty Strawberry", strawberry_image, Integer.toString(quantity));
                        dbHandler.addProduct(item);
                        snackbar();
                    }


                }
            });
            builder.show();
        }
        else {
            if (check.equals("single")){
                Cart_list item = new Cart_list(strawberry_single, "Nutty Strawberry", strawberry_image, counter4.getText().toString());
                dbHandler.addProduct(item);
            }
            else {
                Cart_list item = new Cart_list(strawberry_multiple, "Nutty Strawberry", strawberry_image, counter4.getText().toString());
                dbHandler.addProduct(item);
            }
            snackbar();
        }

    }

    public void peanut_button(View view){

        final String peanut_single = "12954";
        final String peanut_multiple = "12955";
        if(!(check.equals("single") || check.equals("multiple"))) {
            CharSequence engines[] = new CharSequence[]{"Single Delivery", "Multiple Delivery"};

            final AlertDialog.Builder builder = new AlertDialog.Builder(Subscription_products.this);
            builder.setTitle("Select a Delivery Option");
            builder.setItems(engines, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    if (which == 0) {

                        Cart_list item = new Cart_list(peanut_single, "Peanut Butter Crunch", peanut_image, Integer.toString(quantity));
                        dbHandler.addProduct(item);
                        snackbar();
                    } else if (which == 1) {
                        Cart_list item = new Cart_list(peanut_multiple, "Peanut Butter Crunch", peanut_image, Integer.toString(quantity));
                        dbHandler.addProduct(item);
                        snackbar();
                    }


                }
            });
            builder.show();
        }
        else {
            if (check.equals("single")){
                Cart_list item = new Cart_list(peanut_single, "Peanut Butter Crunch", peanut_image, counter3.getText().toString());
                dbHandler.addProduct(item);
            }
            else {
                Cart_list item = new Cart_list(peanut_multiple, "Peanut Butter Crunch", peanut_image, counter3.getText().toString());
                dbHandler.addProduct(item);
            }
            snackbar();
        }


    }

    public void assorted_button(View view){

        final String assorted_single = "12956";
        final String assorted_multiple = "12957";
        if(!(check.equals("single") || check.equals("multiple"))) {
            CharSequence engines[] = new CharSequence[]{"Single Delivery", "Multiple Delivery"};

            final AlertDialog.Builder builder = new AlertDialog.Builder(Subscription_products.this);
            builder.setTitle("Select a Delivery Option");
            builder.setItems(engines, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    if (which == 0) {

                        Cart_list item = new Cart_list(assorted_single, "Assorted Box", assorted_image, Integer.toString(quantity));
                        dbHandler.addProduct(item);
                        snackbar();
                    } else if (which == 1) {
                        Cart_list item = new Cart_list(assorted_multiple, "Assorted Box", assorted_image, Integer.toString(quantity));
                        dbHandler.addProduct(item);
                        snackbar();
                    }


                }
            });
            builder.show();
        }
        else {
            if (check.equals("single")){
                Cart_list item = new Cart_list(assorted_single, "Assorted Box", assorted_image, counter5.getText().toString());
                dbHandler.addProduct(item);
            }
            else {
                Cart_list item = new Cart_list(assorted_multiple, "Assorted Box", assorted_image, counter5.getText().toString());
                dbHandler.addProduct(item);
            }
            snackbar();
        }

    }

    public void tocart(View view){
        Intent i = new Intent(this, Cart_Activity.class);
        startActivity(i);
    }


    public void back(View view){
        this.onBackPressed();
    }


}

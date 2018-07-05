package com.asg.ashish.rine;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.transition.Fade;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import Database.DBHandler;
import Model.Cart_list;

public class blueberry extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    TextView counter1, titletext, info1, info4;
    ImageView img;
    int count1=1;
    Button subscribe;
    private String productid, texttitle, link, TAG = "CHECK:", jsonur = "https://www.rinebars.com/wp-json/wp/v2/product/", info, para, price;
    DBHandler dbHandler;
    CardView cv;
    FloatingActionButton cart;
    private Navi_drawer navi_drawer;
    private NavigationView navigationView;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blueberry);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.navi);
        navigationView = findViewById(R.id.nav_view);
        navi_drawer = new Navi_drawer();
        navi_drawer.nav(mDrawerLayout, navigationView);
        counter1 = (TextView)findViewById(R.id.bluecountertext);
        titletext = (TextView)findViewById(R.id.bluetext);
        img = (ImageView)findViewById(R.id.productimg);
        subscribe = (Button)findViewById(R.id.subscribe);
        cv = (CardView)findViewById(R.id.card_view);
        cart = (FloatingActionButton) findViewById(R.id.floatproduct1);

        Intent i = getIntent();
        productid = i.getStringExtra("productid");
        texttitle = i.getStringExtra("title");
        link = i.getStringExtra("link");
        titletext.setText(texttitle);
        Glide.with(this).load(link).override(1000,500).into(img);
        jsonur = jsonur + productid;

        info1 = (TextView)findViewById(R.id.info1);
        info4 = (TextView)findViewById(R.id.info4);

        Fade fade = new Fade();
        View decor = getWindow().getDecorView();
        fade.excludeTarget(decor.findViewById(R.id.action_bar_container), true);
        fade.excludeTarget(android.R.id.statusBarBackground,true);
        fade.excludeTarget(android.R.id.navigationBarBackground,true);
        fade.excludeTarget(R.id.linear,true);

        dbHandler = new DBHandler(this, null, null, 2);
        if(texttitle.contains("Blueberry")){
            cv.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#1565c0")));
            subscribe.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#0d47a1")));
            cart.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#0d47a1")));
        }
        else if(texttitle.contains("Strawberry")){

            cv.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#f50057")));
            subscribe.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#c51162")));
            cart.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#c51162")));
        }
        else if(texttitle.contains("Peanut")){

            cv.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ffab00")));
            subscribe.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ff8f00")));
            cart.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#ff8f00")));
        }
        else if(texttitle.contains("Chocolate")){

            cv.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#4e342e")));
            subscribe.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#3e2723")));
            cart.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#3e2723")));
        }
        else {

            cv.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#1de9b6")));
            subscribe.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#28b180")));
            cart.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#28b180")));
        }
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
        progressDialog.setCancelable(true);
        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                onBackPressed();
            }
        });
        final StringRequest stringRequest = new StringRequest(Request.Method.GET, jsonur,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        try {

                            JSONObject obj = new JSONObject(response);
                            String content = obj.getJSONObject("excerpt").getString("rendered");
                            Document document = Jsoup.parse(content);
                            Elements element = document.select("h4");
                            Log.v(TAG, document.toString());
                            Log.v(TAG, element.toString());
                            info = element.toString();
                            element = document.select("p");
                            para = element.toString();
                            Log.v(TAG, element.toString());

                            info = info.replaceAll("<b>","\n");
                            info = info.replace("</b>","\b");
                            info = info.replaceAll("\\<[^>]*>", "");
                            para = para.replace("&nbsp;","");
                            para = para.replaceAll("\\<[^>]*>","");
                            info1.setText(info);
                            price = info;
                            info4.setText(para);
                            progressDialog.dismiss();
                            save_price();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.v(TAG, "Some json exception");
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);







    }
    public void drawerclick(View view){
        mDrawerLayout.openDrawer(Gravity.RIGHT);

    }

    public void save_price(){

            //Log.v("INFO TEXT", price);
            String product = titletext.getText().toString();
            java.util.regex.Pattern p = java.util.regex.Pattern.compile("-?\\d+");
            java.util.regex.Matcher m = p.matcher(price);
            if(m.find())
            {
                String temp_= m.group();
                Log.v("PRICE CHECK", temp_);
                SharedPreferences sharedPreferences = getSharedPreferences("price",MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(product, temp_);
                editor.apply();
            }




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


    public void tocart(View view){
        Intent i = new Intent(this, Cart_Activity.class);
        startActivity(i);
    }

    public void subs_activity(View view){
        Intent intent = new Intent(this, Per_pro_subscription.class);
        intent.putExtra("link", link);
        intent.putExtra("title", texttitle);
        startActivity(intent);
    }




    public void back(View view){
        this.onBackPressed();
    }

    public void add_to_cart(View view) {

        Cart_list item = new Cart_list(productid, texttitle, link, (String) counter1.getText());
        dbHandler.addProduct(item);
        Toast.makeText(blueberry.this, "ITEM ADDED", Toast.LENGTH_SHORT).show();
    }
}

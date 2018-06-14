package com.asg.ashish.rine;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
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
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


import Database.DBHandler;
import Model.Cart_list;

public class blueberry extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    TextView counter1, titletext, info1, info4;
    ImageView img;
    int count1=1;
    String Cartdata = "";
    //private List<Cart_list> listItems;
    SharedPreferences sharedPreferences;
    String productid, texttitle, link, TAG = "CHECK:", jsonur = "https://www.rinebars.com/wp-json/wp/v2/product/", info, para;
    DBHandler dbHandler;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blueberry);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.navi);
        counter1 = (TextView)findViewById(R.id.bluecountertext);
        titletext = (TextView)findViewById(R.id.bluetext);
        img = (ImageView)findViewById(R.id.productimg);
        sharedPreferences = getSharedPreferences("cart", MODE_PRIVATE);
        Cartdata = sharedPreferences.getString("cartdata", "");
        Intent i = getIntent();
        productid = i.getStringExtra("productid");
        texttitle = i.getStringExtra("title");
        link = i.getStringExtra("link");
        titletext.setText(texttitle);
        Glide.with(this).load(link).override(1000,500).into(img);
        jsonur = jsonur + productid;
        info1 = (TextView)findViewById(R.id.info1);
        info4 = (TextView)findViewById(R.id.info4);
        //listItems = new ArrayList<>();
        dbHandler = new DBHandler(this, null, null, 2);
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
                            info4.setText(para);
                            progressDialog.dismiss();

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




    public void back(View view){
        this.onBackPressed();
    }

    public void add_to_cart(View view){

        Cart_list item = new Cart_list( productid,texttitle, link, (String) counter1.getText());
        dbHandler.addProduct(item);
        Toast.makeText(blueberry.this,"ITEM ADDED",Toast.LENGTH_SHORT).show();
        /*listItems.add(item);
        String json = new Gson().toJson(listItems);
        Log.v("STRING IS: ", json);
        Cartdata = Cartdata + json + ",";
        Log.v("CARDDATA IS: ", Cartdata);
        SharedPreferences Preferences = getSharedPreferences("flag",MODE_PRIVATE);
        SharedPreferences.Editor edit = Preferences.edit();
        edit.putInt("count",0);
        edit.apply();
        sharedPreferences = getSharedPreferences("cart", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("cartdata", Cartdata);
        editor.apply();


        Toast.makeText(blueberry.this, Cartdata, Toast.LENGTH_SHORT ).show();*/



    }

    /*@Override
    protected void onResume() {
        super.onResume();
        Cartdata = "";
        sharedPreferences = getSharedPreferences("cart", MODE_PRIVATE);
        Cartdata = sharedPreferences.getString("cartdata", "");
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        Cartdata = "";
        sharedPreferences = getSharedPreferences("cart", MODE_PRIVATE);
        Cartdata = sharedPreferences.getString("cartdata", "");
    }*/
}

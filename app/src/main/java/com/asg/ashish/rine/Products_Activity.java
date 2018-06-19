package com.asg.ashish.rine;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Fade;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Products_Activity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    String jsonurl = "https://rinebars.com/wp-json/wp/v2/product?per_page=30";
    private RecyclerView recyclerView;
    private List<Products_list> listItems;
    private Products_adapter adapter;
    String TAG = "Check ADAPTER", id;
    SwipeRefreshLayout mSwipeRefresh;
    private String img;

    public String resp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.navi);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mSwipeRefresh = (SwipeRefreshLayout)findViewById(R.id.swipeRefreshLayout);
        listItems = new ArrayList<>();

        Fade fade = new Fade();
        View decor = getWindow().getDecorView();
        fade.excludeTarget(decor.findViewById(R.id.action_bar_container), true);
        fade.excludeTarget(android.R.id.statusBarBackground,true);
        fade.excludeTarget(android.R.id.navigationBarBackground,true);
        fade.excludeTarget(R.id.lay,true);


        if(isNetworkAvailable()){
            loadRecyclerViewData();
        }
        else Toast.makeText(Products_Activity.this, "Please Check Your Connection", Toast.LENGTH_SHORT).show();
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(isNetworkAvailable()){
                    listItems.clear();
                    loadRecyclerViewData();
                    mSwipeRefresh.setRefreshing(false);
                }

                else
                    Toast.makeText(Products_Activity.this, "Please Check Your Connection", Toast.LENGTH_SHORT).show();

            }
        });



}

    private void loadRecyclerViewData() {
        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        final ProgressDialog progressDialog = new ProgressDialog(this);
        //progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        progressDialog.setCancelable(true);
        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                onBackPressed();
            }
        });

        final StringRequest stringRequest = new StringRequest(Request.Method.GET, jsonurl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.v(TAG, response);

                        try {

                            JSONArray array = new JSONArray(response);
                            for(int i=0; i<array.length();i++){
                                final JSONObject o = array.getJSONObject(i);
                                if(!((o.getJSONObject("title").getString("rendered")).contains("Subscription"))){

                                    JSONArray x = o.getJSONObject("_links").getJSONArray("wp:featuredmedia");
                                    JSONObject y = x.getJSONObject(0);
                                    String newjsonurl = y.getString("href");
                                    Log.v("URL IS", newjsonurl);
                                    StringRequest stringRequest1 = new StringRequest(Request.Method.GET, newjsonurl, new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response1) {
                                            try {
                                                Log.v(TAG, "Here in STRING REQ block");
                                                JSONObject object= new JSONObject(response1);
                                                object = object.getJSONObject("guid");

                                                img = object.getString("rendered");
                                                Log.v("IMAGE CHECKER", img);
                                                Products_list item = new Products_list(o.getJSONObject("title").getString("rendered"),  o.getString("id"), img);
                                                Log.v("IMAGE CHECKER OUTSIDE", img);
                                                listItems.add(item);
                                                progressDialog.dismiss();
                                                adapter = new Products_adapter(listItems, getApplicationContext());
                                                recyclerView.setAdapter(adapter);

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                                Log.v("IMAGE CHECKER OUTSIDE", "Image exception");
                                            }

                                        }
                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {

                                        }
                                    });
                                    /*RequestQueue requestQueue1 = Volley.newRequestQueue(Products_Activity.this);
                                    requestQueue1.add(stringRequest1);*/
                                    requestQueue.add(stringRequest1);

                                    Log.v(TAG, "Here in the for loop block");
                                    Log.v(TAG, "THE ID IS "+o.getInt("id"));
                                }

                            }


                            Log.v(TAG, "Here in the ADAPTER block");
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

        requestQueue.add(stringRequest);


    }


    public void drawerclick(View view){
        mDrawerLayout.openDrawer(Gravity.RIGHT);

    }



    public void tocart(View view){
        Intent i = new Intent(this, Cart_Activity.class);
        startActivity(i);
    }

    public void back(View view){
        this.onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


}

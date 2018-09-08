package com.asg.ashish.rine;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Adapter.Orders_Adapter;
import Model.Orders_list;

public class Orders extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private RecyclerView recyclerView;
    private List<Orders_list> listItems;
    private Orders_Adapter adapter;
    private SwipeRefreshLayout mSwipeRefresh;
    private Navi_drawer navi_drawer;
    private NavigationView navigationView;
    private SharedPreferences sharedPreferences;
    private String jsonurl = "https://www.rinebars.com/wp-json/wc/v2/orders?consumer_key=ck_143511d1b1fd59f4db330333f1242abf956cbb3c&consumer_secret=cs_bc0d231f97f70c3bb8d20c3ebe919be96b5b0d7c&customer=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        mDrawerLayout = findViewById(R.id.navi);
        navigationView = findViewById(R.id.nav_view);
        navi_drawer = new Navi_drawer();
        navi_drawer.nav(mDrawerLayout, navigationView);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mSwipeRefresh = findViewById(R.id.swipeRefreshLayout);
        SharedPreferences sharedPreferences = getSharedPreferences("User", Context.MODE_PRIVATE);
        String id = sharedPreferences.getString("id", "");
        jsonurl = jsonurl + id;
        Log.v("NEWURL", jsonurl);
        listItems = new ArrayList<>();
        loader();

        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                listItems.clear();
                loader();
                mSwipeRefresh.setRefreshing(false);
            }
        });

    }

    private void loader(){

        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        final ProgressDialog progressDialog = new ProgressDialog(this);
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

                        try {

                            JSONArray array = new JSONArray(response);
                            for (int i = 0; i < array.length(); i++) {
                                    JSONObject jsonObject = array.getJSONObject(i);
                                    String id = jsonObject.getString("id");
                                    String date = jsonObject.getString("date_created");
                                    date = date.replaceAll("T.*", "");
                                    String status = jsonObject.getString("status");
                                    String total = jsonObject.getString("total");
                                    String product = jsonObject.getJSONArray("line_items").toString();
                                    String quantity = "2";
                                    String subtotal = "720";
                                    String payment = jsonObject.getString("payment_method_title");
                                    String addr1 = jsonObject.getJSONObject("billing").getString("address_1");
                                    String addr2 = jsonObject.getJSONObject("billing").getString("address_2");
                                    String city = jsonObject.getJSONObject("billing").getString("city");
                                    String postcode = jsonObject.getJSONObject("billing").getString("postcode");
                                    Orders_list item = new Orders_list(id, date, status, total, product, quantity, subtotal, payment, addr1, addr2, city, postcode);
                                    listItems.add(item);
                                    progressDialog.dismiss();
                                    adapter = new Orders_Adapter(listItems, getApplicationContext());
                                    recyclerView.setAdapter(adapter);
                                }

                            }



                         catch (JSONException e) {
                            e.printStackTrace();

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
}

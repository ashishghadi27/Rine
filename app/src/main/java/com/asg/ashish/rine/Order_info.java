package com.asg.ashish.rine;

import android.app.ProgressDialog;
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
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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

import Adapter.Order_temp_adapter;
import Model.Order_temp_list;

public class Order_info extends AppCompatActivity {
    private String jsonurl, address, city, postcode;
    private TextView address1, city1, postcode1, finaltotal;
    private RecyclerView recyclerView;
    private List<Order_temp_list> listItems;
    private Order_temp_adapter adapter;
    private SwipeRefreshLayout mSwipeRefresh;
    private DrawerLayout mDrawerLayout;
    private Navi_drawer navi_drawer;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_info);
        address1 = findViewById(R.id.address);
        city1 = findViewById(R.id.city);
        postcode1 = findViewById(R.id.postcode);
        mDrawerLayout = findViewById(R.id.navi);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mSwipeRefresh = findViewById(R.id.swipeRefreshLayout);
        navigationView = findViewById(R.id.nav_view);
        navi_drawer = new Navi_drawer();
        navi_drawer.nav(mDrawerLayout, navigationView);
        finaltotal = findViewById(R.id.finaltotal);
        listItems = new ArrayList<>();
        Intent intent = getIntent();
        String orderid = intent.getStringExtra("orderid");
        String ft = intent.getStringExtra("finaltotal");
        SharedPreferences sharedPreferences = getSharedPreferences(orderid, MODE_PRIVATE);
        jsonurl = sharedPreferences.getString("productinfo", "");
        address = sharedPreferences.getString("address1", "") + " " + sharedPreferences.getString("address2", "");
        city = " " + sharedPreferences.getString("city", "");
        postcode = " " + sharedPreferences.getString("postcode", "");
        address = address.replaceAll(",", "\n");
        address = " " + address;
        address1.setText(address);
        city1.setText(city);
        postcode1.setText(postcode);
        finaltotal.setText(ft);
        loadRecyclerViewData();


    }

    private void loadRecyclerViewData() {


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


        try {

            JSONArray array = new JSONArray(jsonurl);
            for (int i = 0; i < array.length(); i++) {
                JSONObject jsonObject = array.getJSONObject(i);

                Order_temp_list item = new Order_temp_list(jsonObject.getString("name"), jsonObject.getString("quantity"), jsonObject.getString("subtotal"));
                // Log.v("IMAGE CHECKER OUTSIDE", img);
                listItems.add(item);
                progressDialog.dismiss();
                adapter = new Order_temp_adapter(listItems, getApplicationContext());
                recyclerView.setAdapter(adapter);
            }


        } catch (JSONException e) {
            e.printStackTrace();


        }
    }

    public void drawerclick(View view){
        mDrawerLayout.openDrawer(Gravity.RIGHT);

    }



    public void tocart(View view){
        Intent i = new Intent(this, Cart_Activity.class);
        startActivity(i);
    }

    public void runurl(View view){
        final RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://www.rinebars.com/wp-json/wc/v2/?add-to-cart=12955&consumer_key=ck_143511d1b1fd59f4db330333f1242abf956cbb3c&consumer_secret=cs_bc0d231f97f70c3bb8d20c3ebe919be96b5b0d7c&customer=21",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(Order_info.this, "Request Sent", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Order_info.this, "ERROR", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(stringRequest);
    }

    public void back(View view){
        this.onBackPressed();
    }


}

package com.asg.ashish.rine;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
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
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Products_Activity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    String jsonurl = "https://www.rinebars.com/wp-json/wc/v2/products?consumer_key=ck_143511d1b1fd59f4db330333f1242abf956cbb3c&consumer_secret=cs_bc0d231f97f70c3bb8d20c3ebe919be96b5b0d7c&per_page=30";
    private RecyclerView recyclerView;
    private List<Products_list> listItems;
    private Products_adapter adapter;
    String TAG = "Check ADAPTER", id;
    SwipeRefreshLayout mSwipeRefresh;
    private Navi_drawer navi_drawer;
    public String resp;
    private NavigationView navigationView;
    String getting_products;
    //int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_);
        mDrawerLayout = findViewById(R.id.navi);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mSwipeRefresh = findViewById(R.id.swipeRefreshLayout);
        navigationView = findViewById(R.id.nav_view);
        navi_drawer = new Navi_drawer();
        navi_drawer.nav(mDrawerLayout, navigationView);
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
        else {
            Toast.makeText(Products_Activity.this, "Please Check Your Connection", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Products_Activity.this, Rine_home.class);
                startActivity(intent);
                finish();


        }
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(isNetworkAvailable()){
                    listItems.clear();
                    SharedPreferences preferences = getSharedPreferences("saved_products", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("our_products", "");
                    editor.apply();
                    loadRecyclerViewData();
                    mSwipeRefresh.setRefreshing(false);
                }

                else
                    Toast.makeText(Products_Activity.this, "Please Check Your Connection", Toast.LENGTH_SHORT).show();

            }
        });

}

    private void loadRecyclerViewData() {

        SharedPreferences preferences = getSharedPreferences("saved_products",MODE_PRIVATE);
        getting_products = preferences.getString("our_products","");


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
                            Log.v(TAG, response);

                            try {

                                JSONArray array = new JSONArray(response);
                                String images[] = new String[4];
                                for (int i = 0; i < array.length(); i++) {
                                    final JSONObject o = array.getJSONObject(i);
                                    if (!(o.getString("name").contains("Subscription"))) {

                                        Log.v("IMAGE ARRAY", o.getJSONArray("images").toString());
                                        JSONArray jsonArray = o.getJSONArray("images");
                                        Log.v("LENGTH OF ARRAY", Integer.toString(jsonArray.length()));
                                        for( int j = 0; j < jsonArray.length(); j++){
                                            JSONObject jsonObject = jsonArray.getJSONObject(j);
                                            images[j] = jsonObject.getString("src");
                                            Log.v("TEST IMAGES", images[j].toString());
                                            if(!isNetworkAvailable() && i<array.length()){
                                                Toast.makeText(Products_Activity.this, "Connection Failed", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(Products_Activity.this, Rine_home.class);
                                                startActivity(intent);

                                            }

                                        }

                                        Products_list item = new Products_list(o.getString("name"), o.getString("id"), images[0], images[1], images[2], images[3], o.getString("short_description"), o.getString("sale_price"), o.getString("regular_price"));

                                        listItems.add(item);
                                        progressDialog.dismiss();
                                        adapter = new Products_adapter(listItems, getApplicationContext());
                                        recyclerView.setAdapter(adapter);
                                    }



                                }


                                Log.v(TAG, "Here in the ADAPTER block");
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.v(TAG, "Some json exception");
                                loadRecyclerViewData();


                            }
                            catch (Exception e){
                                if(!isNetworkAvailable()){
                                    Toast.makeText(Products_Activity.this, "Connection Failed", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(Products_Activity.this, Rine_home.class);
                                    startActivity(intent);

                                }
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


    @Override
    protected void onPause() {
        super.onPause();
        String json_products = new Gson().toJson(listItems);
        SharedPreferences preferences = getSharedPreferences("saved_products", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("our_products", json_products);
        editor.apply();
        Log.v("THE SAVED LIST IS: ",json_products);

    }

    @Override
    protected void onStop() {
        super.onStop();
        String json_products = new Gson().toJson(listItems);
        SharedPreferences preferences = getSharedPreferences("saved_products", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("our_products", json_products);
        editor.apply();
        Log.v("THE SAVED LIST IS: ",json_products);
    }

}

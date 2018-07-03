package com.asg.ashish.rine;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

import Adapter.Recipe_adapter;
import Model.Recipe_list;


public class Recipes extends AppCompatActivity {
    RecyclerView recyclerView;
    private  SwipeRefreshLayout mSwipeRefresh;
    private  DrawerLayout mDrawerLayout;
    private  Recipe_adapter adapter;
    private List<Recipe_list> listItems;
    String TAG = "Check", img;
    private Navi_drawer navi_drawer;
    private NavigationView navigationView;

    //RSS link

    private String recipe_json = "https://www.rinebars.com/wp-json/wp/v2/posts?per_page=50";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        mSwipeRefresh = (SwipeRefreshLayout)findViewById(R.id.swipeRefreshLayout);
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(getBaseContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.navi);
        navigationView = findViewById(R.id.nav_view);
        navi_drawer = new Navi_drawer();
        navi_drawer.nav(mDrawerLayout, navigationView);
        listItems = new ArrayList<>();
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(isNetworkAvailable()) {
                    loadRecipe();
                    mSwipeRefresh.setRefreshing(false);
                }
                else
                    Toast.makeText(Recipes.this, "Please Check Your Connection", Toast.LENGTH_SHORT).show();

            }
        });
        if(isNetworkAvailable())
        loadRecipe();
        else
            Toast.makeText(Recipes.this, "Please Check Your Connection", Toast.LENGTH_SHORT).show();





    }

    private void loadRecipe(){
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

        final StringRequest stringRequest = new StringRequest(Request.Method.GET, recipe_json, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.v(TAG, response);

                try {

                    JSONArray array = new JSONArray(response);
                    Log.v(TAG,array.toString());
                    Log.v("ARRAY LENGTH", Integer.toString(array.length()));
                    for(int i=0; i<array.length();i++){
                        final JSONObject o = array.getJSONObject(i);

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
                                        Recipe_list item = new Recipe_list(o.getJSONObject("title").getString("rendered"),  o.getJSONObject("excerpt").getString("rendered"), img, o.getString("date"), o.getString("link"));
                                        Log.v("IMAGE CHECKER OUTSIDE", img);
                                        listItems.add(item);
                                        progressDialog.dismiss();

                                        adapter = new Recipe_adapter(listItems, getApplicationContext());
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


                    Log.v(TAG, "Here in the ADAPTER block");
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.v(TAG, "Some json exception");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);

    }



    /*private void loadRSS(){
        AsyncTask<String,String,String> loadRSSAsync = new AsyncTask<String, String, String>() {

            ProgressDialog mDialog = new ProgressDialog(Recipes.this);

            @Override
            protected void onPreExecute(){

                mDialog.setMessage("Loading");
                mDialog.show();
            }


            @Override
            protected String doInBackground(String... strings) {
                String result;
                HTTPDataHandler http = new HTTPDataHandler();
                result = http.GetHTTPData(strings[0]);
                return result;
            }

            @Override
            protected void onPostExecute(String s) {
                mDialog.dismiss();
                rssObject = new Gson().fromJson(s, RSSObject.class);
                FeedAdapter adapter = new FeedAdapter(rssObject,getBaseContext(), getLayoutInflater());
                mRecyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        };
        StringBuilder url_get_data = new StringBuilder(RSS_to_Json_API);
        url_get_data.append(RSS_link);
        loadRSSAsync.execute(url_get_data.toString());
        mSwipeRefresh.setRefreshing(false);
    }*/

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void back(View view){
        this.onBackPressed();
    }

    public void drawerclick(View view){
        mDrawerLayout.openDrawer(Gravity.END);

    }

    public void tocart(View view){
        Intent i = new Intent(this, Cart_Activity.class);
        startActivity(i);
    }



}




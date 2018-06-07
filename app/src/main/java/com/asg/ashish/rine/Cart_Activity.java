package com.asg.ashish.rine;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Adapter.Cart_Adapter;
import Model.Cart_list;

public class Cart_Activity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private RecyclerView recyclerView;
    private List<Cart_list> listItems;
    private Cart_Adapter adapter;
    String TAG = "Check ADAPTER", id, data="";
    SwipeRefreshLayout mSwipeRefresh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.navi);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mSwipeRefresh = (SwipeRefreshLayout)findViewById(R.id.swipeRefreshLayout);
        listItems = new ArrayList<>();

        loader();
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                listItems.clear();
                loader();
            }
        });



    }

    public void loader(){

        SharedPreferences sp = getSharedPreferences("cart", MODE_PRIVATE);
        data = sp.getString("cartdata", "");
        //String json = new Gson().toJson(data);
        data = data.replaceAll("]\\[",",");
        data = data.replaceAll("\\[]","");

            Log.v("Regex", data);
            try {
                JSONArray jsonArray = new JSONArray(data);
                Log.v(TAG, jsonArray.toString());
                int l = jsonArray.length();
                Log.v("LENGTH OF JSON ARRAY: ",Integer.toString(l));

                for(int i=1; i<jsonArray.length(); i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Log.v(TAG, jsonObject.toString());
                    Log.v("I IS: ", Integer.toString(i));
                    Cart_list item = new Cart_list(jsonObject.getString("title"), jsonObject.getString("id"), jsonObject.getString("img"), jsonObject.getString("count"), Integer.toString(i));
                    listItems.add(item);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            mSwipeRefresh.setRefreshing(false);

            adapter = new Cart_Adapter(listItems,getApplicationContext());
            recyclerView.setAdapter(adapter);
        }



    public void drawerclick(View view){
        mDrawerLayout.openDrawer(Gravity.RIGHT);

    }




    public void back(View view){
        this.onBackPressed();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void remove(View view){
        String newdata = "";
        TextView index;
        index = (TextView)findViewById(R.id.index);
        String idi = index.getText().toString();
        Log.v("Index is:", idi);
        try {
            JSONArray json= new JSONArray(data);
            json.remove(Integer.parseInt(idi));
            newdata = json.toString();
            newdata = newdata.replaceAll("]\\[",",");
            newdata = newdata.replaceAll("\\[]",",");
            Log.v("New json String:", newdata);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        SharedPreferences sharedPreferences = getSharedPreferences("cart", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("cartdata", newdata);
        editor.apply();
        listItems.clear();
        loader();



    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        listItems.clear();
        loader();
    }

    @Override
    protected void onResume() {
        super.onResume();
        listItems.clear();
        loader();
    }
}

package com.asg.ashish.rine;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;

import Adapter.FeedAdapter;
import Common.HTTPDataHandler;
import Model.RSSObject;

public class Recipes extends AppCompatActivity {
    RecyclerView mRecyclerView;
    SwipeRefreshLayout mSwipeRefresh;
    ImageView feedimage;
    RSSObject rssObject;
    DrawerLayout mDrawerLayout;

    //RSS link
    private final String RSS_link="http://www.rinebars.com/feed/";
    private String RSS_to_Json_API="https://api.rss2json.com/v1/api.json?rss_url=";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipes);
        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        mSwipeRefresh = (SwipeRefreshLayout)findViewById(R.id.swipeRefreshLayout);
        feedimage = (ImageView)findViewById(R.id.feedimage);
        LinearLayoutManager linearLayoutManager= new LinearLayoutManager(getBaseContext(),LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.navi);
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(isNetworkAvailable())
                loadRSS();
                else
                    Toast.makeText(Recipes.this, "Please Check Your Connection", Toast.LENGTH_SHORT).show();

            }
        });
        if(isNetworkAvailable())
        loadRSS();
        else
            Toast.makeText(Recipes.this, "Please Check Your Connection", Toast.LENGTH_SHORT).show();

        //Glide.with(this).load(FeedAdapter.Image).into(feedimage);



    }



    private void loadRSS(){
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
    }

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



}




package com.asg.ashish.rine;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import Database.DBHandler;
import Model.Cart_list;

import static Database.DBHandler.TABLE_NAME;

public class Cart_Activity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private RecyclerView recyclerView;
    private List<Cart_list> listItems;
    private Cart_Adapter adapter;
    String TAG = "Check ADAPTER", id, data="";
    SwipeRefreshLayout mSwipeRefresh;
    int count = 0;
    DBHandler dbHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.navi);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mSwipeRefresh = (SwipeRefreshLayout)findViewById(R.id.swipeRefreshLayout);
        listItems = new ArrayList<>();
        dbHandler = new DBHandler(this, null, null, 2);
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

        /*SharedPreferences sp = getSharedPreferences("cart", MODE_PRIVATE);
        data = sp.getString("cartdata", "");
        //String json = new Gson().toJson(data);
        //data = data.replaceAll("]\\[",",");
        /*data = data.replaceAll("\\[]","");*/
        /*JSONArray json;
        try {
            json = new JSONArray(data);
            if(!(data.startsWith("[")&&data.endsWith("]")))
                data = "["+data+"]";
            /*SharedPreferences sharedPreferences = getSharedPreferences("flag",MODE_PRIVATE);
            count = sharedPreferences.getInt("count", 0);
            if(count == 0) {
                json.remove(json.length()-1);
                count = 1;
            }
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt("count",count);
            editor.apply();
        } catch (JSONException e) {
            e.printStackTrace();
        }*/




            Log.v("REGEX", data);

                String dbpid="", dbtitle="", dblink="", dbquantity="";
                SQLiteDatabase db = dbHandler.getWritableDatabase();
                String query = " SELECT * FROM " + TABLE_NAME + " WHERE 1 ";

                Cursor c = db.rawQuery(query, null);
                c.moveToFirst();
                listItems.clear();

                while(!c.isAfterLast()){
                    if(c.getString(c.getColumnIndex("pid"))!=null){
                        dbpid = c.getString(c.getColumnIndex("pid"));
                        dbtitle = c.getString(c.getColumnIndex("title"));
                        dblink = c.getString(c.getColumnIndex("link"));

                        dbquantity = c.getString(c.getColumnIndex("quantity"));
                        Log.v("PRODUCT ID IS: ", dbpid);
                        Cart_list arrayList = new Cart_list(dbpid,dbtitle,dblink,dbquantity);
                        listItems.add(arrayList);
                        adapter = new Cart_Adapter(listItems, getApplicationContext());
                        recyclerView.setAdapter(adapter);

                    }
                    c.moveToNext();
                }

                c.close();


            mSwipeRefresh.setRefreshing(false);


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
        TextView pid;
        pid = (TextView)findViewById(R.id.counter);
        String idi = pid.getText().toString();
        Log.v("ID IS :",idi);
        dbHandler.deleteProduct(idi);
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

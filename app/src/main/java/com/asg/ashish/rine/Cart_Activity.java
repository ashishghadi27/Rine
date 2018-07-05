package com.asg.ashish.rine;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import android.widget.Button;
import android.widget.TextView;

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
    private Navi_drawer navi_drawer;
    DBHandler dbHandler;
    private NavigationView navigationView;
    private TextView total_set;
    private SharedPreferences sharedPreferences;
    int total_price = 0;
    private Button checkout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.navi);
        navigationView = findViewById(R.id.nav_view);
        navi_drawer = new Navi_drawer();
        navi_drawer.nav(mDrawerLayout, navigationView);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mSwipeRefresh = (SwipeRefreshLayout)findViewById(R.id.swipeRefreshLayout);
        checkout = findViewById(R.id.checkout);
        listItems = new ArrayList<>();
        dbHandler = new DBHandler(this, null, null, 2);
        loader();

        total_set = findViewById(R.id.price_set);
        total_set.setText(sharedPreferences.getString("tp","0"));
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                listItems.clear();
                loader();
                total_set = findViewById(R.id.price_set);
                total_set.setText(sharedPreferences.getString("tp","0"));
            }
        });

        sharedPreferences = getSharedPreferences("totalprice",MODE_PRIVATE);
        total_set = findViewById(R.id.price_set);
        total_set.setText(sharedPreferences.getString("tp","0"));
        calt();


        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Cart_Activity.this, Checkout_details.class);
                startActivity(intent);
            }
        });



    }

    public void loader(){

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
            sharedPreferences = getSharedPreferences("totalprice",MODE_PRIVATE);
            total_set = findViewById(R.id.price_set);
            total_set.setText(sharedPreferences.getString("tp","0"));

            //Cart_Activity.this.recreate();
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
        /*SharedPreferences sharedPreferences1 = getSharedPreferences("totalprice",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences1.edit();
        editor.putString("tp", "0");
        editor.apply();
        SharedPreferences sharedPreferences2 = getSharedPreferences("totalprice", MODE_PRIVATE);
        String cp = sharedPreferences1.getString("tp", "0");
        Log.v("UPDATED PRICE", cp);
        total_set.setText(sharedPreferences2.getString("tp", "0"));*/
        Log.v("ID ISSSSSSSSSSSSSSSS :",idi);
        dbHandler.deleteProduct(idi);
        listItems.clear();
        loader();




    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        //Cart_Activity.this.recreate();
        listItems.clear();
        loader();
        /*SharedPreferences sharedPreferences2 = getSharedPreferences("totalprice", MODE_PRIVATE);
        total_set.setText(sharedPreferences2.getString("tp", "0"));*/
        total_price = 0;
        calt();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Cart_Activity.this.recreate();
        listItems.clear();
        loader();
        total_price = 0;
        /*SharedPreferences sharedPreferences2 = getSharedPreferences("totalprice", MODE_PRIVATE);
        total_set.setText(sharedPreferences2.getString("tp", "0"));*/
        calt();
    }

    public void calt(){

        String dbpid="", dbtitle="", dblink="", dbquantity="";

        SQLiteDatabase db = dbHandler.getWritableDatabase();
        String query = " SELECT * FROM " + TABLE_NAME + " WHERE 1 ";

        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        while(!c.isAfterLast()){
            if(c.getString(c.getColumnIndex("pid"))!=null){
                dbtitle = c.getString(c.getColumnIndex("title"));
                final SharedPreferences sharedPreferences = getSharedPreferences("price", MODE_PRIVATE);
                String price = sharedPreferences.getString(dbtitle, "360");

                dbquantity = c.getString(c.getColumnIndex("quantity"));
                total_price = total_price + Integer.parseInt(dbquantity) * Integer.parseInt(price);

            }
            c.moveToNext();
        }

        c.close();
        total_set.setText(String.valueOf(total_price));
    }
}

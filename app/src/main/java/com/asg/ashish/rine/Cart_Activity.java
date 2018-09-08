package com.asg.ashish.rine;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

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

import Adapter.Cart_Adapter;
import Database.DBHandler;
import Model.Cart_list;

import static Database.DBHandler.TABLE_NAME;

public class Cart_Activity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private RecyclerView recyclerView;
    private List<Cart_list> listItems;
    private Cart_Adapter adapter;
    String TAG = "Check ADAPTER", id, data="", cut_price_by, code, discount = "0.00";
    SwipeRefreshLayout mSwipeRefresh;
    int count = 0;
    private Navi_drawer navi_drawer;
    DBHandler dbHandler;
    private NavigationView navigationView;
    private TextView total_set, discount_set, applied_text, subtotal_price;
    private SharedPreferences sharedPreferences, preferences;
    int total_price = 0; float dis = 0;
    private Button checkout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_);
        mDrawerLayout = findViewById(R.id.navi);
        navigationView = findViewById(R.id.nav_view);
        navi_drawer = new Navi_drawer();
        navi_drawer.nav(mDrawerLayout, navigationView);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mSwipeRefresh = findViewById(R.id.swipeRefreshLayout);
        checkout = findViewById(R.id.checkout);
        applied_text = findViewById(R.id.applied_text);
        discount_set = findViewById(R.id.discount_price);
        subtotal_price = findViewById(R.id.subtotal_price);
        listItems = new ArrayList<>();
        dbHandler = new DBHandler(this, null, null, 2);
        loader();

        preferences = getSharedPreferences("discount",MODE_PRIVATE);
        cut_price_by = preferences.getString("dis", "0");
        dis = Float.parseFloat(cut_price_by);
        dis = dis/100;

        Log.v("THE DISCOUNT: ", String.valueOf(dis));

        total_set = findViewById(R.id.price_set);
        total_set.setText("\u20B9 " + sharedPreferences.getString("tp","0"));
        subtotal_price.setText("\u20B9 " + sharedPreferences.getString("tp","0"));
        mSwipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                listItems.clear();
                loader();
                total_set = findViewById(R.id.price_set);

                total_set.setText("\u20B9 " + sharedPreferences.getString("tp","0"));
                subtotal_price.setText("\u20B9 " + sharedPreferences.getString("tp","0"));
                updatetotal();
            }
        });

        sharedPreferences = getSharedPreferences("totalprice",MODE_PRIVATE);
        total_set = findViewById(R.id.price_set);
        total_set.setText("\u20B9 " + sharedPreferences.getString("tp","0"));
        subtotal_price.setText("\u20B9 " + sharedPreferences.getString("tp","0"));
        calt();


        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Cart_Activity.this, Checkout_details.class);
                intent.putExtra("amount", total_set.getText().toString());
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

                String pinfocheckout = new Gson().toJson(listItems);
                SharedPreferences sharedPreferences5 = getSharedPreferences("Checkout", MODE_PRIVATE);
                SharedPreferences.Editor editor2 = sharedPreferences5.edit();
                editor2.putString("pinfocheckout", pinfocheckout);
                editor2.apply();



                c.close();


            mSwipeRefresh.setRefreshing(false);
            sharedPreferences = getSharedPreferences("totalprice",MODE_PRIVATE);
            total_set = findViewById(R.id.price_set);
            total_set.setText("\u20B9 " + sharedPreferences.getString("tp","0"));
            subtotal_price.setText("\u20B9 " + sharedPreferences.getString("tp","0"));
            updatetotal();

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
        pid = findViewById(R.id.counter);
        String idi = pid.getText().toString();
        Log.v("ID ISSSSSSSSSSSSSSSS :",idi);
        dbHandler.deleteProduct(idi);
        listItems.clear();
        loader();
        updatetotal();




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
        total_set.setText("\u20B9 " + String.valueOf(total_price));
        subtotal_price.setText("\u20B9 " + String.valueOf(total_price));
        updatetotal();
    }

    public void applycoupon(View view){
        android.support.v7.app.AlertDialog.Builder alertDialog = new android.support.v7.app.AlertDialog.Builder(Cart_Activity.this);
        alertDialog.setTitle("Enter Coupon Code");

        // Setting Dialog Message
       // alertDialog.setMessage("Enter Password");
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(50, 0, 30, 0);

        final EditText textBox = new EditText(Cart_Activity.this);
        layout.addView(textBox, params);

        alertDialog.setView(layout);

        // Setting Icon to Dialog
        alertDialog.setIcon(R.drawable.splash);

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("APPLY",
                new android.content.DialogInterface.OnClickListener() {
                    public void onClick(android.content.DialogInterface dialog,int which) {

                        code = textBox.getText().toString();

                        String jsonurl = "https://www.rinebars.com/wp-json/wc/v2/coupons?consumer_key=ck_143511d1b1fd59f4db330333f1242abf956cbb3c&consumer_secret=cs_bc0d231f97f70c3bb8d20c3ebe919be96b5b0d7c";
                        final RequestQueue requestQueue = Volley.newRequestQueue(Cart_Activity.this);
                        final ProgressDialog progressDialog = new ProgressDialog(Cart_Activity.this);
                        progressDialog.setMessage("Applying Coupon");
                        progressDialog.setCancelable(true);
                        progressDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                            @Override
                            public void onCancel(DialogInterface dialog) {
                                onBackPressed();
                            }
                        });
                        progressDialog.setCanceledOnTouchOutside(false);
                        progressDialog.show();

                        StringRequest stringRequest = new StringRequest(Request.Method.GET, jsonurl,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        try {
                                            int i;
                                            JSONArray jsonArray = new JSONArray(response);
                                            for(i=0; i < jsonArray.length(); i++){
                                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                                if(jsonObject.getString("code").equals(code)){
                                                    String setapplied = "(" + code + ")";
                                                    discount = jsonObject.getString("amount");
                                                    progressDialog.dismiss();
                                                    android.support.v7.app.AlertDialog.Builder alertDialog = new android.support.v7.app.AlertDialog.Builder(Cart_Activity.this);
                                                    alertDialog.setTitle("Coupon Code Applied");
                                                    String message = "Discount of " + discount + " % applied";
                                                    alertDialog.setMessage(message);
                                                    alertDialog.setIcon(R.drawable.splash);
                                                    alertDialog.show();
                                                    SharedPreferences sharedPreferences = getSharedPreferences("discount", MODE_PRIVATE);
                                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                                    editor.putString("dis", discount);
                                                    editor.apply();
                                                    String getp = total_set.getText().toString().replaceAll("\u20B9 ", "");
                                                    int setdis = 0;
                                                    if(Float.parseFloat(discount) > 0) {
                                                        setdis = (int) (Integer.parseInt(getp) * (Float.parseFloat(discount) / 100));
                                                    }
                                                    discount_set.setText(String.valueOf("-" + "\u20B9 "+ setdis));
                                                    if(Float.parseFloat(discount) > 0.00) {
                                                        total_set.setText("\u20B9 " + String.valueOf(Integer.parseInt(getp) - (int) (Integer.parseInt(getp) * (Float.parseFloat(discount) / 100))));
                                                    }
                                                    else
                                                        total_set.setText("\u20B9 " + String.valueOf(Integer.parseInt(getp)));
                                                    String replace = applied_text.getText().toString();
                                                    replace = replace.replaceAll("Applied", setapplied);
                                                    applied_text.setText(replace);
                                                    break;
                                                }
                                            }
                                            if(i == jsonArray.length()){
                                                progressDialog.dismiss();
                                                android.support.v7.app.AlertDialog.Builder alertDialog = new android.support.v7.app.AlertDialog.Builder(Cart_Activity.this);
                                                alertDialog.setTitle("Invalid Coupon Code");
                                                alertDialog.setMessage("Check your Coupon Code");
                                                alertDialog.setIcon(R.drawable.splash);
                                                alertDialog.show();

                                            }
                                        } catch (JSONException e) {
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
                });
        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("CANCEL",
                new android.content.DialogInterface.OnClickListener() {
                    public void onClick(android.content.DialogInterface dialog, int which) {
                        // Write your code here to execute after dialog
                        dialog.cancel();
                    }
                });

        // closed

        // Showing Alert Message
        alertDialog.show();
    }

    public void updatetotal(){
        String setapplied = "(" + code + ")";
        String getp = total_set.getText().toString().replaceAll("\u20B9 ", "");
        int setdis = 0;
        if(Float.parseFloat(discount) > 0) {
            setdis = (int) (Integer.parseInt(getp) * (Float.parseFloat(discount) / 100));
        }
        discount_set.setText(String.valueOf("-" + "\u20B9 "+ setdis));
        if(Float.parseFloat(discount) > 0.00) {
            total_set.setText("\u20B9 " + String.valueOf(Integer.parseInt(getp) - (int) (Integer.parseInt(getp) * (Float.parseFloat(discount) / 100))));
        }
        else
            total_set.setText("\u20B9 " + String.valueOf(Integer.parseInt(getp)));


    }
}

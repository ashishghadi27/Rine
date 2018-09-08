package com.asg.ashish.rine;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.payumoney.core.PayUmoneySdkInitializer;
import com.payumoney.core.entity.TransactionResponse;
import com.payumoney.sdkui.ui.utils.PayUmoneyFlowManager;

import org.json.JSONException;
import org.json.JSONObject;

public class Checkout_details extends AppCompatActivity {

    private Navi_drawer navi_drawer;
    private NavigationView navigationView;
    private DrawerLayout mDrawerLayout;
    private  String mail;
    private String name;
    Context context;
    Button button;
    EditText namefield, address1, nofield, address2, email, state_field, city_field, pin_field;
    String key="G9P3awro",txnid="",productinfo="",amount="", surl="https://www.rinebars.com/order-received/", furl="https://www.rinebars.com/order-received/", serverCalculatedHash, addr1="", addr2="", state, city, pin, phoneno;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout_details);
        mDrawerLayout = findViewById(R.id.navi);
        navigationView = findViewById(R.id.nav_view);
        navi_drawer = new Navi_drawer();
        navi_drawer.nav(mDrawerLayout, navigationView);
        button = findViewById(R.id.checkout);
        Intent intent = getIntent();
        amount = intent.getStringExtra("amount");
        amount = amount.replace("\u20B9 ","");
        context = this.getApplicationContext();
        SharedPreferences preferences = getSharedPreferences("profile", MODE_PRIVATE);
        mail = preferences.getString("email","");
        name = preferences.getString("name","");
        namefield = findViewById(R.id.namefield);
        nofield = findViewById(R.id.numberfield);
        address1 = findViewById(R.id.address1);
        address2 = findViewById(R.id.address2);
        state_field = findViewById(R.id.state);
        city_field = findViewById(R.id.city);
        pin_field = findViewById(R.id.pincode);
        email = findViewById(R.id.mailfield);
        namefield.setText(name);
        email.setText(mail);
        txnid = ""+System.currentTimeMillis();
        Log.v("TRANSACTION","" + txnid);
        SharedPreferences sharedPreferences = getSharedPreferences("Checkout", MODE_PRIVATE);
        productinfo = sharedPreferences.getString("pinfocheckout", "");
        Log.v("TRANSACTION","" + productinfo);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start_paying();
            }
        });


    }

    public void drawerclick(View view){
        mDrawerLayout.openDrawer(Gravity.END);

    }

    public void tocart(View view){
        Intent i = new Intent(this, Cart_Activity.class);
        startActivity(i);
    }


    public void back(View view){
        this.onBackPressed();
    }



   /* public void choose_pay(View view){
        /*CharSequence engines[] = new CharSequence[] {"Pay U Money", "Paytm"};

        final AlertDialog.Builder builder = new AlertDialog.Builder(Checkout_details.this);
        builder.setTitle("Select a Delivery Option");
        builder.setItems(engines, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(which == 0) {*/
                    //start_paying();
                /*}
                else if (which == 1){
                    paytm_pay();
                }


            }
        });
        builder.show();
    }*/

    /*public void paytm_pay(){



    }*/

    public void start_paying(){

        namefield = findViewById(R.id.namefield);
        nofield = findViewById(R.id.numberfield);
        address1 = findViewById(R.id.address1);
        address2 = findViewById(R.id.address2);
        state_field = findViewById(R.id.state);
        city_field = findViewById(R.id.city);
        pin_field = findViewById(R.id.pincode);
        email = findViewById(R.id.mailfield);

        addr1 = "" + address1.getText().toString();
        addr2 = "" + address2.getText().toString();
        state = "" + state_field.getText().toString();
        city = "" + city_field.getText().toString();
        pin ="" + pin_field.getText().toString();
        phoneno = "" + nofield.getText().toString();


        if(!(addr1.equals("")||addr2.equals("")||state.equals("")||city.equals("")||pin.equals(""))) {
        Background_Worker background_worker = new Background_Worker(Checkout_details.this);
        background_worker.execute("hash", name, mail, productinfo, Double.toString(Double.parseDouble(amount)), txnid, addr1, addr2, state, city, pin);
        SharedPreferences sharedPreferences = getSharedPreferences("Hash", MODE_PRIVATE);

        try {
            JSONObject jsonObject = new JSONObject(sharedPreferences.getString("sequence",""));
            Log.v("SERVER","" + sharedPreferences.getString("sequence",""));
            serverCalculatedHash = jsonObject.getString("payment_hash");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.v("HASH", "" + serverCalculatedHash);

            PayUmoneySdkInitializer.PaymentParam.Builder builder = new
                    PayUmoneySdkInitializer.PaymentParam.Builder();
            builder.setAmount(Double.parseDouble(amount))                          // Payment amount
                    .setTxnId(txnid)                                             // Transaction ID
                    .setPhone(phoneno)                                           // User Phone number
                    .setProductName(productinfo)                   // Product Name or description
                    .setFirstName(name.trim())// User First name
                    .setEmail(mail)                                            // User Email ID
                    .setsUrl(surl)                    // Success URL (surl)
                    .setfUrl(furl)                      //Failure URL (furl)
                    .setUdf1(addr1)
                    .setUdf2(addr2)
                    .setUdf3(state)
                    .setUdf4(city)
                    .setUdf5(pin)
                    .setIsDebug(false)                              // Integration environment - true (Debug)/ false(Production)
                    .setKey(key)                       // Merchant key
                    .setMerchantId("6095786");


            try {
                PayUmoneySdkInitializer.PaymentParam paymentParam = builder.build();
                Log.v("HASH:","NOT SET");
                paymentParam.setMerchantHash(serverCalculatedHash);
                Log.v("HASH:","SET");
                PayUmoneyFlowManager.startPayUMoneyFlow(
                        paymentParam,Checkout_details.this
                        , R.style.AppTheme_Green, false);

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(Checkout_details.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
            }
//set the hash

        }
        else Toast.makeText(Checkout_details.this,"Fill Details",Toast.LENGTH_SHORT).show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result Code is -1 send from Payumoney activity
        Log.d("MainActivity", "request code " + requestCode + " resultcode " + resultCode);
        if (requestCode == PayUmoneyFlowManager.REQUEST_CODE_PAYMENT && resultCode == RESULT_OK && data != null) {
            TransactionResponse transactionResponse = data.getParcelableExtra( PayUmoneyFlowManager.INTENT_EXTRA_TRANSACTION_RESPONSE );

            if (transactionResponse != null && transactionResponse.getPayuResponse() != null) {

                if(transactionResponse.getTransactionStatus().equals( TransactionResponse.TransactionStatus.SUCCESSFUL )){
                    /*Background_Worker background_worker = new Background_Worker(Checkout_details.this);
                    String ordername[] = name.split(" ");
                    Log.v("FIRST NAME", "" + ordername[0]);
                    Log.v("LAST NAME", "" + ordername[1]);
                    background_worker.execute("orders", ordername[0], ordername[1], mail, productinfo, Double.toString(Double.parseDouble(amount)), addr1, addr2, state, city, pin);*/
                    Toast.makeText(Checkout_details.this,"Transaction Successfull", Toast.LENGTH_SHORT).show();

                } else{
                    Log.v("FAILURE", "Transaction Failed");
                }

                // Response from Payumoney
                String payuResponse = transactionResponse.getPayuResponse();

                // Response from SURl and FURL
                String merchantResponse = transactionResponse.getTransactionDetails();
            }  /*else if (resultModel != null && resultModel.getError() != null) {
                Log.d(TAG, "Error response : " + resultModel.getError().getTransactionResponse());
            } else {
                Log.d(TAG, "Both objects are null!");
            }*/
        }
    }




}

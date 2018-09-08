package com.asg.ashish.rine;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by ashish on 12/3/18.
 */

public class
Background_Worker extends AsyncTask<String, Void, String> {

    Context context;
    AlertDialog alertDialog;
    String TAG = "testPhp";
    private ProgressDialog mDialog;
    Background_Worker(Context ctx) {
        context = ctx;
    }

    @Override
    protected String doInBackground(String... voids) {
        String type = voids[0];
        String login_url = "http://www.rinebars.com/api/login.php";
        String register_url = "http://www.rinebars.com/api/register.php";
        String hashurl = "http://www.rinebars.com/api/moneyhash.php";
        String orderurl = "http://www.rinebars.com/httpdocs/wp-content/themes/rine-child/ordersupdate.php";
        String idlink = "http://www.rinebars.com/api/getid.php";
        if(type.equals("login")){
            try {
                String user_name = voids[1];
                String password = voids[2];
                Log.v(TAG, user_name);
                Log.v(TAG, password);
                URL url = new URL((login_url));
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("user_name",  "UTF-8")+"="+URLEncoder.encode(user_name,  "UTF-8")+"&"+URLEncoder.encode("password",  "UTF-8")+"="+URLEncoder.encode(password,  "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null){
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        else if(type.equals("register")){
            try {
                String name = voids[1];
                String email = voids[2];
                String password = voids[3];
                String username = voids[4];
                Log.v(TAG, email);
                Log.v(TAG, password);
                URL url = new URL((register_url));
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("name",  "UTF-8")+"="+URLEncoder.encode(name,  "UTF-8")+"&"
                        +URLEncoder.encode("email",  "UTF-8")+"="+URLEncoder.encode(email,  "UTF-8")+"&"
                        +URLEncoder.encode("password",  "UTF-8")+"="+URLEncoder.encode(password,  "UTF-8")+"&"
                        +URLEncoder.encode("username",  "UTF-8")+"="+URLEncoder.encode(username,  "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String result = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null){
                    result += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return result;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(type.equals("hash")){
            try {
                String firstname = voids[1];
                String email = voids[2];
                String productinfo = voids[3];
                String amount = voids[4];
                String txnid  = voids[5];
                String addr1 = voids[6];
                String addr2 = voids[7];
                String state = voids[8];
                String city = voids[9];
                String pin = voids[10];

                URL url = new URL((hashurl));
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("firstname",  "UTF-8")+"="+URLEncoder.encode(firstname,  "UTF-8")+"&"
                        +URLEncoder.encode("email",  "UTF-8")+"="+URLEncoder.encode(email,  "UTF-8")+"&"
                        +URLEncoder.encode("productinfo",  "UTF-8")+"="+URLEncoder.encode(productinfo,  "UTF-8")+"&"
                        +URLEncoder.encode("amount",  "UTF-8")+"="+URLEncoder.encode(amount,  "UTF-8")+"&"
                        +URLEncoder.encode("txnid",  "UTF-8")+"="+URLEncoder.encode(txnid,  "UTF-8")+"&"
                        +URLEncoder.encode("addr1",  "UTF-8")+"="+URLEncoder.encode(addr1,  "UTF-8")+"&"
                        +URLEncoder.encode("addr2",  "UTF-8")+"="+URLEncoder.encode(addr2,  "UTF-8")+"&"
                        +URLEncoder.encode("state",  "UTF-8")+"="+URLEncoder.encode(state,  "UTF-8")+"&"
                        +URLEncoder.encode("city",  "UTF-8")+"="+URLEncoder.encode(city,  "UTF-8")+"&"
                        +URLEncoder.encode("pin",  "UTF-8")+"="+URLEncoder.encode(pin,  "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String hasher = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null){
                    hasher += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                SharedPreferences sharedPreferences = context.getSharedPreferences("Hash", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("sequence", hasher);
                editor.apply();
                return "Processing";

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(type.equals("orders")){
            try {
                String firstname = voids[1];
                String lastname = voids[2];
                String email = voids[3];
                String productinfo = voids[4];
                String amount = voids[5];
                String addr1 = voids[6];
                String addr2 = voids[7];
                String state = voids[8];
                String city = voids[9];
                String pin = voids[10];

                URL url = new URL((orderurl));
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("first_name",  "UTF-8")+"="+URLEncoder.encode(firstname,  "UTF-8")+"&"
                        +URLEncoder.encode("mail",  "UTF-8")+"="+URLEncoder.encode(email,  "UTF-8")+"&"
                        +URLEncoder.encode("productinfo",  "UTF-8")+"="+URLEncoder.encode(productinfo,  "UTF-8")+"&"
                        +URLEncoder.encode("amount",  "UTF-8")+"="+URLEncoder.encode(amount,  "UTF-8")+"&"
                        +URLEncoder.encode("last_name",  "UTF-8")+"="+URLEncoder.encode(lastname,  "UTF-8")+"&"
                        +URLEncoder.encode("addr1",  "UTF-8")+"="+URLEncoder.encode(addr1,  "UTF-8")+"&"
                        +URLEncoder.encode("addr2",  "UTF-8")+"="+URLEncoder.encode(addr2,  "UTF-8")+"&"
                        +URLEncoder.encode("state",  "UTF-8")+"="+URLEncoder.encode(state,  "UTF-8")+"&"
                        +URLEncoder.encode("city",  "UTF-8")+"="+URLEncoder.encode(city,  "UTF-8")+"&"
                        +URLEncoder.encode("code",  "UTF-8")+"="+URLEncoder.encode(pin,  "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String hasher = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null){
                    hasher += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                SharedPreferences sharedPreferences = context.getSharedPreferences("Orders", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("sequence", hasher);
                Log.v("WOOCOMERCE", hasher);
                editor.apply();
                return "Processing";

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(type.equals("getid")){
            try {

                String email = voids[1];

                URL url = new URL((idlink));
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("user_name",  "UTF-8")+"="+URLEncoder.encode(email,  "UTF-8");
                bufferedWriter.write(post_data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String id = "";
                String line = "";
                while ((line = bufferedReader.readLine()) != null){
                    id += line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                SharedPreferences sharedPreferences = context.getSharedPreferences("User", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("id", id);
                Log.v("ID IS", id);
                editor.apply();
                return "Gotid";

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPreExecute() {
        mDialog = new ProgressDialog(context);
        mDialog.setMessage("Loading");
        mDialog.show();

    }

    @Override
    protected void onPostExecute(String result) {

        if(result.equals("Login success")){
            ProgressDialog mDialog = new ProgressDialog(context);
            mDialog.setMessage(result);
            mDialog.show();
            SharedPreferences sharedPreferences = context.getSharedPreferences("Issignedin", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("verify", "signed");
            editor.apply();
            mDialog.dismiss();
            Intent i = new Intent(context, Rine_home.class);
            Log.v(TAG,"intent k aandar");
            context.startActivity(i);
            ((Activity)context).finish();

        }

        else if(result.equals("Account already exists please sign in")){
            CharSequence engines[] = new CharSequence[] {"Sign In"};

            final AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Account Already Exists");
            builder.setItems(engines, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    if(which == 0) {
                        Intent myIntent = new Intent(context, Sign_in.class);
                        context.startActivity(myIntent);
                        ((Activity)context).finish();

                    }
                }
            });
            builder.show();

            mDialog.dismiss();
        }
        else if(result.equals("Processing")){
            Toast.makeText(context, "Processing", Toast.LENGTH_SHORT).show();
            mDialog.dismiss();
        }
        else if(result.equals("Gotid")){
            Toast.makeText(context, "Getting Id", Toast.LENGTH_SHORT).show();
            mDialog.dismiss();
        }
        else
            {
            final AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Something went wrong");
            builder.show();
            mDialog.dismiss();
        }

    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}

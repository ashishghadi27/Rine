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

public class Background_Worker extends AsyncTask<String, Void, String> {

    Context context;
    AlertDialog alertDialog;
    String TAG = "testPhp";
    Background_Worker(Context ctx) {
        context = ctx;
    }

    @Override
    protected String doInBackground(String... voids) {
        String type = voids[0];
        String login_url = "http://www.rinebars.com/api/login.php";
        String register_url = "http://www.rinebars.com/api/register.php";
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
        return null;
    }

    @Override
    protected void onPreExecute() {
        ProgressDialog mDialog = new ProgressDialog(context);
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
            Intent i = new Intent(context, Rine_home.class);
            Log.v(TAG,"intent k aandar");
            context.startActivity(i);
            ((Activity)context).finish();

        }
        else if(result.equals("Sign Up successful"))
        {
            ProgressDialog mDialog = new ProgressDialog(context);
            mDialog.setMessage(result);
            mDialog.show();
            SharedPreferences sharedPreferences = context.getSharedPreferences("Issignedin", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("verify", "signed");
            editor.apply();
            Intent i = new Intent(context, Rine_home.class);
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
        }
        else {
            //CharSequence engines[] = new CharSequence[] {"Sign In"};

            final AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Something went wrong");
            builder.show();
        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}

package com.asg.ashish.rine;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.webkit.CookieManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.Toast;

public class loadl_link extends AppCompatActivity {

    private WebView web;
    private ProgressBar progress;
    TextView Address;
    static String weblink;
    ShareActionProvider mShareActionProvider;


    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loadl_link);
        web = findViewById(R.id.web);

        Address = findViewById(R.id.Address);
        Intent i = getIntent();
        weblink = i.getStringExtra("link");

        //WebSettings webSettings = web.getSettings();
        web.getSettings().setJavaScriptEnabled(true);
        web.getSettings().setSupportZoom(true);       //Zoom Control on web
        web.getSettings().setBuiltInZoomControls(true); //Enable Multitouch if supported by ROM
        web.getSettings().setAllowFileAccess(true);
        web.addJavascriptInterface(new MyJavaScriptInterface(), "android");
        web.getSettings().setGeolocationEnabled(true);
        web.getSettings().setAllowUniversalAccessFromFileURLs(true);
        web.getSettings().setDisplayZoomControls(false);
        web.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        web.getSettings().setMediaPlaybackRequiresUserGesture(false);
        web.setWebViewClient(new WebViewClientDemo());
        web.setWebChromeClient(new WebChromeClientDemo());

        web.getSettings().setAllowUniversalAccessFromFileURLs(true);
        web.getSettings().setAllowContentAccess(true);
        web.getSettings().setDatabaseEnabled(true);
        web.getSettings().setDomStorageEnabled(true);
        CookieManager.getInstance().setAcceptCookie(true);
        CookieManager.getInstance().setAcceptThirdPartyCookies(web, true);
        CookieManager.getInstance().acceptThirdPartyCookies(web);
        CookieManager.getInstance().acceptCookie();
        progress = findViewById(R.id.progress);
        if (isConnect()){
            web.loadUrl(weblink);
            Address.setText(weblink);
        }

        else
            Toast.makeText(this, "Please Check your Connection",Toast.LENGTH_SHORT).show();



    }




    @Override

    public boolean onKeyDown(final int keyCode, final KeyEvent event) {
        web = findViewById(R.id.web);

        if ((keyCode == KeyEvent.KEYCODE_BACK) && web.canGoBack()) {
            web.goBack();
//If there is history, then the canGoBack method will return ‘true’//
            return true;
        }


//If the button that’s been pressed wasn’t the ‘Back’ button, or there’s currently no
//WebView history, then the system should resort to its default behavior and return
//the user to the previous Activity//
        else
            return super.onKeyDown(keyCode, event);
    }





    private boolean isConnect() {
        ConnectivityManager c = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert c != null;
        NetworkInfo activeNetwork = c.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnected();

    }


    public class WebViewClientDemo extends WebViewClient {

        ProgressBar progress = findViewById(R.id.progress);




        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progress.setProgress(0);
            view.loadUrl("javascript:window.android.onUrlChange(window.location.href);");
            Address.setText(web.getTitle());
            progress.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#d3d3d3")));



        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            Address.setText(web.getTitle());
            progress.setVisibility(View.VISIBLE);
            progress.setProgress(0);
            progress.setProgressTintList(ColorStateList.valueOf(Color.parseColor("#ffffff")));


        }




    }

    private class WebChromeClientDemo extends WebChromeClient {
        public void onProgressChanged(WebView view, int prog) {
            if(android.os.Build.VERSION.SDK_INT >= 11){
                // will update the "progress" propriety of seekbar until it reaches progress

                ObjectAnimator animation = ObjectAnimator.ofInt(progress, "progress",prog);
                animation.setDuration(500); // 0.5 second
                animation.setInterpolator(new DecelerateInterpolator());
                animation.start();
            }
            else
                progress.setProgress(prog);
            //progress.setProgress(prog);

        }



    }


    class MyJavaScriptInterface {
        @JavascriptInterface
        public void onUrlChange(String url) {
            Log.d("hydrated", "onUrlChange" + url);
        }
    }

    public void share(View view){
        Button share = (Button)findViewById(R.id.button);
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        String sharebody = web.getUrl();
        shareIntent.setType("text/plain");
        shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, sharebody);
        startActivity(Intent.createChooser(shareIntent, "Share via"));
    }

}


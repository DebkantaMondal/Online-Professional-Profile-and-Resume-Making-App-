package com.example.resumeapp;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class FeedbackActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_feedback);
        if (!isConnected(FeedbackActivity.this)) buildDialog(FeedbackActivity.this).show();
        else {
            Toast.makeText(FeedbackActivity.this, "Welcome", Toast.LENGTH_SHORT).show();
            setContentView(R.layout.activity_feedback);
        }
        final WebView webView;
        webView = (WebView) findViewById(R.id.feedbackform);


        webView.setWebViewClient(new WebViewClient() {
                                     public void onReceivedError(WebView view, int errorCode, String description, String fallingUrl) {
                                         webView.loadUrl("file:///android_asset/error.html");
                                     }


                                 }
        );
        webView.loadUrl("https://docs.google.com/forms/d/e/1FAIpQLSdCu_6TC8tzIsqa1je9xJRpYUiLDTgk4YfHS0VEXlRo5rOmvA/viewform");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAppCacheEnabled(true);
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            WebView wv = (WebView)findViewById(R.id.feedbackform);
            if (wv.canGoBack())
            {
                wv.goBack();

                return true;
            }
        }

        return super.onKeyDown(keyCode, event);
    }
    public boolean isConnected(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();

        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if ((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting()))
                return true;
            else return false;
        } else
            return false;
    }

    public AlertDialog.Builder buildDialog(Context c) {

        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("No Internet Connection");
        builder.setMessage("You need to have Mobile Data or wifi to access this. Press ok to Exit");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                finish();
            }
        });

        return builder;
    }
}

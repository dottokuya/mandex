package com.dottokuya.mandex;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class MainActivity extends AppCompatActivity {
    private WebView mywebView;
    private SwipeRefreshLayout swipe;
    private ProgressBar progress;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_main);
        swipe = (SwipeRefreshLayout) findViewById(R.id.swipe);
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                mywebView.reload();
            }
        });
        LoadWeb();
    }
    public void LoadWeb(){
        this.mywebView = (WebView) findViewById(R.id.webview);
        this.progress = (ProgressBar) findViewById(R.id.progressBar);
        this.mywebView.getSettings().setJavaScriptEnabled(true);
        this.mywebView.loadUrl("https://mangadex.cc/");
        swipe.setRefreshing(true);
        this.mywebView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                String main_url = "https://mangadex.cc";
                if (url != null && url.startsWith(main_url)) {
                    return false;
                }
                view.getContext().startActivity(new Intent("android.intent.action.VIEW", Uri.parse(url)));
                return true;
            }

            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                if (progress != null)
                    MainActivity.this.progress.setVisibility(View.VISIBLE);
            }

            public void onPageFinished(WebView view, String url) {
                if (progress != null)
                    MainActivity.this.progress.setVisibility(View.GONE);
                swipe.setRefreshing(false);
            }

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl){
                Log.e("error","Received Error on WebView. ERROR CODE is " + errorCode);
                Log.e("error","description; " + description);
                Log.e("error","failingUrl is " + failingUrl);
                try{
                    view.loadUrl("file:///android_asset/error.html?errorCode=" + errorCode + "&errorDescription=" + description);
                } catch  (Exception e) {
                    Log.e("error", e.toString());
                }
            }

        });
    }

    public void onBackPressed() {
        if (this.mywebView.canGoBack()) {
            this.mywebView.goBack();
        } else {
            super.onBackPressed();
        }
    }
}
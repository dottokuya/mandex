package com.dottokuya.mandex;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {
    private WebView mywebView;
    private ProgressBar progress;


    String defaultUrl = "https://mangadex.org";

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_main);
        Intent intent = getIntent();
        Uri data = intent.getData();
        if (data == null)
            LoadWeb(defaultUrl);
        else
            LoadWeb(data.toString());

    }
    @SuppressLint("SetJavaScriptEnabled")
    public void LoadWeb(String url){
        this.mywebView = (WebView) findViewById(R.id.webview);
        this.progress = (ProgressBar) findViewById(R.id.progressBar);
        this.mywebView.getSettings().setJavaScriptEnabled(true);
        this.mywebView.loadUrl(url);
        this.mywebView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url != null && url.startsWith(defaultUrl)) {
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
            }

        });

        mywebView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int newProgress){
                // Update the progress bar with page loading progress
                progress.setProgress(newProgress);
                if(newProgress == 100){
                    // Hide the progressbar
                    progress.setVisibility(View.GONE);
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
package com.dottokuya.mandex;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


public class MainActivity extends AppCompatActivity {
    private ProgressBar progress;
    private WebView mywebView;
    private SwipeRefreshLayout mySwipeRefreshLayout;


    private String defaultUrl = "https://mangadex.org";

    /* access modifiers changed from: protected */
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progress = findViewById(R.id.progressBar);
        progress.setMax(100);
        mywebView = findViewById(R.id.webview);
        mywebView.setWebViewClient(new WebViewClientDemo());
        mywebView.setWebChromeClient(new WebChromeClientDemo());
        mySwipeRefreshLayout = this.findViewById(R.id.swiperefresh);
        mySwipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        mywebView.reload();
                        mySwipeRefreshLayout.setRefreshing(false);
                    }
                });

        // call your method here to manage the intent
        manageIntent(getIntent());

    }

    public void manageIntent(Intent intent) {
        // ATTENTION: This was auto-generated to handle app links.
        Uri appLinkData = intent.getData();

        if (getIntent().getExtras() != null) {
            if (appLinkData == null) {
                mywebView.loadUrl(defaultUrl);
            }
            else mywebView.loadUrl(String.valueOf(appLinkData));

        }
        else mywebView.loadUrl(defaultUrl);
    }

    // override to get the new intent when this activity has an instance already running
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        // again call the same method here with the new intent received
        manageIntent(intent);
    }

    private class WebViewClientDemo extends WebViewClient {

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            Toast.makeText(getApplicationContext(), R.string.no_internet, Toast.LENGTH_LONG).show();
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Uri uri = Uri.parse(url);
            if (uri.getHost() != null && (url.startsWith(defaultUrl))) {
                return false;
            }

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            view.getContext().startActivity(intent);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            progress.setVisibility(View.GONE);
            progress.setProgress(100);
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            progress.setVisibility(View.VISIBLE);
            progress.setProgress(0);
        }
    }


    private class WebChromeClientDemo extends WebChromeClient {
        public void onProgressChanged(WebView view, int progression) {
            progress.setProgress(progression);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mywebView.canGoBack()) {
            mywebView.goBack();
            return true;
        }
        else {
            return super.onKeyDown(keyCode, event);
        }
    }


    @Override
    // This method is used to detect back button
    public void onBackPressed() {
        if (this.mywebView.canGoBack()) {
            this.mywebView.goBack();
        }

        else {
            // Let the system handle the back button
            super.onBackPressed();
        }
    }

}
package com.dottokuya.mandex;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private WebView mywebView;
    private ProgressBar progress;

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) R.layout.activity_main);
        this.mywebView = (WebView) findViewById(R.id.webview);
        this.progress = (ProgressBar) findViewById(R.id.progressBar);
        this.mywebView.getSettings().setJavaScriptEnabled(true);
        this.mywebView.loadUrl("https://mangadex.cc/");
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
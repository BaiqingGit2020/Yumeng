package com.chanshiguan.yumeng;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class imageviewone extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imageviewone);

        final WebView webView=(WebView) findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        //webView.setWebViewClient(new WebViewClient());

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("http:") || url.startsWith("https:")) {

                    return false;
                }//if
                Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//                startActivity(intent);
                //webView.loadUrl(url);
                return true;
            }
        });
        webView.loadUrl("http://www.dianping.com/shop/98306608");

    }
}

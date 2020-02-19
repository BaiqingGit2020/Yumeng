package com.chanshiguan.yumeng;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Looper;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class WebViewActivity extends AppCompatActivity {
    private String Url;
    private Bundle Data;
    private static final String TAG = "WebViewActivity";
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Data = getIntent().getExtras();
        Url = Data.getString("NewsUrl");
        Log.i(TAG, "onCreate: "+Url);
        setContentView(R.layout.activity_web_view);
        WebView webView = (WebView) findViewById(R.id.web_view);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        // 加载webview

        // 在当前的浏览器中响应
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
//      mWebView.getSettings().setDefaultFontSize(14);
        webSettings.setAppCacheEnabled(false);
        // // 设置可以支持缩放
        webSettings.setSupportZoom(true);
        // //扩大比例的缩放
        webSettings.setUseWideViewPort(true);
        // //自适应屏幕
        webSettings.setLoadWithOverviewMode(true);
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                // TODO Auto-generated method stub
                return super.onJsAlert(view, url, message, result);
            }
        });

        webView.setWebViewClient(new WebViewClient(){
            // 重写shouldOverrideUrlLoading方法，使点击链接后不使用其他的浏览器打开。
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                // 如果不需要其他对点击链接事件的处理返回true，否则返回false
                return true;

            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                // TODO Auto-generated method stub
                super.onPageStarted(view, url, favicon);
                //打开时显示加载框
                if (progressBar.getVisibility() == View.GONE) {
                    progressBar.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                // TODO Auto-generated method stub
                super.onPageFinished(view, url);
                //加载完成关闭加载框
                progressBar.setVisibility(View.GONE);
            }
        });
        webView.loadUrl(Url);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.BackTab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        FloatingActionButton collect = (FloatingActionButton) findViewById(R.id.CollectionTab);
        collect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //实例化okHttp
                OkHttpClient client = new OkHttpClient.Builder()
                        .connectTimeout(5,TimeUnit.MINUTES)
                        .readTimeout(5,TimeUnit.MINUTES)
                        .build();
                //okHttp参数
                RequestBody requestBody = new FormBody.Builder()
                        .add("UserId",Data.getString("UserName","0"))
                        .add("NewsId",Data.getString("NewsID","0"))
                        .build();
                Request request = new Request.Builder()
                        .url(getString(R.string.server)+"/collect.php")
                        .post(requestBody)
                        .build();
                Call call = client.newCall(request);
                //异步请求
                call.enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String result=response.body().string();
                        Log.i(TAG,result);
                        //请求成功时在子线程中加载progreaabar动态
                        if(result.equals("success!")){
                            Looper.prepare();
                            Toast.makeText(WebViewActivity.this,"在\"收藏\"等你",Toast.LENGTH_LONG).show();
                            Looper.loop();
                        }
                        else if(result.equals("double"))
                        {
                            Looper.prepare();
                            Toast.makeText(WebViewActivity.this,"已经收藏过啦！",Toast.LENGTH_LONG).show();
                            Looper.loop();

                        }
                        else
                        {
                            Looper.prepare();
                            Toast.makeText(WebViewActivity.this,"服务器不听话啦",Toast.LENGTH_LONG).show();
                            Looper.loop();
                        }
                    }
                });
            }
        });
    }
}

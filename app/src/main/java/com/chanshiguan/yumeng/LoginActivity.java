package com.chanshiguan.yumeng;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;
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

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
//    private Bundle bundle;
    private String username,password,result;
    private TextView view_username,view_password;
    private TextView view_login,view_signup;
    private CheckBox view_rememberpassword;
    private ProgressBar progressBar;
    private SharedPreferences.Editor editor;
    private SharedPreferences pref;
    private static final String TAG = "LoginHttp";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //初始化控件
        view_username = findViewById(R.id.user_id);
        view_password = findViewById(R.id.user_password);
        view_login = findViewById(R.id.button_login);
        view_rememberpassword = findViewById(R.id.remember_password);
        view_signup = findViewById(R.id.register);
        progressBar = findViewById(R.id.progress_horizon);
        editor = getSharedPreferences("LoginMessage",MODE_PRIVATE).edit();
        pref = getSharedPreferences("LoginMessage",MODE_PRIVATE);
        if(pref.getBoolean("isLunched",false))
        {
            username = pref.getString("UserName","123");
            password = pref.getString("PassWord","123");
            view_username.setText(username);
            view_password.setText(password);
            view_rememberpassword.setChecked(true);
        }
        view_login.setOnClickListener(this);
        view_rememberpassword.setOnClickListener(this);
        view_signup.setOnClickListener(this);
    }
    public void onClick(View v){
        switch (v.getId()){
            //取得用户名和密码
            case R.id.button_login:
            username = view_username.getText().toString();
            password = view_password.getText().toString();
            Log.i(TAG,"get Text successfuly");
            //初始化ProgressBar
            progressBar.setProgress(0);
            if(progressBar.getVisibility()==View.GONE)progressBar.setVisibility(View.VISIBLE);
            //登录
            login();
            break;
            //注册
            case R.id.register:
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
                break;
        }

    }
    /**
     * 登录功能
     * POST
     * okHTTP
     */
    private void login(){
        //实例化okHttp
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(5,TimeUnit.MINUTES)
                .readTimeout(5,TimeUnit.MINUTES)
                .build();
        //okHttp参数
        RequestBody requestBody = new FormBody.Builder()
                .add("username",username)
                .add("password",password)
                .build();
        Request request = new Request.Builder()
                .url(getString(R.string.server)+"/login.php")
                .post(requestBody)
                .build();
        Call call = client.newCall(request);
        //异步请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                new ProgressThread().start();
                Looper.prepare();
                Toast.makeText(LoginActivity.this,"请检查网络连接",Toast.LENGTH_LONG).show();
                Looper.loop();e.printStackTrace();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                result=response.body().string();
                Log.i(TAG,result);
                //请求成功时在子线程中加载progreaabar动态

                if(result.equals("no user"))
                {
                    new ProgressThread().start();
                    //在子进程中更新UI需要以下两个函数：prepare和loop
                    Looper.prepare();
                    Toast.makeText(LoginActivity.this,"用户不存在，请注册",Toast.LENGTH_LONG).show();
                    Looper.loop();

                }
                else if(result.equals("wrong pw"))
                {
                    new ProgressThread().start();
                    Looper.prepare();
                    Toast.makeText(LoginActivity.this,"密码错误",Toast.LENGTH_LONG).show();
                    Looper.loop();
                }
                else
                {
                    new Thread(){
                        @Override
                        public void run() {
                            super.run();
                            progressBar.setProgress(0);
                            int progress = progressBar.getProgress();
                            try{
                                while (progress<100){
                                    progress = progressBar.getProgress()+5;
                                    progressBar.setProgress(progress);
                                    sleep(30);
                                }
                                Intent intent = new Intent(LoginActivity.this,FirstActivity.class);
                                startActivity(intent);
                                LoginActivity.this.finish();
                            }catch (InterruptedException e){}
                        }
                    }.start();
                    //检查登录状态
                    if(view_rememberpassword.isChecked()){
                        editor.putString("UserName",username);
                        editor.putString("PassWord",password);
                        editor.putString("NakeName",result);
                        editor.putBoolean("isLunched",true);
                    }
                    else editor.putString("UserName",username);
                    //提交保存到本地
                    editor.apply();
                }
            }
        });
    }
    private class ProgressThread extends Thread{
        @Override
        public void run() {
            super.run();
            int progress = progressBar.getProgress();
            try{
                while (progress<60){
                    progress = progressBar.getProgress()+5;
                    progressBar.setProgress(progress);
                    sleep(60);
                }
                while (progress<100){
                    progress = progressBar.getProgress()+5;
                    progressBar.setProgress(progress);
                    sleep(5);
                }
                //耗时操作，完成之后发送消息给Handler，完成UI更新；
                mHandler.sendEmptyMessage(0);
                //需要数据传递，用下面方法；
            }catch (InterruptedException e){}

        }
    }
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    if(progressBar.getVisibility()==View.VISIBLE)progressBar.setVisibility(View.GONE);
                    break;

            }
        }
    };

}







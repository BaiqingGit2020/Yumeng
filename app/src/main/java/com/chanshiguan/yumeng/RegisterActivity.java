package com.chanshiguan.yumeng;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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


public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "Registered";
    //注册信息控件
    private TextView user_id,user_name,user_password1,user_password2,identifycode;
    //注册信息
    private String id,name,password1,password2,result,real_code,user_code;
    private TextView register;
    private SharedPreferences.Editor editor;
    //图片验证码
    private ImageView identifyingCode;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //初始化控件
        user_id = findViewById(R.id.user_id_register);
        user_name = findViewById(R.id.user_name_register);
        user_password1 = findViewById(R.id.user_password_register1);
        user_password2 = findViewById(R.id.user_password_register2);
        register = findViewById(R.id.register);
        identifycode = findViewById(R.id.identifyingcode_edittext);
        editor = getSharedPreferences("LoginMessage",MODE_PRIVATE).edit();

        register.setOnClickListener(this);
        //这里是初始化并设置监听，记得要保存对的验证码，用来验证后面用户输入的对不对
        identifyingCode=(ImageView)findViewById(R.id.identifyingcode_image);
        identifyingCode.setOnClickListener(this);
        //忽略大小写
        identifyingCode.setImageBitmap(IdentifyingCode.getInstance().createBitmap());
        real_code=IdentifyingCode.getInstance().getCode().toLowerCase();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.register:
                //读取输入信息
                id = user_id.getText().toString();
                name = user_name.getText().toString();
                password1 = user_password1.getText().toString();
                password2 = user_password2.getText().toString();
                user_code = identifycode.getText().toString().toLowerCase();
                Log.i(TAG,id+"+"+password1);
                //检查注册条件
                if(check()){
                    Register();
                }
                break;
            case R.id.identifyingcode_image:
                identifyingCode.setImageBitmap(IdentifyingCode.getInstance().createBitmap());
                real_code=IdentifyingCode.getInstance().getCode().toLowerCase();
                break;
        }
    }

    /**
     * 注册
     * 用户名
     * 两次密码
     */
    private void Register(){
        //实例化okHttp
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(5,TimeUnit.MINUTES)
                .readTimeout(5,TimeUnit.MINUTES)
                .build();
        //okHttp参数
        RequestBody requestBody = new FormBody.Builder()
                .add("username",id)
                .add("nakename",name)
                .add("password",password1)
                .build();
        Request request = new Request.Builder()
                .url(getString(R.string.server)+"/register.php")
                .post(requestBody)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                result=response.body().string();
                Log.i(TAG,"return "+result);
                //注册成功保存用户信息
                if(result.equals("register success")){
                    editor.putString("UserName",id);
                    editor.putString("PassWord",password1);
                    editor.putString("NakeName",name);
                    editor.putBoolean("isLunched",true);
                    //保存
                    editor.apply();
                    Intent intent = new Intent(RegisterActivity.this,FirstActivity.class);
                    startActivity(intent);
                    RegisterActivity.this.finish();
                }
                else if(result.equals("existed"))
                {
                    Looper.prepare();
                    Toast.makeText(RegisterActivity.this,"用户名已经存在，请重新注册",Toast.LENGTH_LONG).show();
                    Looper.loop();

                }
                else
                {
                    Looper.prepare();
                    Toast.makeText(RegisterActivity.this,"服务器错误，请稍后再试",Toast.LENGTH_LONG).show();
                    Looper.loop();
                }
            }
        });
    }

    /**
     * 本地检查输入内容
     * @return
     */
    private boolean check(){
        if(password1==null||user_name==null){
            Toast.makeText(RegisterActivity.this,"用户名或密码不能为空",Toast.LENGTH_LONG).show();
            return false;
        }
        if(!user_code.equals(real_code)){
            Toast.makeText(RegisterActivity.this,"验证码不正确",Toast.LENGTH_LONG).show();
            return false;
        }
        if(password1.length()<5)
        {
            Toast.makeText(RegisterActivity.this,"密码不能少于5位",Toast.LENGTH_LONG).show();
            return false;
        }
        if(!password1.equals(password2)){
            Toast.makeText(RegisterActivity.this,"两次输入的密码不一致",Toast.LENGTH_LONG).show();
            return false;
        }
        Log.i(TAG,"可以注册");
         return true;   
    }
}

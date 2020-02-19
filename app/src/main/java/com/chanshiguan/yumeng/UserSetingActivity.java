package com.chanshiguan.yumeng;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.leon.lib.settingview.LSettingItem;

public class UserSetingActivity extends AppCompatActivity {

    private final static int FEED_BACK = 5;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private  String UserName;
    private LSettingItem Logout,AppAbout,FeedBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_seting);

        Logout = findViewById(R.id.setting_logout);
        AppAbout = findViewById(R.id.setting_about);
        FeedBack = findViewById(R.id.setting_feedback);
        Logout.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click(boolean isChecked) {
                pref = getSharedPreferences("LoginMessage",MODE_PRIVATE);
                editor = pref.edit();
                editor.putBoolean("isLunched",false);
                editor.apply();
                startActivity(new Intent(UserSetingActivity.this,LoginActivity.class));
            }
        });
        AppAbout.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click(boolean isChecked) {
                startActivity(new Intent(UserSetingActivity.this,AppAboutActivity.class));
            }
        });
        FeedBack.setmOnLSettingItemClick(new LSettingItem.OnLSettingItemClick() {
            @Override
            public void click(boolean isChecked) {
                startActivityForResult(
                        new Intent(UserSetingActivity.this,FeedBackActivity.class),
                        FEED_BACK);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case FEED_BACK:
                if(resultCode==0)
                    Toast.makeText(UserSetingActivity.this,"反馈失败，请检查网络",Toast.LENGTH_SHORT).show();
                else Toast.makeText(UserSetingActivity.this,"反馈成功，我们会很快处理",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}

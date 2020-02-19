package com.chanshiguan.yumeng;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

public class WelcomeActivity extends AppCompatActivity implements Animation.AnimationListener{


    private static final String TAG ="WelcomeActivity" ;
    private ImageView welcomeImg;
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置为全屏模式
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcom);
        welcomeImg = (ImageView) findViewById(R.id.welcome);

        //设置动画时间 结束 后跳转到指定页面
        final Intent it = new Intent(this, FirstActivity.class); //你要转向的Activity

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                startActivity(it); //执行
                finish();
            }
        };
        timer.schedule(task, 2000);

    }
    public  void  onAnimationStart (Animation animation)  {}
    public  void  onAnimationEnd (Animation animation)  {}
    public  void  onAnimationRepeat (Animation animation)  {}
}


package com.chanshiguan.yumeng;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

public class FirstActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView topBar;
//底部导航栏文字控件
    private TextView tabFirst;
    private TextView tabLook;
    private TextView tabMessage;
    private TextView tabMy;
    private TextView tabGift;
//    登录信息的SharePreference
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    private FrameLayout ly_content;
//    五个布局类
    private ShopFragment f_look;
    private InformationFragment f_information;
    private MainFragment f_main;
    private MessageFragment f_gift;
    private MyFragment f_my;
    private FragmentManager fragmentManager;

    private String UserId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_first);
        //绑定Text
        bindView();
    }
    private void bindView(){
        tabFirst = this.findViewById(R.id.txt_first);
        tabLook  = this.findViewById(R.id.txt_look);
        tabMessage = this.findViewById(R.id.txt_message);
        tabMy = this.findViewById(R.id.txt_my);
        tabGift = this.findViewById(R.id.txt_gift);
        ly_content = findViewById(R.id.fragment_container);
//注册单击事件监听
        tabFirst.setOnClickListener(this);
        tabLook.setOnClickListener(this);
        tabMessage.setOnClickListener(this);
        tabMy.setOnClickListener(this);
        tabGift.setOnClickListener(this);
        //默认单击我的
        pref = getSharedPreferences("LoginMessage",MODE_PRIVATE);
        boolean islanched = pref.getBoolean("isLunched",false);
        if(islanched){
            UserId = pref.getString("UserName","0");
            this.onClick(findViewById(R.id.txt_first));
        }
        else this.onClick(findViewById(R.id.txt_my));

    }
    //重置文本的选中状态
    public void selected(){
        tabFirst.setSelected(false);
        tabLook.setSelected(false);
        tabMessage.setSelected(false);
        tabMy.setSelected(false);
        tabGift.setSelected(false);
    }
    //隐藏Fragment
    public void hideAllFragment(FragmentTransaction transaction ){
        if(f_main!=null)transaction.hide(f_main);
        if(f_look!=null)transaction.hide(f_look);
        if(f_information!=null)transaction.hide(f_information);
        if(f_my!=null)transaction.hide(f_my);
        if(f_gift!=null)transaction.hide(f_gift);
    }

    /**
     * 底部导航的点击事件，首次点击时绑定碎片
     * @param v
     */
    public void onClick(View v){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        hideAllFragment(transaction);
        switch(v.getId()){
            case R.id.txt_first:
                selected();
                tabFirst.setSelected(true);
                if(f_main==null){
                    f_main = MainFragment.newInstance(UserId);
                    transaction.add(R.id.fragment_container,f_main);
                }else{
                    transaction.show(f_main);
                }
                break;
            case R.id.txt_look:
                selected();
                tabLook.setSelected(true);
                if(f_look==null){
                    f_look = ShopFragment.newInstance("Second_Fragment");
                    transaction.add(R.id.fragment_container,f_look);
                }else{
                    transaction.show(f_look);
                }
                break;
            case R.id.txt_message:
                selected();
                tabMessage.setSelected(true);
                if(f_information==null){
                    f_information = InformationFragment.newInstance("Second_Fragment");
                    transaction.add(R.id.fragment_container,f_information);
                }else{
                    transaction.show(f_information);
                }
                break;
            case R.id.txt_gift:
                selected();
                tabGift.setSelected(true);
                if(f_gift==null){
                    f_gift = MessageFragment.newInstance("");
                    transaction.add(R.id.fragment_container,f_gift);
                }else{
                    transaction.show(f_gift);
                }
                break;
            case R.id.txt_my:
                selected();
                tabMy.setSelected(true);
                if(f_my==null){
                    f_my = MyFragment.newInstance("Fourth_Fragment");
                    transaction.add(R.id.fragment_container,f_my);
                }else{
                    transaction.show(f_my);
                }
                break;
        }
        transaction.commit();
    }
    private static boolean isExit = false;
    //主线程处理视图，isExit默认为false，就是点击第一次时，弹出"再按一次退出程序"
    //点击第二次时关闭应用
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    };

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 点击两次退出程序
     */
    private void exit() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            // 利用handler延迟发送更改状态信息
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            finish();
            //参数用作状态码；根据惯例，非 0 的状态码表示异常终止。
            System.exit(0);
        }
    }
}


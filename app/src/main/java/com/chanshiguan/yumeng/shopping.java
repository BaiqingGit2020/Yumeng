package com.chanshiguan.yumeng;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class shopping extends AppCompatActivity {
    private TextView m11;
    private ImageView m21;
    private TextView m41;
    private TextView m31;
    private TextView mTextMessage;
    String name1;
    String name3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);
        mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        // m11=(TextView) findViewById(R.id.money1_view);
        m21=(ImageView) findViewById(R.id.item_detail_image);
        m31=(TextView) findViewById(R.id.item_detail_price);
        m41=(TextView) findViewById(R.id.item_detail_name);
        Intent intent=getIntent();

        name1=intent.getStringExtra("m1");
        String name2=intent.getStringExtra("m2");
        name3=intent.getStringExtra("m3");
        String name4=intent.getStringExtra("m4");
        m41.setText(name1);
        m41.setTextColor(m41.getResources().getColor(R.color.black));
        m41.setTextSize(30);
        m31.setText("¥  "+name3);
        m31.setTextColor(m31.getResources().getColor(R.color.lightcoral));
        m31.setTextSize(20);
        // m11.setText(name3);
        m21.setImageResource(R.drawable.a1);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);





    }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    // mTextMessage.setText(R.string.title_home);

                    Intent intent = new Intent();

                    intent.setClass(shopping.this, SelectPicPopupWindow.class);
                    intent.putExtra("m1", name1);

                    intent.putExtra("m3", name3);
                    intent.putExtra("m4", R.drawable.a1);
                    startActivity(intent);
                    //  startActivity(new Intent(shopping.this,SelectPicPopupWindow.class));


                    return true;
                case R.id.navigation_dashboard:
                    // mTextMessage.setText(R.string.title_dashboard);
                    Intent intent1 = new Intent();

                    intent1.setClass(shopping.this, tanchu2.class);
                    intent1.putExtra("m1", name1);

                    intent1.putExtra("m3", name3);
                    intent1.putExtra("m4", R.drawable.a1);
                    startActivity(intent1);
                    //  startActivity(new Intent(shopping.this,SelectPicPopupWindow.class));

                    return true;
                case R.id.navigation_notifications:
                    //mTextMessage.setText(R.string.title_notifications);
                    return true;
            }
            return false;
        }
    };
}

package com.chanshiguan.yumeng;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class tanchu2 extends Activity implements View.OnClickListener {

    private Button btn_take_photo, btn_pick_photo, btn_cancel;
    private LinearLayout layout;
    private TextView textView1;
    private int num1;
    private TextView m41;
    private TextView m31;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tanchu2);

        getWindow().setLayout(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);
        btn_take_photo = (Button) this.findViewById(R.id.button22);



        layout=(LinearLayout)findViewById(R.id.pop_layout);
        m31=(TextView) findViewById(R.id.money22);
        m41=(TextView) findViewById(R.id.count22);
        Intent intent=getIntent();
        String name1=intent.getStringExtra("m1");
        String name2=intent.getStringExtra("m3");
        m31.setText(name1);
        m41.setText(name2);
        //添加选择窗口范围监听可以优先获取触点，即不再执行onTouchEvent()函数，点击其他地方时执行onTouchEvent()函数销毁Activity
        layout.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                Toast.makeText(getApplicationContext(), "提示：点击窗口外部关闭窗口！",
                        Toast.LENGTH_SHORT).show();
            }
        });
        //添加按钮监听
        // btn_cancel.setOnClickListener(this);
        // btn_pick_photo.setOnClickListener(this);
        btn_take_photo.setOnClickListener(this);
    }

    //实现onTouchEvent触屏函数但点击屏幕时销毁本Activity
    @Override
    public boolean onTouchEvent(MotionEvent event){
        finish();
        return true;
    }


    public void iv_1(View view){
        textView1=(TextView)findViewById(R.id.textView1);
        num1=Integer.parseInt(textView1.getText().toString());
        if(num1>1){
            num1-=1;
        }

        textView1.setText(Integer.toString(num1));

    }
    public void iv_2(View view){
        textView1=(TextView)findViewById(R.id.textView1);
        num1=Integer.parseInt(textView1.getText().toString());
        if(num1<999){
            num1+=1;
        }

        textView1.setText(Integer.toString(num1));

    }



    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button22:{
                Toast.makeText(getApplicationContext(), "购买成功", Toast.LENGTH_SHORT).show();

                break;}



            default:
                break;
        }
        finish();
    }
}

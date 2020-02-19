package com.chanshiguan.yumeng;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

/**
 * Created by Carson_Ho on 16/7/22.
 */
public class Fragment1 extends Fragment {
    private View view,view2;
    private ImageButton btn5;
    private ImageButton btn6;
    private ImageButton btn7;
    private ImageButton btn8;
    private ImageButton btn1;
    private ImageButton btn2;
    private ImageButton btn3;
    private ImageButton btn4;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.activity_fragment1, null);
        btn5 = (ImageButton) view.findViewById(R.id.Imagebutton5);
        btn6 = (ImageButton) view.findViewById(R.id.Imagebutton6);
        btn7 = (ImageButton) view.findViewById(R.id.Imagebutton7);
        btn8 = (ImageButton) view.findViewById(R.id.Imagebutton8);
        btn1 = (ImageButton) view.findViewById(R.id.Imagebutton1);
        btn2 = (ImageButton) view.findViewById(R.id.Imagebutton2);
        btn3 = (ImageButton) view.findViewById(R.id.Imagebutton3);
        btn4 = (ImageButton) view.findViewById(R.id.Imagebutton4);
        //return view2=inflater.inflate(R.layout.fragment1, null);//错误的写法
        return view;


        //return inflater.inflate(R.layout.activity_fragment1, container, false);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);


        btn5.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                Intent intent=new Intent(getActivity(), shop2.class);

                intent.putExtra("name1", "狗粮");
                startActivity(intent);

            }
        });
        btn6.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                Intent intent=new Intent(getActivity(), shop2.class);

                intent.putExtra("name1", "服饰");
                startActivity(intent);

            }
        });
        btn7.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                Intent intent=new Intent(getActivity(), shop2.class);

                intent.putExtra("name1", "狗窝");
                startActivity(intent);

            }
        });
        btn8.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                Intent intent=new Intent(getActivity(), shop2.class);

                intent.putExtra("name1", "装饰");
                startActivity(intent);

            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                Intent intent=new Intent(getActivity(), shop2.class);

                intent.putExtra("name1", "洗护");
                startActivity(intent);

            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                Intent intent=new Intent(getActivity(), shop2.class);

                intent.putExtra("name1", "药品");
                startActivity(intent);

            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                Intent intent=new Intent(getActivity(), shop2.class);

                intent.putExtra("name1", "玩具");
                startActivity(intent);

            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                Intent intent=new Intent(getActivity(), shop2.class);

                intent.putExtra("name1", "");
                startActivity(intent);

            }
        });
    }


}
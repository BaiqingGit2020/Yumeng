package com.chanshiguan.yumeng;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class InformationFragment extends Fragment {
    private Bundle bundle;
    ImageView mimageView01;
    ImageView mimageView02;
    private static final String TAG = "InformationFragment";
    private static final String BUNDLE_CONTEXT= "context";
    public static InformationFragment newInstance(String context){
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_CONTEXT,context);

        InformationFragment firstFragment = new InformationFragment();
        firstFragment.setArguments(bundle);
        return firstFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.information_fragment,container,false);
        mimageView01=(ImageView)view.findViewById(R.id.image01);
        mimageView01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent=new Intent(getActivity(),imageviewone.class);
                startActivity(intent);
            }
        });
        mimageView02=(ImageView)view.findViewById(R.id.image02);
        mimageView02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent=new Intent(getActivity(),imageviewtwo.class);
                startActivity(intent);
            }
        });
        return view;
    }
}

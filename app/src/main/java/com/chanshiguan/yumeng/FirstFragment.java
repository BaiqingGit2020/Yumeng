package com.chanshiguan.yumeng;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FirstFragment extends Fragment implements View.OnClickListener{
    private Bundle bundle;
    private TextView mTextView;
    private static final String TAG = "FirstFragment";
    private static final String BUNDLE_CONTEXT= "context";

    public static FirstFragment newInstance(String context){
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_CONTEXT,context);

        FirstFragment firstFragment = new FirstFragment();
        firstFragment.setArguments(bundle);
        return firstFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.first_fragment,container,false);
        mTextView = view.findViewById(R.id.txt_content);
        bundle=getArguments();
        //mTextView = (TextView)getActivity().findViewById(R.id.txt_content);
        mTextView.setText(bundle.getString(BUNDLE_CONTEXT));
        Button button=view.findViewById(R.id.OKHttp);
        button.setOnClickListener(this);
        return view;
    }
    public void onClick(View v){
        send();
    }
    private void send(){
        startActivity(new Intent(getActivity(),MessageActivity.class));
    }
}

